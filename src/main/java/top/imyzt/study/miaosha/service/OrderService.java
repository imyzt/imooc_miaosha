package top.imyzt.study.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, Long goodsId) {
        return orderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
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

        long orderId = orderMapper.insert(orderInfo);

        // 下秒杀订单

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());

        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        return orderInfo;
    }
}
