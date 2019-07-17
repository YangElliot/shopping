package com.qf.v13itemweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ItemWebApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void creatHTML() throws IOException, TemplateException {
        //1.模板
        Template template = configuration.getTemplate("hello.ftl");
        //2.数据
        Map<String,Object> data = new HashMap<>();
        data.put("username","java1902");
        //3.输出
        FileWriter out = new FileWriter("E:\\Java\\V13项目\\v13-web\\v13-item-web\\src\\main\\resources\\static\\hello.html");
        //4.集结
        template.process(data,out);
        System.out.println("success!");
    }

}
