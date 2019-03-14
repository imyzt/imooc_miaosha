package top.imyzt.study.miaosha.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.imyzt.study.miaosha.domain.Goods;

import java.util.Date;

/**
 * @author imyzt
 * @date 2019/3/13 15:57
 * @description 秒杀商品信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsVo extends Goods {

    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
