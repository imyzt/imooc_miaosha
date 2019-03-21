package top.imyzt.study.miaosha.controller;

import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imyzt.study.miaosha.common.redis.GoodsKey;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.rabbitmq.MqSender;
import top.imyzt.study.miaosha.rabbitmq.dto.MiaoshaMessage;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.GoodsService;
import top.imyzt.study.miaosha.service.MiaoshaService;
import top.imyzt.study.miaosha.service.OrderService;
import top.imyzt.study.miaosha.service.RedisService;
import top.imyzt.study.miaosha.vo.GoodsVo;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author imyzt
 * @date 2019/3/13 18:12
 * @description MiaoshaController
 */
@Controller
@RequestMapping("miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private MqSender sender;

    @Autowired
    private OrderService orderService;

    private static final Map<Long, Boolean> LOCAL_GOODS_MAP = new ConcurrentHashMap <>();

    /**
     * 初始化系统时, 将所有商品的库存加入到redis缓存中
     */
    @Override
    public void afterPropertiesSet() {
        List <GoodsVo> goodsVos = goodsService.listGoodsVo();
        if (null != goodsVos) {
            goodsVos.parallelStream().forEach(goodsVo -> {
                redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
                LOCAL_GOODS_MAP.put(goodsVo.getId(), false);
            });
        }
    }

    /**
     * 2.0 页面静态化处理, 前后端通过json交互
     * 3.0 请求入队,削峰 qps = 2130
     */
    @PostMapping("do_miaosha")
    public @ResponseBody Result miaosha(MiaoshaUser user, Long goodsId) {

        // 未登录
        if (null == user) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 内存标记, 减少redis访问
        if (LOCAL_GOODS_MAP.get(goodsId)) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断库存, 先减一再判断减一后的结果是否大于0
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (null != stock && stock < 0) {
            LOCAL_GOODS_MAP.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if(order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 请求入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage(user, goodsId);
        sender.miaoshaSender(miaoshaMessage);

        return Result.success(0);
        /*
        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (null == goodsVo || goodsVo.getStockCount() <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否重复下单
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (null != miaoshaOrder) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 减库存,下订单 写入订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        return Result.success(orderInfo);
        */
    }

    /**
     * orderId  秒杀成功
     * -1 秒杀失败
     * 0 排队中
     */
    @GetMapping("result")
    public @ResponseBody Result<Long> miaoshaResult(MiaoshaUser user, Long goodsId) {

        if (null == user) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        long orderId = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(orderId);
    }


}
