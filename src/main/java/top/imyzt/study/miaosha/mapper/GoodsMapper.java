package top.imyzt.study.miaosha.mapper;

import org.apache.ibatis.annotations.Param;import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @author imyzt
 * @date 2019/3/13 15:59
 * @description GoodsMapper
 */
public interface GoodsMapper {

    /**
     * 查询秒杀商品列表
     * @return 秒杀商品列表
     */
    @Select("SELECT mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date, g.* " +
            "FROM miaosha_goods mg " +
            "LEFT JOIN goods g " +
            "ON mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("SELECT mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date, g.* " +
            "FROM miaosha_goods mg " +
            "LEFT JOIN goods g " +
            "ON mg.goods_id = g.id " +
            "WHERE g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);

    @Update("UPDATE miaosha_goods SET stock_count = stock_count - 1 WHERE goods_id = #{goodsId} ")
    int reduceStock(MiaoshaOrder miaoshaOrder);
}
