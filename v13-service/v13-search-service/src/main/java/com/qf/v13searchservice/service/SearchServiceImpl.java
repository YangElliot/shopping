package com.qf.v13searchservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v13.api.ISearchService;
import com.qf.v13.common.pojo.PageResultBean;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import com.qf.v13.mapper.TProductMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: YangRuiGuang
 * @Date: 2019/6/17 17:15
 */
@Service
public class SearchServiceImpl implements ISearchService {


    @Autowired
    private TProductMapper productMapper;

    @Autowired
    private SolrClient solrClient;

    /**
     * 全量同步
     * @return
     */
    @Override
    public ResultBean synAllData() {
        //获取数据
        List<TProduct> list = productMapper.list();


        //将数据导入到索引库中
        for (TProduct product : list) {
            //product --> SolrInputDocument
            SolrInputDocument document = new SolrInputDocument();
            //唯一的标识符 文件里默认有id字段
            document.setField("id",product.getId());
            document.setField("product_name",product.getName());
            document.setField("product_price",product.getPrice());
            document.setField("product_images",product.getImages());
            document.setField("product_sale_point",product.getSalePoint());

            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404","数据提交失败");
            }

            try {
                solrClient.commit();
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("404","数据提交失败");
            }
        }

        return new ResultBean("200","数据提交成功");
    }

    /**
     * 增量同步
     * @param id
     * @return
     */
    @Override
    public ResultBean synDataById(Long id) {

        TProduct product = productMapper.selectByPrimaryKey(id);

        SolrInputDocument document = new SolrInputDocument();

        document.setField("id",product.getId());
        document.setField("product_name",product.getName());
        document.setField("product_price",product.getPrice());
        document.setField("product_images",product.getImages());
        document.setField("product_sale_point",product.getSalePoint());

        try {
            solrClient.add(document);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交失败");
        }

        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("404","数据提交失败");
        }

        return new ResultBean("200","数据提交成功");

    }

    @Override
    public ResultBean queryByKeywords(String keywords) {
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        //做判断
        if(StringUtils.isAllEmpty(keywords)){
            queryCondition.setQuery("*:*");
        }else {
            queryCondition.setQuery("product_keywords:"+keywords);
        }

        //新增高亮的效果
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        //2.执行查询 documentList

        try {
            QueryResponse response = solrClient.query(queryCondition);

            SolrDocumentList documents = response.getResults();

            //获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();


//            documents -> List<TProduct>

            List<TProduct> list = new ArrayList<>(documents.size());

            for (SolrDocument document : documents) {

                TProduct Product = new TProduct();
                Product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //Product.setName(document.getFieldValue("product_name").toString());
                Product.setImages(document.getFieldValue("product_images").toString());
                Product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                Product.setSalePoint(document.getFieldValue("product_sale_point").toString());


                //单独为高亮做配置
                Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                //获取商品名称的高亮信息
                List<String> productNameHigh = idHigh.get("product_name");
                if(productNameHigh == null || productNameHigh.isEmpty()){
                    Product.setName(document.getFieldValue("product_name").toString());
                }else {
                    Product.setName(productNameHigh.get(0));
                }

                list.add(Product);
            }

            return new ResultBean("200",list);

        } catch (SolrServerException|IOException e) {
            e.printStackTrace();
            return new ResultBean("404","查询结果集失败");
        }

    }

    @Override
    public PageResultBean<TProduct> page(Integer pageIndex, String keywords) {

        PageResultBean<TProduct> pageResultBean= new PageResultBean<TProduct>();

        pageResultBean.setPageSize(4);
        pageResultBean.setNavigatePages(5);
        //1.组装查询条件
        SolrQuery queryCondition = new SolrQuery();

        //做判断
        if(StringUtils.isAnyEmpty(keywords)){
            queryCondition.setQuery("*:*");
        }else {
            queryCondition.setQuery("product_keywords:"+keywords);
        }

        //新增高亮的效果
        queryCondition.setHighlight(true);
        queryCondition.addHighlightField("product_name");
        queryCondition.setHighlightSimplePre("<font color='red'>");
        queryCondition.setHighlightSimplePost("</font>");

        queryCondition.setStart(pageIndex);
        queryCondition.setRows(4);

        //2.执行查询 documentList

        try {
            QueryResponse response = solrClient.query(queryCondition);

            long pageNum = response.getResults().getNumFound();


            SolrDocumentList documents = response.getResults();

            //获取高亮的信息
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();


//            documents -> List<TProduct>

            List<TProduct> list = new ArrayList<>(documents.size());

            for (SolrDocument document : documents) {

                TProduct Product = new TProduct();
                Product.setId(Long.parseLong(document.getFieldValue("id").toString()));
                //Product.setName(document.getFieldValue("product_name").toString());
                Product.setImages(document.getFieldValue("product_images").toString());
                Product.setPrice(Long.parseLong(document.getFieldValue("product_price").toString()));
                Product.setSalePoint(document.getFieldValue("product_sale_point").toString());


                //单独为高亮做配置
                Map<String, List<String>> idHigh = highlighting.get(document.getFieldValue("id"));
                //获取商品名称的高亮信息
                List<String> productNameHigh = idHigh.get("product_name");
                if(productNameHigh == null || productNameHigh.isEmpty()){
                    Product.setName(document.getFieldValue("product_name").toString());
                }else {
                    Product.setName(productNameHigh.get(0));
                }

                list.add(Product);
                pageResultBean.setList(list);
            }

        } catch (SolrServerException|IOException e) {
            e.printStackTrace();
        }

        return pageResultBean;
    }


}
