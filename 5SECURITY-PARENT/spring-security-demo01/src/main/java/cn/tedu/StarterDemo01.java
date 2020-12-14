package cn.tedu;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tedu.mapper")
public class StarterDemo01 {
    public static void main(String[] args) {
        SpringApplication.run(StarterDemo01.class,args);
    }
}
