package cn.tedu.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FanoutMode {
    //声明组件用到的名字
    private static final String TYPE="fanout";
    private static final String EXCHANGE=TYPE+"ex";
    private static final String Q1=TYPE+"q1";
    private static final String Q2=TYPE+"q2";
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
    //声明组件,声明绑定关系
    @Test
    public void declare() throws Exception {
        //声明队列
        channel.queueDeclare(Q1,false,false,false,null);
        channel.queueDeclare(Q2,false,false,false,null);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE,TYPE);//directex,direct
        //绑定关系
        channel.queueBind(Q1,EXCHANGE,"北京");
        channel.queueBind(Q1,EXCHANGE,"广州");
        channel.queueBind(Q2,EXCHANGE,"上海");
    }
    //生产端
    @Test
    public void send() throws IOException {
        channel.basicPublish(
                EXCHANGE,
                "北京",
                null,
                "".getBytes());
    }
}
