package cn.tedu.service;

import cn.tedu.domain.Order;
import cn.tedu.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl {
    @Autowired(required = false)
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate template;
    public void payOrder(String orderId) {
        /*  1.利用orderId查询一个t_order表格行数据
        经过持久层封装,封装一个Order对象
            2.模拟支付,打桩:该订单支付金额**元
            3.金额计算积分 1倍/2倍
            4.userId将积分,添加到t_user表格的points字段中
         */
        Order order=orderMapper
                .selectOrderByOrderid(orderId);
        Double money=order.getOrderMoney();
        System.out.println("该订单支付金额:"+money+"元");
        //调用restTemplate方法,发送请求

        String url="http://point-service/user/update/point?" +
                "money={1}&userId={2}";
        template.getForObject(url,Integer.class,
                money,order.getUserId());
    }
}
