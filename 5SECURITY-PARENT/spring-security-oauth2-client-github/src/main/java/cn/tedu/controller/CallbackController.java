package cn.tedu.controller;

import cn.tedu.domain.GithubUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallbackController {
    private static String tokenUrl="https://github.com/login/oauth/access_token";
    private static String userUrl="https://api.github.com/user";
    private static String clientId="31feb5e72b77c7ed6359";
    private static String clientSecret="e64de7271e8ee27c6edfd9f5756b8723d1cfc6b6";
    @RequestMapping("/callback")
    public GithubUser callback(String code){
        //使用code换token
        RestTemplate template=new RestTemplate();
        String url=tokenUrl+"?client_id={1}&client_secret={2}&code={3}";
        String tokenStr = template.postForObject(url, null, String.class, clientId, clientSecret, code);
        String token=obtainToken(tokenStr);
        System.out.println("获取到token:"+token);
        //使用token获取user信息 解析 获取属性login
        //携带在一个请求头header中 Authorization token token
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","token "+token);
        HttpEntity entity=new HttpEntity(headers);
        ResponseEntity<GithubUser> exchange = template.exchange(userUrl, HttpMethod.GET, entity, GithubUser.class);
        return exchange.getBody();
    }
    public String obtainToken(String tokenStr){
        return tokenStr.split("&")[0].split("=")[1];
    }
}
