package top.imyzt.study.miaosha.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author imyzt
 * @date 2019/3/9 13:55
 * @description ValidatorUtil
 */
public class ValidatorUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    public static boolean isMobile (String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("12222222222"));
    }
}
