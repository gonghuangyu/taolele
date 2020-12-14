package cn.tedu.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({Condition1.class})
public class MyConfigByCondition {
    public MyConfigByCondition() {
        System.out.println("condition配置类条件满足,会被加载");
    }
}
