package top.imyzt.study.miaosha.exception;

import lombok.Getter;
import top.imyzt.study.miaosha.result.CodeMsg;

/**
 * @author imyzt
 * @date 2019/3/9 15:31
 * @description GlobalException
 */
@Getter
public class GlobalException extends RuntimeException {

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }
}
