package cn.tedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//开启注册中心所有功能注解
@EnableEurekaServer
public class StarterEurekaServer {
    public static void main(String[] args) {
        SpringApplication.run
                (StarterEurekaServer.class,args);
    }
}
