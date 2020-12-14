package cn.tedu.seckill.controller;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {
    //注入一个RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;
    //发送一个消息到队列
    @RequestMapping("/send")
    public String send(String msg){
        //将msg发送 到交换机 "" directex topicex fanoutex
        //send(Message message)
        MessageProperties properties=new MessageProperties();
        properties.setAppId("phone");
        properties.setUserId("guest");
        properties.setPriority(100);
        properties.setContentEncoding("UTF-8");
        Message message=new Message(msg.getBytes(),properties);
        //basicPublish("","simple01",props,msg.getBytes())
        rabbitTemplate.send("","simple01",message);
        //convertAndSend()
        rabbitTemplate.convertAndSend("simple01",msg);
        //Message simple01 = rabbitTemplate.receive("simple01");
        //Object simple011 = rabbitTemplate.receiveAndConvert("simple01");
        return "success";
    }
}
