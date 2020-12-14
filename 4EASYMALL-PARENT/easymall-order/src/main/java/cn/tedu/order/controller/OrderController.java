package cn.tedu.order.controller;

import cn.tedu.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order/manage")
public class OrderController {
    @Autowired
    private OrderService orderService;
    //新增订单
    @RequestMapping("/save")
    public SysResult addOrder(Order order){
        //order属性orderId paystate orderTime 缺少
        //userId orderMoney orderReceiverinfo
        //List<OrderItem> 缺少orderId
        try{
            orderService.addOrder(order);
            return SysResult.ok();
        }catch (Exception E){
            E.printStackTrace();
            return SysResult.build(
                    201,
                    "新增订单失败",
                    null);
        }
    }
    //查询我的订单
    @RequestMapping("/query/{userId}")
    public List<Order> queryMyOrders(@PathVariable String userId){
        return orderService.queryMyOrders(userId);
    }
    //删除我的订单
    @RequestMapping("/delete/{orderId}")
    public SysResult deleteOrder(@PathVariable String orderId){
        try{
            orderService.deleteOrder(orderId);
            return SysResult.ok();
        }catch (Exception E){
            E.printStackTrace();
            return SysResult.build(
                    201,
                    "删除失败",
                    null);
        }
    }
}
