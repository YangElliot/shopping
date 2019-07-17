package com.qf.v13userservice;

import com.qf.v13.api.IUserService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {
        TUser user = new TUser();
        user.setUsername("admin");
        user.setPassword("123");
        ResultBean resultBean = userService.checkLogin(user);
        System.out.println(resultBean.getStatusCode());
        System.out.println(resultBean.getData());
    }


    @Test
    public void checkIsLogin(){
        ResultBean resultBean = userService.checkIsLogin("75691fa3-faf4-4c44-b84d-f3da0062076f");
        System.out.println(resultBean.getStatusCode());
        TUser user= (TUser) resultBean.getData();
        if(user != null){
            System.out.println(user.getUsername());
        }
    }

    @Test
    public void loginOutTest(){

    }

}
