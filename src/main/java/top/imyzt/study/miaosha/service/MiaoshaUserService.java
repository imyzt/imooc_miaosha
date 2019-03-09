package top.imyzt.study.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.mapper.MiaoshaUserMapper;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.utils.MD5Util;
import top.imyzt.study.miaosha.vo.LoginVo;

import java.util.Objects;

/**
 * @author imyzt
 * @date 2019/3/9 14:12
 * @description MiaoshaUserService
 */
@Service
public class MiaoshaUserService {

    @Autowired
    private MiaoshaUserMapper userMapper;

    public MiaoshaUser getById(long id) {
        return userMapper.getById(id);
    }

    public CodeMsg login(LoginVo loginVo) {

        if (null == loginVo) {
            return CodeMsg.SERVER_ERROR;
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        MiaoshaUser user = this.getById(Long.parseLong(mobile));

        if (null == user) {
            return CodeMsg.MOBILE_NOT_EXIST;
        }

        String dbSalt = user.getSalt();
        String dbPass = user.getPassword();

        String formPassToDBPass = MD5Util.formPassToDBPass(formPass, dbSalt);

        if (Objects.equals(dbPass, formPassToDBPass)) {
            return CodeMsg.PASSWORD_ERROR;
        }

        return CodeMsg.SUCCESS;
    }
}
