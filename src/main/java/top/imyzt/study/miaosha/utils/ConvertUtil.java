package top.imyzt.study.miaosha.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author imyzt
 * @date 2019/3/20 11:23
 * @description ConvertUtil
 */
public class ConvertUtil {

    public static  <T> String beanToStr(T value) {
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
    public static  <T> T strToBean(String value, Class<T> clazz) {
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
