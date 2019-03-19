package top.imyzt.study.miaosha.vo;

import lombok.Data;
import top.imyzt.study.miaosha.domain.MiaoshaUser;

/**
 * @author imyzt
 * @date 2019/3/19 15:03
 * @description GoodsDetailVo
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private MiaoshaUser user;
}
