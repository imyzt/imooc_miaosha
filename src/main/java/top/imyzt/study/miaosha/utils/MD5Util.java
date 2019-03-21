package top.imyzt.study.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author imyzt
 * @date 2019/3/9 10:36
 * @description MD5Util
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "fas_12^*(%&";

    public static String inputPassToFormPass(String src) {
        String str = SALT.charAt(4) + SALT.substring(4) + SALT.substring(0, 5) + src + SALT.charAt(9);
        return md5(str);
    }

    public static String formPassToDBPass(String src, String salt) {
        String str = salt.charAt(4) + salt.substring(4) + salt.substring(0, 5) + src + salt.charAt(9);
        return md5(str);
    }

    /*public static String inputPassToDBPass(String src, String saltDB) {
        String str = saltDB.charAt(4) + saltDB.substring(4) + saltDB.substring(0, 5) + src + saltDB.charAt(9);
        return md5(str);
    }*/

    public static String inputPassToDBPass(String src, String saltDB) {
        return formPassToDBPass(inputPassToFormPass(src), saltDB);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass("b4accafee38035d4a1f0532785056d14", "y95834zn39"));
        System.out.println(inputPassToDBPass("123456", "y95834zn39"));
    }

}
