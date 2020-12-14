package cn.tedu;

import cn.tedu.controller.HelloController;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.logging.ClasspathLoggingApplicationListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

//springboot启动类核心注解
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cn.tedu"})
@MapperScan("cn.tedu.mapper")
public class StarterDemo01 {
    //springboot启动的入口方法
    public static void main(String[] args) {
        ApplicationContext context=
                SpringApplication.run(StarterDemo01.class,args);
        context.getBean(HelloController.class);
    }
}
