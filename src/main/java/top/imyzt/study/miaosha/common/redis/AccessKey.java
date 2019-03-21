package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/21 15:56
 * @description AccessKey
 */
public class AccessKey extends AbstractPrefix {
    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static AccessKey withExpireSeconds(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }
}
