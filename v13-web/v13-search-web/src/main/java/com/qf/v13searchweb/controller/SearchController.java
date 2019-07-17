package com.qf.v13searchweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v13.api.ISearchService;
import com.qf.v13.common.pojo.PageResultBean;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/17 19:59
 */
@Controller
@RequestMapping("search")
public class SearchController {
    @Reference
    private ISearchService searchService;

    @RequestMapping("queryBykeywords")
    public String queryByKeywords(String keywords, Model model){

        ResultBean resultBean = searchService.queryByKeywords(keywords);
        model.addAttribute("result",resultBean);
        return "list";
    }

    @RequestMapping("page/{keywords}/{pageIndex}")
    public String page(@PathVariable("keywords") String keywords,@PathVariable("pageIndex") Integer pageIndex,Model model){
        System.out.println(keywords);
        PageResultBean<TProduct> page = searchService.page(pageIndex, keywords);
        model.addAttribute("result",page);
        return "list";
    }

}
