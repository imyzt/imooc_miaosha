package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/20 18:37
 * @description MiaoshaKey
 */
public class MiaoshaKey extends AbstractPrefix {
    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static final MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
