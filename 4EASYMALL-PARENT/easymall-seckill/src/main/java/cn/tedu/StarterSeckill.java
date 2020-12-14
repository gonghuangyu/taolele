package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.seckill.mapper")
public class StarterSeckill {
    public static void main(String[] args) {
        SpringApplication.run(StarterSeckill.class,args);
    }
    //将生命的所有组件，创建成springboot整合的bean对象
    @Bean
    public Queue q01(){
        return new Queue("seckill-queue",false,false,false,null);
    }
    @Bean
    public DirectExchange ex01(){
        return new DirectExchange("seckill-ex");
    }
    //绑定queue到队列
    @Bean
    public Binding bind01(){
        return BindingBuilder
                .bind(q01())
                .to(ex01())
                .with("seckill");
    }


}
