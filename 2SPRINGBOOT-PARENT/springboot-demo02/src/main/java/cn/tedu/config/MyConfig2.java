package cn.tedu.config;

import cn.tedu.bean.Bean3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 {
    public MyConfig2() {
        System.out.println("config2被加载了");
    }
    @Bean("bean3")
    public Bean3 initBean3(){
        return new Bean3();
    }
}
