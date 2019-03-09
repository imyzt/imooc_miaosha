package top.imyzt.study.miaosha.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author imyzt
 * @date 2019/3/9 11:30
 * @description LoginVo
 */
@Data
public class LoginVo implements Serializable{

    private String mobile;

    private String password;
}
