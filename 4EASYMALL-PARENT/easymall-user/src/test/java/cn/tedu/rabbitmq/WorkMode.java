package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * 一发，多接争抢测试
 */
public class WorkMode {
    //链接对象 channel
    private Channel channel=null;
    @Before
    public void initChannel() throws Exception {
        //获取长连接
        ConnectionFactory factory=new ConnectionFactory();
        //设置链接属性 ip port user password
        factory.setHost("10.9.8.96");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
    }
    //无角色客户端:声明组件
    @Test
    public void declare() throws IOException {
        //只声明一个队列 如果存在队列,属性一致,则不报错,属性不一致报错
        //不存在,则声明
        channel.queueDeclare(
                "work01",
                false,false,
                false,
                null);
    }
    //发送消息--生产端
    @Test
    public void send() throws IOException {
        String msg="我们都是一家人";
        //生产端绝对不会把消息直接发给队列.发给交换机,携带路由key
        for(int i=0;i<101;i++){
            msg="我是第["+i+"]条消息";
            channel.basicPublish(
                    "",
                    "work01",
                    null,
                    msg.getBytes()
            );
        }
    }
    //消费者1
    @Test
    public void cosumer01() throws IOException {
        channel.basicConsume("work01", true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("消费者1:"+new String(message.getBody()));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
        while(true);
    }
    //消费者2
    @Test
    public void cosumer02() throws IOException {
        channel.basicConsume("work01", true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("消费者2:"+new String(message.getBody()));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });
        while(true);
    }
}
