package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration*/
@SpringBootApplication
@MapperScan("cn.tedu.mapper")
@EnableEurekaClient
public class StarterOUOrder {
    public static void main(String[] args) {

        SpringApplication.run(StarterOUOrder.class,args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }
}
