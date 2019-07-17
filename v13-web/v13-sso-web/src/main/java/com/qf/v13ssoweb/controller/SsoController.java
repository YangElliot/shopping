package com.qf.v13ssoweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.v13.api.IUserService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/27 16:31
 */
@Controller
@RequestMapping("sso")
public class SsoController {

    @Reference
    private IUserService userService;

    //登陆认证接口
    @RequestMapping("checkLogin")
    public String checkLogin(TUser user,HttpServletResponse response){

        ResultBean resultBean =userService.checkLogin(user);

        if("200".equals(resultBean.getStatusCode())){
            //cookie(user_token---uuid)
            //1.创建
            Cookie cookie=new Cookie("user_token",resultBean.getData().toString());
            cookie.setPath("/");
            //只能通过后端的方式访问这个cookie
            cookie.setHttpOnly(true);
            //将这个cookie写到客户端
            response.addCookie(cookie);
            //return "redirect:http://localhost:9091/index/home";
            return "index";
        }
        return "index";
    }

    @RequestMapping("checkIsLogin")
    @ResponseBody
    @CrossOrigin(origins = "*",allowCredentials = "true")
    public ResultBean checkIsLogin(HttpServletRequest request){
        Cookie[] cookie = request.getCookies();
        if(cookie != null){
            for (Cookie c : cookie) {
                if("user_token".equals(c.getName())){
                    String uuid = c.getValue();
                    return userService.checkIsLogin(uuid);
                }
            }
        }
        return new ResultBean("404",null);
    }

    @RequestMapping("checkIsLoginJsonp")
    @ResponseBody
    public String checkIsLoginJsonp(@CookieValue(name = "user_token",required = false) String uuid,
                                    String callback) throws JsonProcessingException {

        ResultBean resultBean = null;
        if(uuid != null){
             resultBean = userService.checkIsLogin(uuid);

        }else {
            resultBean = new ResultBean("404", null);
        }
        //将对象转换为json
        ObjectMapper objectMapper=new ObjectMapper();
        String json = objectMapper.writeValueAsString(resultBean);
        return callback+"("+json+")";
    }



    @RequestMapping("loginOut")
    public ResultBean loginOut(@CookieValue(name = "user_token",required = false) String uuid,
                               HttpServletResponse response){
        if(uuid != null){
            //删除cookie
            Cookie cookie = new Cookie("user_token",uuid);
            cookie.setPath("/");
            //设置为0 表示删除
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            //删除redis 凭证
            return userService.loginOut(uuid);

        }
        return new ResultBean("404","注销失败");

    }
}
