package top.imyzt.study.miaosha.service;

import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.common.redis.MiaoshaUserKey;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.exception.GlobalException;
import top.imyzt.study.miaosha.mapper.MiaoshaUserMapper;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.utils.MD5Util;
import top.imyzt.study.miaosha.utils.UUIDUtil;
import top.imyzt.study.miaosha.vo.LoginVo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Random;

/**
 * @author imyzt
 * @date 2019/3/9 14:12
 * @description MiaoshaUserService
 */
@Service
public class MiaoshaUserService {

    private final MiaoshaUserMapper userMapper;
    private final RedisService redisService;

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    public MiaoshaUserService(MiaoshaUserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    public MiaoshaUser getById(long id) {

        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (null != miaoshaUser) {
            return miaoshaUser;
        }

        miaoshaUser = userMapper.getById(id);

        if (null != miaoshaUser) {
            redisService.set(MiaoshaUserKey.getById, "" + id, miaoshaUser);
        }

        return miaoshaUser;
    }

    public MiaoshaUser register (MiaoshaUser user) {

        if (null == user) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        MiaoshaUser registerUser = new MiaoshaUser();
        BeanUtils.copyProperties(user, registerUser);

        String formPass = user.getPassword();
        String salt = RandomUtil.randomString(10);
        String dbPass = MD5Util.formPassToDBPass(formPass, salt);
        registerUser.setPassword(dbPass);
        registerUser.setSalt(salt);

        return userMapper.insert(registerUser);
    }

    public MiaoshaUser updatePassword(String token, long id, String form) {

        MiaoshaUser miaoshaUser = this.getById(id);
        if (null == miaoshaUser) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //更新密码
        MiaoshaUser updateUser = new MiaoshaUser();
        updateUser.setId(id);
        updateUser.setPassword(MD5Util.formPassToDBPass(form, miaoshaUser.getSalt()));
        userMapper.update(updateUser);

        miaoshaUser.setPassword(updateUser.getPassword());

        // 更新缓存
        redisService.delete(MiaoshaUserKey.getById, ""+id);
        redisService.set(MiaoshaUserKey.TOKEN, token, miaoshaUser);

        return miaoshaUser;
    }


    public boolean login(HttpServletResponse response, LoginVo loginVo) {

        if (null == loginVo) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();

        MiaoshaUser user = this.getById(Long.parseLong(mobile));

        if (null == user) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        String dbSalt = user.getSalt();
        String dbPass = user.getPassword();

        String formPassToDBPass = MD5Util.formPassToDBPass(formPass, dbSalt);

        if (!Objects.equals(dbPass, formPassToDBPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        String token = UUIDUtil.uuid();
        writeCookie(response, token, user);

        return true;
    }

    private void writeCookie(HttpServletResponse response, String token, MiaoshaUser user) {

        // 存入redis
        redisService.set(MiaoshaUserKey.TOKEN, token, user);

        // 写入cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.TOKEN.getExpireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.TOKEN, token, MiaoshaUser.class);
        // 续签
        if (null != miaoshaUser) {
            writeCookie(response, token, miaoshaUser);
        }
        return miaoshaUser;
    }
}
