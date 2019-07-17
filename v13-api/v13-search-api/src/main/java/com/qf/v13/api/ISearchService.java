package com.qf.v13.api;

import com.qf.v13.common.pojo.PageResultBean;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/17 11:45
 */
public interface ISearchService  {

    //全量同步
    public ResultBean synAllData();

    //增量同步
    public ResultBean synDataById(Long id);

    public ResultBean queryByKeywords(String keywords);

    public PageResultBean<TProduct> page(Integer pageIndex,String keywords);

}
