package cn.tedu.es;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransportClientTest {
    //可以使用http协议的客户端RestTemplate发送请求操作es
    @Test
    public void test(){
        //新增索引
        String url="http://10.9.118.11:9200/index01";
        RestTemplate template=new RestTemplate();
        //发起put
        template.put(url,null);
    }
    /*
        初始化一个连接对象TransportClient
     */
    private TransportClient client=null;
    @Before
    public void init() throws UnknownHostException {
        //当client连接的es集群名字叫做elasticsearch
        //如果不是elasticsearch
        Settings myes = Settings.builder().put("cluster.name", "elasticsearch").build();
        client=new PreBuiltTransportClient(myes);
        //提供可以连接的若干个集群节点，随机挑选可连接的使用
        TransportAddress address=
                new InetSocketTransportAddress(
                        InetAddress.getByName("10.9.8.96"),9300
                );
        client.addTransportAddress(address);
    }
    /*
        索引管理
     */
    @Test
    public void indexAdmin() throws ExecutionException, InterruptedException {
        //先从client获取管理索引的对象
        IndicesAdminClient indices = client.admin().indices();
        //put /indexName
        /*CreateIndexRequest request=new CreateIndexRequest();
        request.index("index02");
        ActionFuture<CreateIndexResponse>
                createIndexResponseActionFuture
                = indices.create(request);
        CreateIndexResponse createIndexResponse
                = createIndexResponseActionFuture.get();
        //{"acknowledged":true,"shards-acknowledged":true}
        createIndexResponse.isShardsAcked();
        createIndexResponse.isAcknowledged();
        createIndexResponse.remoteAddress();*/
        //CreateIndexRequestBuilder request = indices.prepareCreate("index03");
        //CreateIndexResponse createIndexResponse = request.get();//请求才能发送出去
        //indices.prepareDelete("index01","index02").get();
        AnalyzeRequestBuilder request = indices.prepareAnalyze("index03", "中华人民共和国");
        AnalyzeResponse response = request.setAnalyzer("ik_max_word").get();
        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
        for (AnalyzeResponse.AnalyzeToken token : tokens) {
            String term = token.getTerm();
            System.out.println(term);
        }
        //判断索引存在 prepareExist
        IndicesExistsResponse response1 = indices.prepareExists("index03").get();
        System.out.println("index03是否存在为:"+response1.isExists());
    }
    //对文档的管理
    @Test
    public void docAdmin(){
        //新增文档 curl -XPUT -d {"name":"王翠花"}
        //http://10.9.8.96:9200/index01/article/1
        String doc1=
                "{\"id\":1,\"title\":\"java编程思想\",\"content\":\"这是一本工具书\"}";
        IndexRequestBuilder request1 = client.prepareIndex("index01", "article", "1");//获取一个操作index/type/id的文档资源request
        IndexResponse response1 = request1.setSource(doc1).get();
        response1.getId();
        response1.getIndex();
        //type version
        //获取文档
        GetRequestBuilder request2 = client.prepareGet("index01", "article", "1");
        GetResponse response2 = request2.get();
        //从response2解析数据
        String json = response2.getSourceAsString();
        System.out.println(json);
        //删除文档
        //DeleteRequestBuilder request3 = client.prepareDelete();
        client.prepareDelete("index01","article","1").get();
    }
    //搜索功能
    /*
        Query对象创建
        传递分页参数(底层一样是浅查询)
        获取返回结果使用
     */
    @Test
    public void search(){
        //fuzzyquery构造查询条件 trump tramp trimp tremp 孔子曰 孔子日
        //wildcardquery 通配 词项 title:编程 title:编纂,title:编辑
        //title:编?
        //{"query":{"match":{"title":"java编程思想"}}}
        MatchQueryBuilder query = QueryBuilders.matchQuery("title", "java编程思想");
        SearchRequestBuilder request = client.prepareSearch("index01");
        //from=start 起始下表
        //size=rows 查询分页每页条数
        request.setQuery(query).setFrom(0).setSize(5);
        SearchResponse searchResponse = request.get();
        //从response解析我的数据
        /*
            hit:{
                "total":58
                "max_score":1
                "hit":[{_index,_type,_id,_source},{},{},{},{}]
            }
         */
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit hit : hits) {
            hit.getId();
            hit.getType();
            hit.getIndex();
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }

    }
}
