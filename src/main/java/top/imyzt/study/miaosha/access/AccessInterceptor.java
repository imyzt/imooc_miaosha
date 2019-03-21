package top.imyzt.study.miaosha.access;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import top.imyzt.study.miaosha.common.redis.AccessKey;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.MiaoshaUserService;
import top.imyzt.study.miaosha.service.RedisService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author imyzt
 * @date 2019/3/21 15:31
 * @description AccessInterceptor
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

    private final MiaoshaUserService userService;
    private final RedisService redisService;

    @Autowired
    public AccessInterceptor(MiaoshaUserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;

            MiaoshaUser miaoshaUser = getUser(request, response);
            UserContext.setUser(miaoshaUser);

            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);

            // 方法没有注解
            if (Objects.isNull(accessLimit)) {
                return true;
            }

            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            int seconds = accessLimit.seconds();
            String key = request.getRequestURI();

            if (needLogin) {
                if (Objects.isNull(miaoshaUser)) {
                    render(response, CodeMsg.SERVER_ERROR);
                    return false;
                }
                key += "_" + miaoshaUser.getId();
            }

            AccessKey accessKey = AccessKey.withExpireSeconds(seconds);
            Integer currentCount = redisService.get(accessKey, key, Integer.class);
            if (null == currentCount) {
                redisService.set(accessKey, key, 1);
            } else if (currentCount < maxCount) {
                redisService.incr(accessKey, key);
            } else {
                render(response, CodeMsg.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    /**
     * 向响应流写数据.返回前端错误具体原因
     */
    private void render(HttpServletResponse response, CodeMsg codeMsg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String str = JSON.toJSONString(Result.error(codeMsg));
        outputStream.write(str.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {

        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);

        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
