package top.imyzt.study.miaosha.access;

import top.imyzt.study.miaosha.domain.MiaoshaUser;

/**
 * @author imyzt
 * @date 2019/3/21 15:38
 * @description 用户信息上下文
 */
public class UserContext {

    private static final ThreadLocal<MiaoshaUser> USER_HOLDER = new ThreadLocal <>();

    public static MiaoshaUser getUser() {
        return USER_HOLDER.get();
    }

    public static void setUser(MiaoshaUser user) {
        USER_HOLDER.set(user);
    }

    public static void remove() {
        USER_HOLDER.remove();
    }
}
