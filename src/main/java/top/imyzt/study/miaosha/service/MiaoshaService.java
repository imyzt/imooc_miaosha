package top.imyzt.study.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.vo.GoodsVo;

/**
 * @author imyzt
 * @date 2019/3/13 18:25
 * @description MiaoshaService
 */
@Service
public class MiaoshaService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    @Transactional(rollbackFor = Exception.class)
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        // 减库存
        goodsService.reduceStock(goodsVo);

        // 下订单
        return orderService.createOrder(user, goodsVo);
    }
}
