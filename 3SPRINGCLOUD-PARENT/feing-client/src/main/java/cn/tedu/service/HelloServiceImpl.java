package cn.tedu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "hello "+name;
    }

    @Override
    public String sayHello(String name) {
        return null;
    }
}
