package cn.tedu.rabbitmq;

import com.rabbitmq.client.*;
import com.rabbitmq.client.impl.AMQBasicProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 实现一个生产端发送消息
 * 一个消费端接收消息
 */
public class SimpleMode {
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
                "simple01",
                false,false,
                false,
                null);
        /*
            queue:String类型,队列名字
            durable:Boolean类型,是否持久化,rabbitmq重启queue是否恢复
            exclusive:Boolean类型,是否专属于当前链接
            autoDelete:Boolean类型,是否在最后消费者断开链接后,自动删除
            arguments:Map<String,Object>
         */
    }
    //发送消息--生产端
    @Test
    public void send() throws IOException {
        String msg="我们都是一家人";
        //生产端绝对不会把消息直接发给队列.发给交换机,携带路由key
        AMQP.BasicProperties properties=new AMQP.BasicProperties();
        channel.basicPublish
                ("",
                        "ercv234cv34gbsa",properties,msg.getBytes());
        System.out.println("发送消息成功");

        //

        //AMQP default作用,就是绑定一切声明的交换机,以队列名字作为路由key
        /*
            exchange:交换机名字
            routingKey:消息携带的路由key需要和队列的路由key匹配
            props:BasicProperties 类型,定义了消息携带的属性信息
         */
    }
    //消费端
    @Test
    public void consume() throws IOException {
        DeliverCallback myDeliver=new MyDeliveCallback(channel);
        CancelCallback myCancel=new MyCancelCallback();
        //channel调用消费代码
        channel.basicConsume(
                "ercv234cv34gbsa",
                true,
                myDeliver,
                myCancel);
        /*
            queue:使用当前消费逻辑监听的队列名称
            autoAck:是否自动确认
            deliver:处理消息的逻辑
            cancel:在处理消息逻辑中,可以在处理完成时,关闭消费端,cancel中代码
            才会被调用
         */

    }

}
