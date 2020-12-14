package cn.tedu.order.service;

import cn.tedu.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import com.jt.common.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired(required = false)
    private OrderMapper orderMapper;
    public void addOrder(Order order) {
        /*
            1 补全数据 orderId time paystate
            2 调用持久层写入数据
                2.1 业务层,单表处理
                2.2 持久层做多条写入
         */
        String orderId= UUID.randomUUID().toString();
        Date nowTime=new Date();
        order.setOrderId(orderId);
        order.setOrderTime(nowTime);
        /*//新增到t_order
        orderMapper.insertOrder(order);//
        //t_order_item 在order.getOrderItems();
        for(OrderItem oi:order.getOrderItems()){
            oi.setOrderId(orderId);
            orderMapper.inserOrderItem(oi);
        }*/
        //在持久层组织以上相同功能的insert语句
        orderMapper.insertOrder(order);
    }

    public List<Order> queryMyOrders(String userId) {
        //查询主表 select * from t_order where user_id=#{}
        //查子表 select * from t_order_item where order_id=
        return orderMapper.selectOrdersByUserid(userId);
    }

    public void deleteOrder(String orderId) {
        orderMapper.deleteOrder(orderId);
    }
}
