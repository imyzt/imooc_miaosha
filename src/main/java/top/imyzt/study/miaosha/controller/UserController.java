package top.imyzt.study.miaosha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.result.Result;

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description UserController
 */
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/info")
    public Result <MiaoshaUser> info(MiaoshaUser user) {
        return Result.success(user);
    }

}
