package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/9 9:20
 * @description AbstractPrefix
 */
public abstract class AbstractPrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    AbstractPrefix(String prefix) {
        this(0, prefix);
    }

    AbstractPrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 获取过期时间
     *
     * @return expire
     */
    @Override
    public int getExpireSeconds() {
        return this.expireSeconds;
    }

    /**
     * 获取key前缀
     *
     * @return key prefix
     */
    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + this.prefix;
    }

    @Override
    public String getRealKey(String key) {
        return getPrefix() + key;
    }
}
