package top.imyzt.study.miaosha.utils;

import java.util.UUID;

/**
 * @author imyzt
 * @date 2019/3/9 15:49
 * @description UUIDUtil
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
