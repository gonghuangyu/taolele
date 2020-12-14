package cn.tedu.rabbitmq;

import com.rabbitmq.client.CancelCallback;

import java.io.IOException;

public class MyCancelCallback implements CancelCallback {
    @Override
    public void handle(String consumerTag) throws IOException {
        System.out.println("消费端:"+consumerTag+"被关闭了");
    }
}
