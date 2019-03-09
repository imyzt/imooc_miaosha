package top.imyzt.study.miaosha.result;

import lombok.Getter;

/**
 * @author imyzt
 * @date 2019/3/8 17:30
 * @description CodeMsg
 */
@Getter
public class CodeMsg {

    private Integer code;

    private String msg;

    public static final int SUCCESS_CODE = 0;

    // 通用错误码

    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");

    // 登录模块错误码

    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式不正确");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "账号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");



    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
