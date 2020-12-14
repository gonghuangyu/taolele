package cn.tedu.controller;

import cn.tedu.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    /*
        用户积分查询
        请求地址:/user/query/point
        请求参数:String userId
        返回数据:String json字符串,字符串属性必须
        携带points
        Point{
            private Integer points;
        }
     */
    @Autowired
    private UserServiceImpl userService;
    @RequestMapping("/user/query/point")
    public String myPoints(String userId){
        //控制层,业务层方法 名字一般都是业务逻辑
        Integer points=userService.myPoints(userId);
        return "{\"points\":"+points+"}";
        //{"points":66000}
    }

    //负载均衡测试区分
    @Value("${server.port}")
    public String port;
    @RequestMapping("/port")
    public String getPort(String name){
        return "hello "+name+",i am from "+port;
    }
}
