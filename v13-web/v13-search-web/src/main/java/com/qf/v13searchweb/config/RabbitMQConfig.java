package com.qf.v13searchweb.config;

import com.qf.v13.common.constant.RabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/21 11:46
 */
@Configuration
public class RabbitMQConfig {

    //声明队列
    @Bean
    public Queue initQueue(){
        return new Queue(RabbitMQConstant.PRODUCT_SEARCH_QUEUE,true,false,false);
    }

    //声明交换机 如果交换机存在，则不需要操作
    @Bean
    public TopicExchange initExchange(){
        return new TopicExchange(RabbitMQConstant.CENTER_PRODUCT_EXCHANGE);
    }

    //绑定交换机
    @Bean
    public Binding initBinding(Queue initQueue,TopicExchange initExchange){
        return BindingBuilder.bind(initQueue).to(initExchange).with("product-add");
    }
}
