package top.imyzt.study.miaosha.config.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.service.MiaoshaUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author imyzt
 * @date 2019/3/9 16:53
 * @description 实现此方法, 将自动对方法入参包含 {@link MiaoshaUser} 的对象的进行注入
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final MiaoshaUserService userService;

    @Autowired
    public UserArgumentResolver(MiaoshaUserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class <?> parameterType = parameter.getParameterType();
        return parameterType == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);

        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }

        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return userService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
