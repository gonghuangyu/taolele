package cn.tedu.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    private RestTemplate template;
    //添加服务降级逻辑
    //如果当前sayHi方法失败,将会调用一个叫做error的方法实现服务降级
    @HystrixCommand(fallbackMethod = "error")
    public String sayHi(String name) {
        //目的负载均衡访问8091 8092 不能使用localhost:8091
        //也不能添加nginx的配置server http://www.ou.com/client/hello
        //要使用微服务名称service-hi访问
        String url="http://service-hi/client/hello?name={1}";
        return template.getForObject(url,String.class,name);
    }
    //除了方法名字和sayHi不同,参数,返回值要相同
    @HystrixCommand(fallbackMethod = "error2")
    public String error(String name){
        String url="http://service-hi/client/hello?name={1}";
        return template.getForObject(url,String.class,name);
        /*return "sorry "+name+" error happened";*/
    }
}
