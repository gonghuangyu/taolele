package cn.tedu.product.controller;

import cn.tedu.product.mapper.ProductMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.SysResult;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CreateIndex {
    @Autowired(required = false)
    private ProductMapper productMapper;
    @Autowired
    private TransportClient client;
    //创建批量索引
    @RequestMapping("/index/create")
    public SysResult indexCreate(){
        //创建索引
        String indexName="easymall";
        String type="product";
        /* 1 判断是否存在,存在直接使用,不存在创建
           2 读取数据库数据start=0 rows=100
           3 将数据解析成json 放到索引
         */
        IndicesAdminClient indices = client.admin().indices();
        //存在
        IndicesExistsResponse existsResponse
                = indices.prepareExists(indexName).get();
        if(!existsResponse.isExists()){
            indices.prepareCreate(indexName).get();
        }
        //读数据源
        List<Product> products =
                productMapper.selectProductByPage
                        (0, 100);
        for (Product product : products) {
            try{
                String json = MapperUtil
                        .MP.writeValueAsString(product);
                IndexRequestBuilder
                        docRequest
                        = client.prepareIndex
                        (indexName, type,
                                product.getProductId());
                docRequest.setSource(json).get();
            }catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        return SysResult.ok();
    }
}
