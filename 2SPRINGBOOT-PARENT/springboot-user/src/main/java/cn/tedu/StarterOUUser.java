package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration*/
@SpringBootApplication
@MapperScan("cn.tedu.mapper")
@EnableEurekaClient
public class StarterOUUser {
    public static void main(String[] args) {
        SpringApplication.run(StarterOUUser.class,args);
    }
}
