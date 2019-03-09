package top.imyzt.study.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.MiaoshaUserService;
import top.imyzt.study.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    private MiaoshaUserService userService;

    @GetMapping("/to_list")
    public String toList(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }

}
