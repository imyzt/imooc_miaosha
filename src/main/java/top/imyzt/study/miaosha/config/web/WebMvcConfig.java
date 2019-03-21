package top.imyzt.study.miaosha.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import top.imyzt.study.miaosha.access.AccessInterceptor;

import java.util.List;

/**
 * @author imyzt
 * @date 2019/3/9 16:52
 * @description WebMvcConfig
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{

    private final AccessInterceptor accessInterceptor;
    private final UserArgumentResolver userArgumentResolver;

    @Autowired
    public WebMvcConfig(AccessInterceptor accessInterceptor,
                        UserArgumentResolver userArgumentResolver) {
        this.accessInterceptor = accessInterceptor;
        this.userArgumentResolver = userArgumentResolver;
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }
}
