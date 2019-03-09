package top.imyzt.study.miaosha.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author imyzt
 * @date 2019/3/8 17:59
 * @description RedisConfig
 */
@ConfigurationProperties("redis")
@Data
@Component
public class RedisConfig {
    private String host;
    private Integer port;
    /**
     * s
     */
    private Integer timeout;
    private String password;
    private Integer poolMaxTotal;
    private Integer poolMaxIdle;
    private Integer poolMaxWait;
    private Integer database;
}
