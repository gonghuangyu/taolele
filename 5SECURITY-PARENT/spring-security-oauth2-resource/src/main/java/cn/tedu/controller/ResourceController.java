package cn.tedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @RequestMapping("/home")
    public String sayHi(String name){
        return "hello "+name+" resource " ;
    }
}
