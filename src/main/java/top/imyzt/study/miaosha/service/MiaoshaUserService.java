package top.imyzt.study.miaosha.service;

import org.apache.commons.lang3.StringUtils;
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

/**
 * @author imyzt
 * @date 2019/3/9 14:12
 * @description MiaoshaUserService
 */
@Service
public class MiaoshaUserService {

    @Autowired
    private MiaoshaUserMapper userMapper;
    @Autowired
    private RedisService redisService;

    public static final String COOKIE_NAME_TOKEN = "token";

    public MiaoshaUser getById(long id) {
        return userMapper.getById(id);
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
