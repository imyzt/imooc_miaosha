package top.imyzt.study.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.result.CodeMsg;
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

    @PostMapping("do_miaosha")
    public String miaosha(Model model, MiaoshaUser user, Long goodsId) {

        // 未登录
        if (null == user) {
            return "/login/login";
        }

        model.addAttribute("user", user);

        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (null == goodsVo || goodsVo.getStockCount() <= 0) {
            model.addAttribute("errorMsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return "miaosha_fail";
        }

        // 判断是否重复下单
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (null != miaoshaOrder) {
            model.addAttribute("errorMsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        // 减库存,下订单 写入订单
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);

        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);


        return "order_detail";
    }

}
