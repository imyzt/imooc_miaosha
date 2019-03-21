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

    public MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final MiaoshaKey isGoodsOver = new MiaoshaKey("go");
    public static final MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "gp");
    public static final MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}
