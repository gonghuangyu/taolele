package cn.tedu;

import com.sun.tools.javadoc.Start;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*@SpringBootConfiguration
@ComponentScan
@EnableAutoConfiguration*/
@SpringBootApplication
@MapperScan("cn.tedu.mapper")
public class StarterOU {
    public static void main(String[] args) {
        SpringApplication.run(StarterOU.class,args);
    }
}
