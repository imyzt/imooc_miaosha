package top.imyzt.study.miaosha.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import top.imyzt.study.miaosha.common.Constant;

/**
 * @author imyzt
 * @date 2019/3/20 11:26
 * @description mq 接收端
 */
@Service
@Slf4j
public class MqReceiver {


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
