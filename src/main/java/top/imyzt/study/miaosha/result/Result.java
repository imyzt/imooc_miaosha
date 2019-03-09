package top.imyzt.study.miaosha.result;

import lombok.Data;

/**
 * @author imyzt
 * @date 2019/3/8 17:28
 * @description Result
 */
@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    private Result() {
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (null == codeMsg) {
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    public static <T> Result<T> success(T data) {
        return new Result <>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result <>(codeMsg);
    }

}
