package cn.tedu;

import cn.tedu.bean.Bean1;
import cn.tedu.config.MyConfig1;
import javafx.application.Application;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Run {
    @Test
    public void test1(){
        ApplicationContext context=
            new ClassPathXmlApplicationContext("app.xml");
        context.getBean(Bean1.class);

    }
    @Test
    public void test2(){
        ApplicationContext context=
            new AnnotationConfigApplicationContext(MyConfig1.class);
        context.getBean(Bean1.class);
    }
}
