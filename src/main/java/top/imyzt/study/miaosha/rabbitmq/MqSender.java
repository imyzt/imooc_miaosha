package top.imyzt.study.miaosha.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.common.Constant;
import top.imyzt.study.miaosha.rabbitmq.dto.MiaoshaMessage;
import top.imyzt.study.miaosha.utils.ConvertUtil;

import java.util.HashMap;

/**
 * @author imyzt
 * @date 2019/3/20 11:21
 * @description mq消息发送服务
 */
@Service
@Slf4j
public class MqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void miaoshaSender(MiaoshaMessage message) {
        String msg = ConvertUtil.beanToStr(message);
        log.debug("miaosha sender={}", msg);
        amqpTemplate.convertAndSend(Constant.MIAOSHA_QUEUE, msg);
    }

    public void sender(Object message) {
        String msg = ConvertUtil.beanToStr(message);
        log.info("sender msg={}", msg);
        amqpTemplate.convertAndSend(Constant.DEFAULT_QUEUE_NAME, msg);
    }

    public void topicSender(Object message) {
        String msg = ConvertUtil.beanToStr(message);
        log.info("sender msg={}", msg);
        amqpTemplate.convertAndSend(Constant.TOPIC_EXCHANGE, Constant.ROUTING_KEY_1, msg);
        amqpTemplate.convertAndSend(Constant.TOPIC_EXCHANGE, Constant.ROUTING_KEY_2, msg);
    }

    public void fanoutSender(Object message) {
        String msg = ConvertUtil.beanToStr(message);
        log.info("sender msg={}", msg);
        amqpTemplate.convertAndSend(Constant.FANOUT_EXCHANGE,"", msg);
        amqpTemplate.convertAndSend(Constant.FANOUT_EXCHANGE, "", msg);
    }

    public void headersSender(Object message) {
        String msg = ConvertUtil.beanToStr(message);
        log.info("sender msg={}", msg);

        HashMap<String, Object> hashMap = new HashMap<String, Object>(2){{
            put("header1", "value1");
            put("header2", "value2");
        }};
        MessageHeaders messageHeaders = new MessageHeaders(hashMap);
        Message <Object> obj = new GenericMessage<>(msg.getBytes(), messageHeaders);

        amqpTemplate.convertAndSend(Constant.HEADERS_EXCHANGE,"", obj);
    }

}
