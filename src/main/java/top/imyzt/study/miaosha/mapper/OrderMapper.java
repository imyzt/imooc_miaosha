package top.imyzt.study.miaosha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
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
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId, @Param("goodsId")Long goodsId);

    @Insert("INSERT INTO order_info(user_id, goods_id, goods_name, goods_price, goods_count, order_channel, status, create_date) " +
            "VALUES (#{userId}, #{goodsId}, #{goodsName}, #{goodsPrice}, #{goodsCount}, #{orderChannel}, #{status}, #{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("INSERT INTO miaosha_order (user_id, order_id, goods_id) VALUES (#{userId}, #{orderId}, #{goodsId});")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("SELECT * FROM miaosha_order WHERE id = #{orderId} ")
    OrderInfo getById(long orderId);
}
