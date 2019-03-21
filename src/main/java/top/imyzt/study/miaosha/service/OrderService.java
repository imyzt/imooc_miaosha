package top.imyzt.study.miaosha.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.common.redis.OrderKey;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.mapper.OrderMapper;
import top.imyzt.study.miaosha.vo.GoodsVo;

import java.time.Instant;
import java.util.Date;

/**
 * @author imyzt
 * @date 2019/3/13 18:21
 * @description OrderService
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {

        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
    }

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {

        // 下订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(Date.from(Instant.now()));
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());

        orderMapper.insert(orderInfo);

        // 下秒杀订单
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());

        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+user.getId()+"_"+goodsVo.getId(), miaoshaOrder);

        log.info("成功下单. userId={}, goodsId={}", user.getId(), goodsVo.getId());
        return orderInfo;
    }

    public OrderInfo getById(long orderId) {
        return orderMapper.getById(orderId);
    }
}
