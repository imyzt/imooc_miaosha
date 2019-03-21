package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/9 9:24
 * @description OrderKey
 */
public class OrderKey extends AbstractPrefix {


    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
