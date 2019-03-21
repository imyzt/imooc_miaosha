package top.imyzt.study.miaosha.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.imyzt.study.miaosha.common.Constant;

import java.util.HashMap;

/**
 * @author imyzt
 * @date 2019/3/20 11:21
 * @description RabbitMqConfig
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(Constant.MIAOSHA_QUEUE, true);
    }

    /**
     * Direct模式,exchange
     */
    @Bean
    public Queue queue() {
        return new Queue(Constant.DEFAULT_QUEUE_NAME, true);
    }

    /**
     * Topic模式, exchange
     * 订阅模式
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(Constant.TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(Constant.TOPIC_QUEUE_1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(Constant.TOPIC_QUEUE_2, true);
    }

    /*
     * 通过key, 将队列和交换机进行绑定
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(Constant.ROUTING_KEY_1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(Constant.ROUTING_KEY_2);
    }


    /**
     * Fanout模式, exchange
     * 广播模式
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Constant.FANOUT_EXCHANGE);
    }

    @Bean Queue fanoutQueue2() {
        return new Queue(Constant.FANOUT_QUEUE_2, true);
    }

    @Bean Queue fanoutQueue1() {
        return new Queue(Constant.FANOUT_QUEUE_1, true);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }


    /**
     * headers模式, exchange
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(Constant.HEADERS_EXCHANGE);
    }

    @Bean Queue headersQueue() {
        return new Queue(Constant.HEADERS_QUEUE, true);
    }

    @Bean
    public Binding headersBinding() {

        HashMap <String, Object> hashMap = new HashMap<String, Object>(2){{
            put("header1", "value1");
            put("header2", "value2");
        }};

        // 只有当满足key和value的时候, 才会向队列发送
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(hashMap).match();
    }

}
