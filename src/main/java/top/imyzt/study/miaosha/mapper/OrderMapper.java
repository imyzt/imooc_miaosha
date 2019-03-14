package top.imyzt.study.miaosha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.OrderInfo;

/**
 * @author imyzt
 * @date 2019/3/13 18:21
 * @description OrderMapper
 */
public interface OrderMapper {


    /**
     * 根据条件查询秒杀订单
     * @param userId 用户id
     * @param goodsId 商品id
     * @return 秒杀商品
     */
    @Select("SELECT * FROM miaosha_order WHERE user_id = #{userId} AND goods_id = #{goodsId} ")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId, @Param("goodsId")Long goodsId);

    @Insert("INSERT INTO order_info() VALUES ()")
    long insert(OrderInfo orderInfo);

    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
