package cn.tedu.controller;

import cn.tedu.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value="/user/query/point",method= RequestMethod.GET)
    public String myPoints(String userId){
        //控制层,业务层方法 名字一般都是业务逻辑
        Integer points=userService.myPoints(userId);
        return "{\"points\":"+points+"}";
        //{"points":66000}
    }
    /*
        用户积分更新接口
        地址:/user/update/point
        参数:Double money String userId
        返回:
            1 成功 0失败
     */
    @RequestMapping("/user/update/point")
    public Integer updatePoint(Double money,String userId){
        try{
            userService.updatePoint(money,userId);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    //负载均衡测试区分
    @Value("${server.port}")
    public String port;
    @RequestMapping("/port")
    public String getPort(String name){
        return "hello "+name+",i am from "+port;
    }
}
