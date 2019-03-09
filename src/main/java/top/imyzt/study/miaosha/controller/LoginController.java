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

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description LoginController
 */
@Controller
@Slf4j
@RequestMapping("login")
public class LoginController {

    @Autowired
    private MiaoshaUserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/do_login")
    @ResponseBody
    public Result login(LoginVo loginVo) {

        String password = loginVo.getPassword();
        String mobile = loginVo.getMobile();

        if (StringUtils.isEmpty(password)) {
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        } else if (StringUtils.isEmpty(mobile)) {
            return Result.error(CodeMsg.MOBILE_EMPTY);
        } else if (!ValidatorUtil.isMobile(mobile)) {
            return Result.error(CodeMsg.MOBILE_ERROR);
        }

        CodeMsg codeMsg = userService.login(loginVo);
        if (codeMsg.getCode() == CodeMsg.SUCCESS_CODE) {
            return Result.success(true);
        }
        return Result.error(codeMsg);
    }


}
