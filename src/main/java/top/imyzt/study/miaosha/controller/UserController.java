package top.imyzt.study.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.imyzt.study.miaosha.common.redis.UserKey;
import top.imyzt.study.miaosha.domain.User;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.RedisService;
import top.imyzt.study.miaosha.service.UserService;

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description UserController
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @GetMapping("{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/redis/set")
    public Result redisSet() {
        User user = new User();
        user.setId(1);
        user.setName("yzt");

        boolean uyzt = redisService.set(UserKey.GET_BY_ID,"1", user);
        return Result.success(uyzt);
    }

    @GetMapping("/redis/get")
    public Result redisGet() {
        User uyzt = redisService.get(UserKey.GET_BY_ID,"1", User.class);
        return Result.success(uyzt);
    }

}
