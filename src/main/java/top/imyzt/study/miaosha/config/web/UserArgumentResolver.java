package top.imyzt.study.miaosha.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.imyzt.study.miaosha.access.UserContext;
import top.imyzt.study.miaosha.domain.MiaoshaUser;

/**
 * @author imyzt
 * @date 2019/3/9 16:53
 * @description 实现此方法, 将自动对方法入参包含 {@link MiaoshaUser} 的对象的进行注入
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

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
        return UserContext.getUser();
    }
}
