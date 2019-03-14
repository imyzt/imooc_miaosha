package top.imyzt.study.miaosha.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author imyzt
 * @date 2019/3/9 15:19
 * @description GlobalExceptionHandler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exception(HttpServletRequest request, Exception e) {

        e.printStackTrace();

        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            CodeMsg codeMsg = globalException.getCm();
            return Result.error(codeMsg);
        }
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List <ObjectError> allErrors = bindException.getAllErrors();
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(defaultMessage));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
