package com.self.framework.message.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    /************************ queue 区域 **********************/
    public static final String QUEUE_TEST_01_CODE = "topic.queue_test_01";
    public static final String QUEUE_TEST_02_CODE = "topic.queue_test_02";

    public static final String QUEUE_TEST_FANOUT_A_CODE = "fanout.A";
    public static final String QUEUE_TEST_FANOUT_B_CODE = "fanout.B";
    public static final String QUEUE_TEST_FANOUT_C_CODE = "fanout.C";
    /*********************************************************/

    /************************ exchange 区域 **********************/
    public static final String EXCHANGE_TEST_01_CODE = "exchange01";
    public static final String EXCHANGE_TEST_02_CODE = "exchange02";
    /*********************************************************/

    @Bean(name = "test01")
    public Queue queue01() {
        return new Queue(QUEUE_TEST_01_CODE);
    }

    @Bean(name = "test02")
    public Queue queue02() {
        return new Queue(QUEUE_TEST_02_CODE);
    }

    @Bean(name = "a")
    public Queue queueA() {
        return new Queue(QUEUE_TEST_FANOUT_A_CODE);
    }

    @Bean(name = "b")
    public Queue queueB() {
        return new Queue(QUEUE_TEST_FANOUT_B_CODE);
    }

    @Bean(name = "c")
    public Queue queueC() {
        return new Queue(QUEUE_TEST_FANOUT_C_CODE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_TEST_01_CODE);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_TEST_02_CODE);
    }

    @Bean
    Binding bindingExchangeMessage01(@Qualifier("test01") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.#");//*表示一个词,#表示零个或多个词
    }

    @Bean
    Binding bindingExchangeMessage02(@Qualifier("test02") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(QUEUE_TEST_02_CODE);//*表示一个词,#表示零个或多个词
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("a") Queue a,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(a).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("b") Queue b, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(b).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(@Qualifier("c") Queue c, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(c).to(fanoutExchange);
    }
}
