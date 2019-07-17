package com.qf.v13productservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.v13.api.IProductService;
import com.qf.v13.common.base.BaseServiceImpl;
import com.qf.v13.common.base.IBaseDao;
import com.qf.v13.entity.TProduct;
import com.qf.v13.entity.TProductDesc;
import com.qf.v13.mapper.TProductDescMapper;
import com.qf.v13.mapper.TProductMapper;
import com.qf.v13.pojo.TProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/11 19:44
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private TProductDescMapper productDescMapper;

    @Override
    public IBaseDao<TProduct> getBaseDao() {
        return productMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        //设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //获取数据
        List<TProduct> list=list();
        //构建一个分页对象
        PageInfo<TProduct> pageInfo= new PageInfo<TProduct>(list);
        return pageInfo;
    }

    @Override
    @Transactional
    public Long save(TProductVO vo) {
         //保存商品的基本信息
        TProduct tProduct =vo.getProduct();
        tProduct.setFlag(true);
        //主键回填
        int count = productMapper.insert(tProduct);
        //保存商品的描述信息
        String productDesc = vo.getProductDesc();
        TProductDesc desc = new TProductDesc();
        desc.setProductDesc(productDesc);
        desc.setProductId(tProduct.getId());
        productDescMapper.insert(desc);
        //返回新增主键
        return tProduct.getId();
    }

    @Override
    public Long batchDel(List<Long> ids) {
        return productMapper.batchUpdateFlag(ids);
    }

    @Override
    public int deleteByPrimaryKey(Long id){

        TProduct product = new TProduct();
        product.setId(id);
        product.setFlag(false);
        return productMapper.updateByPrimaryKeySelective(product);
    }

}
