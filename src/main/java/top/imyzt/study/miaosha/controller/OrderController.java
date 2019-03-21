package top.imyzt.study.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.GoodsService;
import top.imyzt.study.miaosha.service.OrderService;
import top.imyzt.study.miaosha.vo.GoodsVo;
import top.imyzt.study.miaosha.vo.OrderDetailVo;

/**
 * @author imyzt
 * @date 2019/3/19 16:36
 * @description OrderController
 */
@RequestMapping("order")
@Controller
public class OrderController {

    private final OrderService orderService;
    private final GoodsService goodsService;

    @Autowired
    public OrderController(OrderService orderService, GoodsService goodsService) {
        this.orderService = orderService;
        this.goodsService = goodsService;
    }

    @GetMapping("detail")
    public @ResponseBody Result detail(MiaoshaUser user, Long orderId) {

        if (null == user) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 查询order
        OrderInfo orderInfo = orderService.getById(orderId);
        // 查询goods
        if (null == orderInfo) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        Long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoods(goodsVo);
        orderDetailVo.setOrder(orderInfo);

        return Result.success(orderDetailVo);
    }

}
