package top.imyzt.study.miaosha;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author imyzt
 * @date 2019/3/15 11:44
 * @description tomcat启动引导类
 */
public class ServletApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServletApplication.class);
    }
}
