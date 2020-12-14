package cn.tedu.controller;

import cn.tedu.service.DemoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DemoController {
    @Autowired
    private DemoServiceImpl demoService;
    /*
        请求地址 /get/data
        请求参数 Integer id 有null值
        返回数据 String username
        select username from user where id=#{id}
     */
    @RequestMapping(value="/get/data",method = RequestMethod.GET,produces = "text/html;charset=utf-8")
    public String getData(Integer id){
        String username = demoService.getData(id);
        return username;
    }
}
