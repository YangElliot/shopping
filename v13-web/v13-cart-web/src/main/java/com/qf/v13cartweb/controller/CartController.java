package com.qf.v13cartweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.ICartService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/7/2 11:43
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean add(@PathVariable("productId") Long productId,
                          @PathVariable("count") Integer count,
                          @CookieValue(name = "user_cart",required = false) String uuid,
                          HttpServletResponse response,
                          HttpServletRequest request){

        TUser currentUser = (TUser) request.getAttribute("user");

        //1判断当前购物车是否为空对象
        if(uuid == null || "".equals(uuid)){
            uuid = UUID.randomUUID().toString();
        }

        //2将商品添加至购物车
        ResultBean resultBean = cartService.add(uuid, productId, count);

        //3判断
        if("200".equals(resultBean.getStatusCode())){
            flashCookie(uuid, response);
        }
        return resultBean;
    }

    @RequestMapping("query")
    @ResponseBody
    public ResultBean query(@CookieValue(name = "user_cart",required = false) String uuid,
                            HttpServletResponse response){

        if(uuid == null || "".equals(uuid)){
            return new ResultBean("404","购物车为空");
        }

        ResultBean resultBean = cartService.query(uuid);

        if("200".equals(resultBean.getStatusCode())){
            flashCookie(uuid,response);
        }

        return resultBean;

    }
    @RequestMapping("del/{productId}")
    @ResponseBody
    public ResultBean del(@CookieValue(name = "user_cart",required = false) String uuid,
                          @PathVariable("productId") Long productId,
                          HttpServletResponse response){
        //1.判断
        if (uuid == null || "".equals(uuid)){
            return new ResultBean("404","购物车为空");
        }
        //2.执行删除的操作
        ResultBean resultBean = cartService.del(uuid, productId);
        //3.
        //3.判断
        if("200".equals(resultBean.getStatusCode())){
            flashCookie(uuid, response);
        }
        return resultBean;
    }

    @RequestMapping("update/{productId}/{count}")
    @ResponseBody
    public ResultBean update(@CookieValue(name = "user_cart",required = false) String uuid,
                             @PathVariable("productId") Long productId,
                             @PathVariable("count") Integer count,
                             HttpServletResponse response){
        //1.判断
        if (uuid == null || "".equals(uuid)){
            return new ResultBean("404","购物车为空");
        }
        //2.执行删除的操作
        ResultBean resultBean = cartService.update(uuid,productId,count);
        //3.
        //3.判断
        if("200".equals(resultBean.getStatusCode())){
            flashCookie(uuid, response);
        }
        return resultBean;
    }




    private void flashCookie(@CookieValue(name = "user_cart", required = false) String uuid, HttpServletResponse response) {
        //4写cookie到客户端
        Cookie cookie =new Cookie("user_cart",uuid);
        cookie.setPath("/");
        //cookie.setDomain("qf.com");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7*24*60*60);
        //
        response.addCookie(cookie);
    }

}
