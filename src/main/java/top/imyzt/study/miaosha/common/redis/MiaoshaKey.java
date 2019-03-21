package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/20 18:37
 * @description MiaoshaKey
 */
public class MiaoshaKey extends AbstractPrefix {
    private MiaoshaKey(String prefix) {
        super(prefix);
    }

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final MiaoshaKey IS_GOODS_OVER = new MiaoshaKey("go");
    public static final MiaoshaKey GET_MIAOSHA_PATH = new MiaoshaKey(60, "gp");
    public static final MiaoshaKey GET_MIAOSHA_VERIFY_CODE = new MiaoshaKey(300, "vc");
}
