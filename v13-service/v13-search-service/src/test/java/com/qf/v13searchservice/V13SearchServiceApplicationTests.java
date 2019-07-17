package com.qf.v13searchservice;

import com.qf.v13.api.ISearchService;
import com.qf.v13.common.pojo.ResultBean;
import com.qf.v13.entity.TProduct;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13SearchServiceApplicationTests {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private ISearchService searchService;

    @Test
    public void synDataById(){
        ResultBean resultBean = searchService.synDataById(18L);
        System.out.println(resultBean.getStatusCode());

    }

    @Test
    public void queryByKeywordsTest(){
        ResultBean<List<TProduct>> resultBean = searchService.queryByKeywords("笔记本");
        List<TProduct> data = resultBean.getData();
        for (TProduct product : data) {
            System.out.println(product.getName());
        }
    }


    @Test
    public void synAllDataTest(){
        ResultBean resultBean = searchService.synAllData();
        System.out.println(resultBean.getStatusCode());
    }

    @Test
    public void add() throws IOException, SolrServerException {
        //以document为基本单位
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id",1);//唯一的标识符 文件里默认有id字段
        document.setField("product_name","华为手机");
        document.setField("product_price",3600);
        document.setField("product_images",123);
        document.setField("product_sale_point","全球限量");
        //保存信息到solr索引库中
        solrClient.add(document);
        //提交
        solrClient.commit();
        System.out.println("保存成功");
    }

    @Test
    public void queryTest() throws IOException, SolrServerException {
        //组装查询条件
        SolrQuery query= new SolrQuery();
        query.setQuery("product_name:华为");

        //执行查询
        QueryResponse response = solrClient.query(query);

        SolrDocumentList solrDocuments = response.getResults();

        for (SolrDocument document : solrDocuments) {
            System.out.println("id:"+document.get("id")+",name="+document.get("product_name"));
        }

    }

    @Test
    public void deleteAllTest() throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }

    @Test
    public void contextLoads() {

    }

}
