package cn.tedu.controller;

import cn.tedu.mapper.HelloMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired(required = false)
    private HelloMapper helloMapper;
    @RequestMapping("/hello")
    public String sayHi(String name){
        return "hello world "
                +name+
                ",welcome to springboot";
    }
    @RequestMapping("/get/points")
    public Integer getPoints(String userId){
        return helloMapper.selectPointsById(userId);
    }
}
