package cn.tedu.search.service;

import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SearchService {
    @Autowired
    private TransportClient client;
    public List<Product> search(String text, Integer page, Integer rows) {
        /*BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(query1)*/
        //query对象封装
        MatchQueryBuilder query
                = QueryBuilders.matchQuery("productName", text);
        SearchRequestBuilder request = client.prepareSearch("easymall");
        int start=(page-1)*rows;
        SearchResponse response =
                request.setQuery(query)
                       .setFrom(start)
                       .setSize(rows).get();
        //准备一个封装号返回结果的list对象
        List<Product> products=new ArrayList<>();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String json=hit.getSourceAsString();
            //转化成product对象
            try{
                Product product = MapperUtil.MP.readValue(json, Product.class);
                products.add(product);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return products;
    }
}
