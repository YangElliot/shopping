package com.qf.miaosha.controller;

import com.qf.miaosha.entity.TProduct;
import com.qf.miaosha.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/7/8 21:45
 */
@Controller
@RequestMapping("product")

public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("getById")
    public String getById(Long id, Model model){
        TProduct product =  productService.getById(id);
        model.addAttribute("product",product);
        return "detail";
    }

}
