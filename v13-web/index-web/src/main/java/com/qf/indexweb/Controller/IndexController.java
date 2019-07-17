package com.qf.indexweb.Controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.IProductTypeService;
import com.qf.v13.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/15 16:00
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService productTypeService;


    @RequestMapping("home")
    public String showHome(Model model){
        //调用远程服务，获取商品类别的数据
        List<TProductType> list =productTypeService.list();
        model.addAttribute("list",list);
        return "home";
    }


    @RequestMapping("list")
    @ResponseBody
    public List<TProductType> list(){
        return productTypeService.list();
    }


}
