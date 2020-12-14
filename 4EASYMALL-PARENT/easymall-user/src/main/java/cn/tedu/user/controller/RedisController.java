package cn.tedu.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
    //测试RedisTemplate对象
    @Autowired
    private StringRedisTemplate redisTemplate;
    //访问 /redis 写测试代码 返回字符串
    @RequestMapping("/redis")
    public String redis(String key,String value){
        //直接使用redisTemplate对象可以操作redis的基础功能命令
        Boolean aBoolean = redisTemplate.hasKey(key);//exists key
        System.out.println("有没有:"+aBoolean);
        Boolean expire = redisTemplate.expire(key, 2, TimeUnit.DAYS);
        System.out.println("设置超时:"+expire);
        //pexpire key 1000*60*60*24*2
        Long expire1 = redisTemplate.getExpire(key);
        System.out.println("超时时间:"+expire1);
        redisTemplate.delete(key);

        //springboot中的template对象将操作redis的不同数据分开处理了
        //String
        ValueOperations<String, String> opsForValue
                = redisTemplate.opsForValue();
        opsForValue.set(key,value);
        //hash
        HashOperations<String, Object, Object> opsForHash
                = redisTemplate.opsForHash();
        opsForHash.put("user","age","18");//hset
        //list
        ListOperations<String, String> opsForList
                = redisTemplate.opsForList();
        opsForList.size(key);//llen
        //set
        //zset
        return "success";
    }
    //发送验证码
    @RequestMapping("/sendCode")
    public String sendCode(String phone){
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        ListOperations<String, String> opsForList = redisTemplate.opsForList();
        //准备好所有key
        String lock=phone+".lock";
        String list=phone+".list";
        String code=phone+".code";
        if(redisTemplate.hasKey(lock)){
            return "您的手机号已经被锁住了,等10分钟";
        }
        Long size = opsForList.size(list);
        //当前系统时间
        Long nowTime=new Date().getTime();
        if(size==3){
            //判断里面的元素是否和当前时间时间差和5分钟对比
            String firstTimeStr = opsForList.rightPop(list);
            Long firstTime=Long.parseLong(firstTimeStr);

            Long timeGap=nowTime-firstTime;
            if(timeGap<1000*60*5){
                //生成锁,将其他数据删除
                redisTemplate.delete(list);
                redisTemplate.delete(code);
                opsForValue.set(lock,"",10,TimeUnit.MINUTES);
                return "您的手机号,本次发送违反规定,锁住10分钟";
            }
        }
        //上述判断都结束,正常生成code
        int codeR = (int) Math.ceil(Math.random() * 9000 + 1000);
        opsForValue.set(code,codeR+"",15,TimeUnit.MINUTES);
        //在list将当前时间lpush进入
        opsForList.leftPush(list,nowTime+"");
        return "您的手机号成功生成验证码";
    }
    //使用验证码
    @RequestMapping("/verify/code")
    public String verifyCode(String phone,String inputCode){
        //判断phone是否生成过验证码
        String key=phone+".code";
        if(!redisTemplate.hasKey(key)){
            return "您的验证码根本不存在,你对比什么呀";
        }
        String code = redisTemplate.opsForValue().get(key);
        boolean equals = code.equals(inputCode);
        if(equals){
            return "您的验证成功,请继续享受网站其他功能";
        }else{
            return "验证码是错误的,是不是填了别人的手机号";
        }
    }

}
