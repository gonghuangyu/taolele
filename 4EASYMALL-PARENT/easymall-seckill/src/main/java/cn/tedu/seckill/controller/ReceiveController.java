package cn.tedu.seckill.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiveController {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RequestMapping("/receive/simple01")
    public Object receive(){
        Object simple01 = rabbitTemplate
                .receiveAndConvert("simple01");
        return simple01;
    }
}
