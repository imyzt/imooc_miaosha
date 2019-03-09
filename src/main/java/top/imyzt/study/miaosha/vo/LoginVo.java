package top.imyzt.study.miaosha.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.imyzt.study.miaosha.validator.IsMobile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author imyzt
 * @date 2019/3/9 11:30
 * @description LoginVo
 */
@Data
public class LoginVo implements Serializable{

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 6)
    private String password;
}
