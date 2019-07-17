package com.qf.v13.api;

import com.github.pagehelper.PageInfo;
import com.qf.v13.common.base.IBaseService;
import com.qf.v13.entity.TProduct;
import com.qf.v13.pojo.TProductVO;

import java.util.List;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/11 19:38
 */
public interface IProductService extends IBaseService<TProduct> {
    //单独扩展特殊的方法
    //public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);
    public PageInfo<TProduct> page(Integer pageIndex,Integer pageSize);

    //返回新增的商品ID
    public Long save(TProductVO vo);

    //批量删除数据
    public Long batchDel(List<Long> ids);
}
