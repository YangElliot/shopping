package com.qf.v13centerweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.api.ISearchService;
import com.qf.v13.common.constant.RabbitMQConstant;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.TProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/12 16:40
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private ISearchService searchService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id){
        return productService.selectByPrimaryKey(id);
    }

    @RequestMapping("list")
    public String list(Model model){
        //获取数据
        List<TProduct> list = productService.list();
        //保存数据
        model.addAttribute("list",list);
        //跳转页面
        return "product/list";
    }


    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize,
                       Model model){
        //Integer pageSize = 5;
        PageInfo<TProduct> page=productService.page(pageIndex,pageSize);
        model.addAttribute("page",page);
        return "product/list";
    }


    @PostMapping("add")
    public String add(TProductVO vo){
          Long id = productService.save(vo);

//        searchService.synDataById(id);

          rabbitTemplate.convertAndSend(RabbitMQConstant.CENTER_PRODUCT_EXCHANGE,"product-add",id);
          return "redirect:/product/page/1/5";
    }

    //统一规范放回数据的方式
    //json

    @PostMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id") Long id){
        int count = productService.deleteByPrimaryKey(id);
        if(count > 0){
            return new ResultBean("200","删除成功");
        }
        return new ResultBean("404","删除失败");
    }

    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(@RequestParam List<Long> ids){
        Long count = productService.batchDel(ids);
        if(count > 0){
            return new ResultBean("200","删除成功");
        }
        return new ResultBean("404","删除失败");
    }


}
