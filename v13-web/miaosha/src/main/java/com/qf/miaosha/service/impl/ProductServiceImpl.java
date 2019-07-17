package com.qf.miaosha.service.impl;

import com.qf.miaosha.entity.TProduct;
import com.qf.miaosha.mapper.TProductMapper;
import com.qf.miaosha.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/7/8 21:46
 */
@Service
public class ProductServiceImpl implements IProductService {


    @Autowired
    private TProductMapper productMapper;


    @Override
    @Cacheable(value = "product",key = "#id")
    public TProduct getById(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
