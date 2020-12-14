package cn.tedu.controller;

import cn.tedu.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    /*
        请求地址:/order/pay
        请求参数:String orderId
        返回数据:1/0
     */
    @Autowired
    private OrderServiceImpl orderService;
    @RequestMapping("/sdfsqdfds/pay/sdfsdaf/asdfdas")
    public Integer payOrder(String orderId){
        try{
            orderService.payOrder(orderId);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
