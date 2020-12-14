package cn.tedu.user.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
//二次封装类
public class MyRedisTemplate {
    private JedisPool pool;
    //private LettucePool pool;
    public MyRedisTemplate(JedisPool pool) {
        this.pool = pool;
    }
    //将使用的各种操作redis的方法重新定义
    //exists hasKey
    public boolean hasKey(String key){
        Jedis jedis = pool.getResource();
        try{
            return jedis.exists(key);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(jedis!=null);
            jedis.close();
        }
    }
    public void insertOrUpdate(String key,String value){
        Jedis jedis = pool.getResource();
        try{
            jedis.set(key,value);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(jedis!=null);
            jedis.close();
        }
    }
}
