package top.imyzt.study.miaosha.common.redis;

/**
 * @author imyzt
 * @date 2019/3/18 15:34
 * @description GoodsKey
 */
public class GoodsKey extends AbstractPrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");
}
