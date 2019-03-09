package top.imyzt.study.miaosha.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import top.imyzt.study.miaosha.common.redis.KeyPrefix;

/**
 * @author imyzt
 * @date 2019/3/8 18:12
 * @description RedisService
 */
@Service
public class RedisService {

    private final JedisPool jedisPool;

    @Autowired
    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * get
     * @param prefix 前缀
     * @param key key
     * @param clazz json转换类型
     * @param <T> 泛型
     * @return 值
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        try(Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getRealKey(key);
            String str = jedis.get(realKey);
            return strToBean(str, clazz);
        }
    }

    /**
     * set
     * @param prefix 前缀
     * @param key key
     * @param value value
     * @param <T> 泛型
     * @return 是否成功
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        try(Jedis jedis = jedisPool.getResource()) {
            String str = beanToStr(value);
            if (null != str && str.length() > 0) {
                String realKey = prefix.getRealKey(key);
                int seconds = prefix.getExpireSeconds();
                if (seconds <= 0) {
                    jedis.set(realKey, str);
                } else {
                    jedis.setex(realKey, seconds, str);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断key是否存在
     * @param prefix 前缀
     * @param key key
     * @return 是否存在
     */
    public boolean exists(KeyPrefix prefix, String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getRealKey(key);
            return jedis.exists(realKey);
        }
    }

    /**
     * 自增
     * @param prefix 前缀
     * @param key key
     * @return 自增后的值
     */
    public Long incr(KeyPrefix prefix, String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getRealKey(key);
            return jedis.incr(realKey);
        }
    }

    /**
     * 自减
     * @param prefix 前缀
     * @param key key
     * @return 自减后的值
     */
    public Long decr(KeyPrefix prefix, String key) {
        try(Jedis jedis = jedisPool.getResource()) {
            String realKey = prefix.getRealKey(key);
            return jedis.decr(realKey);
        }
    }


    private <T> String beanToStr(T value) {
        if (null == value) {
            return null;
        }

        Class <?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T strToBean(String value, Class<T> clazz) {
        if (null == value || value.length() <= 0 || null == clazz) {
            return null;
        }

        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (clazz == String.class) {
            return (T) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(value);
        } else {
            return JSON.toJavaObject(JSON.parseObject(value), clazz);
        }
    }


}
