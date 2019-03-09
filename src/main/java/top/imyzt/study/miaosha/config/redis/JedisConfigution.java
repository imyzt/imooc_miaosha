package top.imyzt.study.miaosha.config.redis;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Objects;

/**
 * @author imyzt
 * @date 2019/3/8 18:02
 * @description JedisConfigution
 */
@Configuration
public class JedisConfigution {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        return poolConfig;
    }

    @Bean
    public JedisPool jedisPool(JedisPoolConfig poolConfig) {
        String password = "".equals(redisConfig.getPassword()) ? null : redisConfig.getPassword();
            return new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),
                    redisConfig.getTimeout(), password, redisConfig.getDatabase());
    }
}
