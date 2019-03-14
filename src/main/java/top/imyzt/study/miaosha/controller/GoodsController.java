package top.imyzt.study.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.GoodsService;
import top.imyzt.study.miaosha.service.MiaoshaUserService;
import top.imyzt.study.miaosha.vo.GoodsVo;
import top.imyzt.study.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description GoodsController
 */
@Controller
@Slf4j
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/to_list")
    public String toList(Model model, MiaoshaUser user) {

        List <GoodsVo> goodsVos = goodsService.listGoodsVo();

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsVos);
        return "goods_list";
    }

    @GetMapping("to_detail/{goodsId}")
    public String getDetail(Model model, MiaoshaUser user, @PathVariable Long goodsId) {

        model.addAttribute("user", user);

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);

        long startTme = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long now = Instant.now().getEpochSecond() * 1000;

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        // 还没开始
        if (now < startTme) {
            miaoshaStatus = 0;
            remainSeconds = (int) (startTme - now) / 1000;
        // 已结束
        } else if (now > endTime) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("user", user);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goodsVo);

        return "goods_detail";
    }

}
