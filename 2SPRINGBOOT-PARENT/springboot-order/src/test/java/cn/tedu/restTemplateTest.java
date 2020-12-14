package cn.tedu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class restTemplateTest {
    /*public static void main(String[] args) {
        //发送请求到https://www.baidu.com/
        RestTemplate restTemplate=new RestTemplate();
        String url="https://www.baidu.com";
        String bodyStr =
                restTemplate.getForObject(url, String.class);
        *//*
            参数解析
            url:请求路径
            responseType:请求响应体数据类型,可以是接收String,如果
            响应体内容是json,可以使用对象对应json接收{"points":166666}
            Point.class
         *//*
        System.out.println(bodyStr);
    }*/
    public static void main(String[] args) {
        //localhost:8098/user/query/point
        RestTemplate template=new RestTemplate();
        String userId="1";
       /* String url="http://localhost:8098/user/query/point?userId="+userId;
        Point bodyStr = template.getForObject(url, Point.class);*/
       //第二种携带参数方式,可变参数
        /*String url="http://localhost:8098/user/query/point?" +
                "userId={1}&haha={2}&kaka={3}";
        Point bodyStr = template.getForObject(url, Point.class,
                "1", "王翠花", "38");*/
        //第三种携带参数方式,map对象
        /*String url="http://localhost:8098/user/query/point?" +
                "userId={userId}&haha={name}&kaka={age}";
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("userId","1");
        param.put("name","刘首付");
        param.put("age","28");
        //Point bodyStr = template.getForObject(url, Point.class, param);
        Point bodyStr=template.postForObject(url, null,Point.class, param);
        System.out.println(bodyStr.getPoints());*/
        //准备一个HttpEntity封装请求头
        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization","haha");
        //key1=value&key2=value2
        MultiValueMap<String,Object> param=new LinkedMultiValueMap();
        param.add("userId","1");
        //url?userId=1&name=刘首付&age=28
        HttpEntity entity=new HttpEntity(param,headers);
        String url="http://localhost:8098/user/query/point";
        ResponseEntity<Point> response
                = template.exchange(url, HttpMethod.POST, entity, Point.class);
       /* HttpHeaders headers1 = response.getHeaders();
        headers1.containsKey()*/
        Point body = response.getBody();
        System.out.println(body.getPoints());

    }
}
