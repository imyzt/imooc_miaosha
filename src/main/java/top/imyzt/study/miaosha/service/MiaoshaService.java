package top.imyzt.study.miaosha.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.imyzt.study.miaosha.common.redis.MiaoshaKey;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.vo.GoodsVo;

/**
 * @author imyzt
 * @date 2019/3/13 18:25
 * @description MiaoshaService
 */
@Service
@Slf4j
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        // 减库存
        boolean reduceStock = goodsService.reduceStock(goodsVo);
        if (reduceStock) {
            // 下订单
            return orderService.createOrder(user, goodsVo);
        } else {
            // 设置该商品状态为已售完
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }

    public long getMiaoshaResult(Long userId, Long goodsId) {

        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        // 秒杀成功
        if (null != miaoshaOrder) {
            return miaoshaOrder.getId();
        } else {
            // 判断该商品是否已售完
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(Long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
