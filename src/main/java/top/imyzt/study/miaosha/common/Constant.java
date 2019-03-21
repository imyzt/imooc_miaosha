package top.imyzt.study.miaosha.common;

/**
 * @author imyzt
 * @date 2019/3/20 11:22
 * @description Constant
 */
public interface Constant {

    String DEFAULT_QUEUE_NAME = "queue";

    String TOPIC_QUEUE_1 = "topic.queue.1";
    String TOPIC_QUEUE_2 = "topic.queue.2";
    String TOPIC_EXCHANGE = "topicExchange";
    String ROUTING_KEY_1 = "topic.key1";
    String ROUTING_KEY_2 = "topic.#";

    String FANOUT_QUEUE_1 = "fanout.queue.1";
    String FANOUT_QUEUE_2 = "fanout.queue.2";
    String FANOUT_EXCHANGE = "fanoutExchange";

    String HEADERS_QUEUE = "headers.queue";
    String HEADERS_EXCHANGE = "headersExchange";

    String MIAOSHA_QUEUE = "miaosha.queue";
}
