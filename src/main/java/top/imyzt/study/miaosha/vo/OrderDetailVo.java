package top.imyzt.study.miaosha.vo;

import lombok.Data;
import top.imyzt.study.miaosha.domain.OrderInfo;

/**
 * @author imyzt
 * @date 2019/3/19 16:39
 * @description OrderDetailVo
 */
@Data
public class OrderDetailVo {
    private OrderInfo order;
    private GoodsVo goods;
}
