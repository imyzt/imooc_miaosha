package top.imyzt.study.miaosha.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.common.Constant;
import top.imyzt.study.miaosha.domain.MiaoshaOrder;
import top.imyzt.study.miaosha.domain.MiaoshaUser;
import top.imyzt.study.miaosha.domain.OrderInfo;
import top.imyzt.study.miaosha.rabbitmq.dto.MiaoshaMessage;
import top.imyzt.study.miaosha.result.CodeMsg;
import top.imyzt.study.miaosha.result.Result;
import top.imyzt.study.miaosha.service.GoodsService;
import top.imyzt.study.miaosha.service.MiaoshaService;
import top.imyzt.study.miaosha.service.OrderService;
import top.imyzt.study.miaosha.utils.ConvertUtil;
import top.imyzt.study.miaosha.vo.GoodsVo;

/**
 * @author imyzt
 * @date 2019/3/20 11:26
 * @description mq 接收端
 */
@Service
@Slf4j
public class MqReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RabbitListener(queues = Constant.MIAOSHA_QUEUE)
    public void miaoshaQueueReceiver(String message) {

        log.debug("miaoshaQueueReceiver msg={}", message);

        MiaoshaMessage miaoshaMessage = ConvertUtil.strToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        Long goodsId = miaoshaMessage.getGoodsId();

        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        if (null == goodsVo || goodsVo.getStockCount() <= 0) {
//            log.error("miaoshaQueueReceiver, 库存不足, u={}, g={}", user.getId(), goodsId);
            return;
        }

        // 判断是否重复下单
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (null != miaoshaOrder) {
//            log.error("miaoshaQueueReceiver, 重复秒杀, u={}, g={}", user.getId(), goodsId);
            return;
        }

        // 减库存,下订单 写入订单
        miaoshaService.miaosha(user, goodsVo);
        log.info("miaoshaQueueReceiver. 秒杀成功. userId={}, goodsId={}", user.getId(), goodsVo.getId());
    }

    @RabbitListener(queues = Constant.DEFAULT_QUEUE_NAME)
    public void listenerQueue(String message) {
        log.info("receiver DEFAULT_QUEUE_NAME msg={}", message);
    }

    @RabbitListener(queues = Constant.TOPIC_QUEUE_1)
    public void listenerTopicQueue1(String message) {
        log.info("receiver TOPIC_QUEUE_1 msg={}", message);
    }
    @RabbitListener(queues = Constant.TOPIC_QUEUE_2)
    public void listenerTopicQueue2(String message) {
        log.info("receiver TOPIC_QUEUE_2 msg={}", message);
    }

    @RabbitListener(queues = Constant.FANOUT_QUEUE_1)
    public void listenerFanoutQueue1(String message) {
        log.info("receiver FANOUT_QUEUE_1 msg={}", message);
    }
    @RabbitListener(queues = Constant.FANOUT_QUEUE_2)
    public void listenerFanoutQueue2(String message) {
        log.info("receiver FANOUT_QUEUE_2 msg={}", message);
    }

    @RabbitListener(queues = Constant.HEADERS_QUEUE)
    public void listenerHeadersQueue(byte[] message) {
        log.info("receiver HEADERS_QUEUE msg={}", message);
    }
}
