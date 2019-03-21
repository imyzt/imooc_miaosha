package top.imyzt.study.miaosha.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imyzt.study.miaosha.domain.User;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.MiaoshaUserService;
import top.imyzt.study.miaosha.utils.ValidatorUtil;
import top.imyzt.study.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description LoginController
 */
@Controller
@Slf4j
@RequestMapping("login")
public class LoginController {

    private final MiaoshaUserService userService;

    @Autowired
    public LoginController(MiaoshaUserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result login(HttpServletResponse response, @Valid LoginVo loginVo) {
        boolean login = userService.login(response, loginVo);
        return Result.success(true);
    }


}
