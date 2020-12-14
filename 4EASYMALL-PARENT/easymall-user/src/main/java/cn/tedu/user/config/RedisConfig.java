package cn.tedu.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 创建redis对应结构的对象们
 */
//@Configuration
public class RedisConfig {
    @Value("${maxTotal}")
    private Integer maxTotal;
    @Bean
    public JedisPool initJedisPool(){
        //连接池的属性最大连接,最小空闲,最大空闲等
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMinIdle(2);
        config.setMaxTotal(maxTotal);
        //创建一个连接9000端口的连接对象的连接池
        JedisPool pool=new JedisPool(config,"10.9.118.11",9000);
        return pool;
    }
}
