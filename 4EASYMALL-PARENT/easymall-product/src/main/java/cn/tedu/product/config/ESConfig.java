package cn.tedu.product.config;

import com.jt.common.pojo.Product;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * 实现TransportClient容器管理
 * @Bean初始化这个对象
 */
@Configuration
@ConfigurationProperties(prefix = "easymall.es")
//当前配置类中所有属性,都可以自动在properties配置文件中
//找到一个key值 =prefix+属性名称
public class ESConfig {
    //读取属性 @Value("${easymall.es.nodes}") 读取ip1:port1,ip2:port2集群list
    private List<String> nodes;//easymall.es.nodes=ip:port,ip:port
    private String clusterName;
    //nodes={"10.9.9.9:9300","10.9.9.8:9300"}
    //构造一个TransportClient对象
    @Bean
    public TransportClient initTransportClient() throws Exception {
        Settings settings =
                Settings.builder()
                        .put("cluster.name", clusterName).build();
        TransportClient client=new PreBuiltTransportClient(settings);
        for (String node : nodes) {
            //node=10.9.9.9:9300
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            TransportAddress address=
                    new InetSocketTransportAddress(
                            InetAddress.getByName(host),port
                    );
            client.addTransportAddress(address);
        }
        return client;
    }



    /*private Product product;//easymall.es.product.user.userName*/
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
