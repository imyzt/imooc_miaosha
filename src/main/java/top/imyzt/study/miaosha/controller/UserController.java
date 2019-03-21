package top.imyzt.study.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.rabbitmq.MqSender;
import top.imyzt.study.miaosha.result.Result;

/**
 * @author imyzt
 * @date 2019/3/8 17:27
 * @description UserController
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final MqSender sender;

    @Autowired
    public UserController(MqSender sender) {
        this.sender = sender;
    }

    @GetMapping("/info")
    public Result <MiaoshaUser> info(MiaoshaUser user) {
        return Result.success(user);
    }

    @GetMapping("mqSender")
    public void mqSender(String msg) {
        sender.sender(msg);
    }

    @GetMapping("topicSender")
    public void topicSender(String msg) {
        sender.topicSender(msg);
    }

    @GetMapping("fanoutSender")
    public void fanoutSender(String msg) {
        sender.fanoutSender(msg);
    }

    @GetMapping("headersSender")
    public void headersSender(String msg) {
        sender.headersSender(msg);
    }
}
