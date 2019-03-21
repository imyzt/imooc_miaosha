package top.imyzt.study.miaosha.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.imyzt.study.miaosha.domain.MiaoshaUser;

/**
 * @author imyzt
 * @date 2019/3/20 18:02
 * @description MiaoshaMessage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiaoshaMessage {
    private MiaoshaUser user;
    private Long goodsId;
}
