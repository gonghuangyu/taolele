package cn.tedu.cn.tedu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    //client/hello 客户端被调用的一个功能
    //传递一个name 放回当前系统的端口号
    @Value("${server.port}")
    private String port;

    @RequestMapping(value="/client/hello",method = RequestMethod.GET)
    public String sayHi(@RequestParam("name")String name){
        return "hello "+name+",I am from "+port;
    }
    @RequestMapping(value="/hello/{name}",method=RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name){
        return "hello "+name+",I am from "+port;
    }
}
