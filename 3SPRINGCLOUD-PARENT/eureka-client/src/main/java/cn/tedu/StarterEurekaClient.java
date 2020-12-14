package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//开启eureka客户端的注解
@EnableEurekaClient
public class StarterEurekaClient {
    public static void main(String[] args) {
        SpringApplication.run(StarterEurekaClient.class,args);
    }
}