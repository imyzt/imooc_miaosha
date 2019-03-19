package top.imyzt.study.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.GoodsService;
import top.imyzt.study.miaosha.service.MiaoshaService;
import top.imyzt.study.miaosha.service.OrderService;
import top.imyzt.study.miaosha.vo.GoodsVo;

/**
 * @author imyzt
 * @date 2019/3/13 18:12
 * @description MiaoshaController
 */
@Controller
@RequestMapping("miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    /**
     * 2.0 页面静态化处理, 前后端通过json交互
     * @param user
     * @param goodsId
     * @return
     */
    @PostMapping("do_miaosha")
    public @ResponseBody Result miaosha(MiaoshaUser user, Long goodsId) {

        // 未登录
        if (null == user) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

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
    }

}
