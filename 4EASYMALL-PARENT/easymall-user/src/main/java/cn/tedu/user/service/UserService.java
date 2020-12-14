package cn.tedu.user.service;

import cn.tedu.user.mapper.UserMapper;
import com.jt.common.pojo.User;
import com.jt.common.utils.MD5Util;
import com.jt.common.utils.MapperUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public boolean checkUsername(String userName) {
        //select count 返回值 0/1
        int result=userMapper.selectCountByUsername(userName);
        return result==0;
    }

    public void doRegister(User user) {
        //补充数据 userId 加密的password
        String userId= UUID.randomUUID().toString();
        String password=
                MD5Util.md5(user.getUserPassword());
        user.setUserId(userId);
        user.setUserPassword(password);
        userMapper.insertUser(user);
                /*MD5Encoder.encode(user.getUserPassword().getBytes());*/
    }
    @Autowired
    private StringRedisTemplate redisTemplate;
    public String doLogin(User user) {
        /*  1.校验 username password 到数据库查询对象行数据
                password加密
            2.成功校验,想办法把用户数据存储到redis
                2.1判断是否有前者使用相同账号登录
                    没有,当前登陆者是第一个,不管
                    有,顶替,利用username的key值获取上一次登录的ticket删除
                2.2
                key:是一个有业务意义的字符串,
                "EM_TICKET_"+currentTime+username
                同一个用户不同时间登录生成的key不同的
                不同用户登录生成key不同
                value:{"username":""},使用一个完整的user对象的json
                2.3存储一个username ticket的 供后续顶替使用
            3.存储到redis,超时时间2小时
         */
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        //从数据库查询user对象 select * from t_user where username= password=
        User exist=userMapper.selectExistByUsernameAndPassword(user);
        if(exist==null){
            //返回空字符串,登录失败
            return "";
        }else{
            //生成登录顶替的key值
            String loginKey="login_"+user.getUserName();
            //判断是否存在
            if(redisTemplate.hasKey(loginKey)){
                //说明有人登录过,获取上一次登录的ticket 将其删除
                String oldTicket= opsForValue.get(loginKey);
                redisTemplate.delete(oldTicket);
            }
            //登录成功,准备写入redis的数据们key value
            String ticket=
                    "EM_TICKET_"+
                    System.currentTimeMillis()
                            +user.getUserName();
            //ajax解析用户登录状态,展示欢迎回来,用的是userJson字符串
            //可以使用从数据库查询的exist 利用api方法将对象转化为json
            //username 替换成nickname password 替换成空
            exist.setUserName(exist.getUserNickname());
            exist.setUserPassword("");
            //利用一个ObjectMapper的api
            try{
                String userJson=
                        MapperUtil.MP.writeValueAsString(exist);
                //写入redis存储中,设置2小时超时 set key value
                opsForValue.
                        set(ticket,userJson,2, TimeUnit.HOURS);
                //将本次ticket保存在loginKey
                opsForValue.
                        set(loginKey,ticket,2, TimeUnit.HOURS);
                return ticket;
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    public String queryUserJson(String ticket) {
        ValueOperations<String, String> opsForValue
                = redisTemplate.opsForValue();
        /*
            1.剩余时间 getExpire 毫秒
            2.是否小于一小时
                不小于1小时
                小于1小时,重新设置超时时长2小时
                ticket userJson
                username ticket
         */
        Long expire = redisTemplate.getExpire(ticket,
                TimeUnit.MILLISECONDS);
        String username=getLoginKey(ticket);
        String loginKey="login_"+username;
        if(expire<1000*60*60){
            redisTemplate.expire(ticket,2,
                    TimeUnit.HOURS);
            redisTemplate.expire(loginKey,2,
                    TimeUnit.HOURS);
        }
        return opsForValue.get(ticket);
    }
    //利用ticket 解析loginKey
    //EM_TICKET_1603866151918eeee
    public String getLoginKey(String ticket){
        return ticket.substring(23);
    }


    public void doLogout(String em_ticket) {
        redisTemplate.delete(em_ticket);
        String username = getLoginKey(em_ticket);
        String loginKey="login_"+username;
        redisTemplate.delete(loginKey);
    }
}
