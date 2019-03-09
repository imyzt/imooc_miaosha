package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/9 9:24
 * @description OrderKey
 */
public class OrderKey extends AbstractPrefix {

    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
