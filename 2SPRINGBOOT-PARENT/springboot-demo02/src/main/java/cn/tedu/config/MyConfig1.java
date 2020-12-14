package cn.tedu.config;

import cn.tedu.bean.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"cn.tedu.bean","cn.tedu.condition"})//不添加扫描base-packages会将当前配置类所在
//包作为base-packages使用
@Import({MyConfig2.class})
public class MyConfig1 {
    public MyConfig1(){
        System.out.println("config1被容器加载了");
    }
    //一个方法上添加了@Bean注解,这个方法中返回的对象
    //将会交给spring容器管理 相当于一个xml中<bean>
    //<bean id="方法名称" class="返回值类型"/>
    //如果不想使用方法名作为id 自定义 @Bean("id值")
    @Bean("bean1")
    public Bean1 initBean1(){
        return new Bean1();
    }
}
