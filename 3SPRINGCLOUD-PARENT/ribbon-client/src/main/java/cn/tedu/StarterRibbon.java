package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class StarterRibbon {
    public static void main(String[] args) {
        SpringApplication.run(StarterRibbon.class,args);
    }
    @Bean
    @LoadBalanced//负载均衡
    //RestTemplate 将会经过ribbon的负载均衡计算
    //调用微服务
    public RestTemplate initTemplate(){
        return new RestTemplate();
    }
}
