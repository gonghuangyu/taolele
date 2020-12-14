package cn.tedu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StarterConfigClient {
    @Value("${host}")
    private String host;

    public static void main(String[] args) {

        SpringApplication.run(StarterConfigClient.class,args);
    }
    @RequestMapping("/host")
    public String host(){
        return host;
    }


}
