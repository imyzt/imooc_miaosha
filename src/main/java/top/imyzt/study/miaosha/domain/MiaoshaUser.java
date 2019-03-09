package top.imyzt.study.miaosha.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author imyzt
 * @date 2019/3/9 13:59
 * @description MiaoshaUser
 */
@Data
public class MiaoshaUser {

    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;

}
