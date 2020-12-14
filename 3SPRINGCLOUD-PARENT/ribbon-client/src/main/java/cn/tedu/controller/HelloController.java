package cn.tedu.controller;

import cn.tedu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    /*
        请求地址:/ribbon/hello
        请求参数:name
        返回数据:"RIBBON"+service-hi返回值
     */
    @Autowired
    private HelloService helloService;
    @RequestMapping("ribbon/hello")
    public String sayHi(String name){
        return "RIBBON:"+helloService.sayHi(name);
    }
    @RequestMapping("/hello")
    public String sayHello(String name){
        return "hello "+name+",I am from ribbon";
    }
}
