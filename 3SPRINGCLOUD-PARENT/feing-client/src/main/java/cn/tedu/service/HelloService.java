package cn.tedu.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实现过程交给feign,实现逻辑的目的,是通过调用api方法sayHi底层
 * restTemplate访问 http://service-hi/client/hello?name={name}
 * 添加feign的注解和springmvc的注解,使得
 */
@FeignClient(value="service-hi",fallback = HelloServiceImpl.class)
public interface HelloService {
    //每一个抽象方法都对应一个后端微服务接口/client/hello
    @RequestMapping(value="/client/hello",method= RequestMethod.GET)
    String sayHi(@RequestParam("name") String name);

    @RequestMapping(value="/hello/{name}",method=RequestMethod.GET)
    String sayHello(@PathVariable("name") String name);
}
