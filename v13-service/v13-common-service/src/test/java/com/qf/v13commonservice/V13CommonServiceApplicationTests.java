package com.qf.v13commonservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.qf.v13.api.IEmailService;
import com.qf.v13.common.pojo.ResultBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13CommonServiceApplicationTests {

    @Autowired
    private IEmailService emailService;


    @Test
    public void contextLoads() {
        ResultBean resultBean = emailService.send("yrg71n9@163.com", "服务发布", "发布成功");
        System.out.println(resultBean.getStatusCode());
    }

}
