package com.qf.v13searchweb.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.ISearchService;
import com.qf.v13.common.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/21 11:55
 */
@Component
@RabbitListener(queues = RabbitMQConstant.PRODUCT_SEARCH_QUEUE)
public class ProductHandler {


    @Reference
    private ISearchService searchService;

    @RabbitHandler
    public void processAdd(Long id){
        System.out.println(id);
        searchService.synDataById(id);
    }
}
