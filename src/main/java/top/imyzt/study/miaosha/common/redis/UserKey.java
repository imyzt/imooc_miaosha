package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/9 9:23
 * @description UserKey
 */
public class UserKey extends AbstractPrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey GET_BY_ID = new UserKey("id");
    public static UserKey GET_BY_NAME = new UserKey("name");

}
