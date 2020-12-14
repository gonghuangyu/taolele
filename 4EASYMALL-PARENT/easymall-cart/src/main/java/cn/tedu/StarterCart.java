package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.cart.mapper")
@EnableFeignClients
public class StarterCart {
    public static void main(String[] args) {
        SpringApplication.run(StarterCart.class,args);
    }
    //补充代码,cart调用其他微服务,需要restTemplate
    @Bean
    @LoadBalanced
    public RestTemplate init(){
        return new RestTemplate();
    }
}