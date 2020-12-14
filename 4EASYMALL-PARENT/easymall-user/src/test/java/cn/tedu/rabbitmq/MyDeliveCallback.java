package cn.tedu.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;

public class MyDeliveCallback implements DeliverCallback {
    private Channel channel;
    public MyDeliveCallback(){

    }
    public MyDeliveCallback(Channel channel){
        this.channel=channel;
    }
    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        //接收到消息后,可以调用的方法,执行消费逻辑
                /*
                    consumerTag:消费端的表示id
                    message:消息对象
                 */

        AMQP.BasicProperties properties = message.getProperties();
        System.out.println(new String(message.getBody()));
        channel.queueDelete("simple01");
        //autoAck自动确认是false说明需要在执行消费正常结束后,回执确认
        /*channel.basicAck(message.getEnvelope().getDeliveryTag(),
                false);*/
    }
}
