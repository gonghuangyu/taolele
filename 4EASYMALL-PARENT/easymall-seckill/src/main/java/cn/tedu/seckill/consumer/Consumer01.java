package cn.tedu.seckill.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer01 {

    @RabbitListener(queues = {"simple01"})
    public void consume(String msg){
        //拿到消息后，执行消费逻辑
        System.out.println("消息者接收到消息:"+msg);
    }
}
