package cn.tedu.redis;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;

public class JedisTest {
    /*
        率先掌握,链接redis的方式,jedis客户端提供了最底层
        链接对象Jedis
     */
    @Test
    public void test01(){
        String key="name";
        String value="王翠花";
        Jedis jedis1=new Jedis("127.0.0.1",9000);
        Jedis jedis2=new Jedis("10.9.118.11",6379);
        Jedis jedis3=new Jedis("10.9.118.11",6380);
        Jedis jedis4=new Jedis("10.9.118.11",6381);
        //string 数据 set/get/incr/decr
        jedis1.set("name","王翠花");
        System.out.println(jedis1.get("name"));
        //hash
        jedis1.hset("user","name","刘守富");
        //list
        jedis1.lpush("list01","100");
        //set
        jedis1.sadd("favorat","数学");
        //zset
        jedis1.zadd("scores",200,"朴乾");
        jedis1.close();
    }

    //了解最大正整数 多少位1
    //Integer.MAX_VALUE
    @Test
    public void test02(){
        //hashCode()
        String key="_haha_dk";
        int result=key.hashCode();
        System.out.println(result);
        System.out.println((result&Integer.MAX_VALUE)%3);
    }
    //做好一个hash取余计算的方法
    public int getShard(String key,int n){
        return (key.hashCode()&Integer.MAX_VALUE)%n;
    }
    //测试hash取余算法的分布式计算逻辑
    @Test
    public void test03(){
        //准备好节点list对象
        List<Jedis> nodes=new ArrayList<>();
        nodes.add(new Jedis("10.9.118.11",6379));//0
        nodes.add(new Jedis("10.9.118.11",6380));//1
        nodes.add(new Jedis("10.9.118.11",6381));//2
        //测试单调性,key值不变读写都在同一个节点
        String keyS="";
        for(int i=0;i<100;i++){
            //模拟每次循环需要生成系统key value值
            String key= "key_"+i;
            String value="value_"+i;
            //从nodes中选择一个jedis执行set操作
            int index = getShard(key, nodes.size());
            Jedis jedis = nodes.get(index);
            jedis.set(key,value);
            if(i==58){
                keyS=key;
            }
        }
        int index = getShard(keyS, nodes.size());
        Jedis jedis = nodes.get(index);
        System.out.println(jedis.get(keyS));//value_58

    }
    //分片对象 ShardedJedis 实现分片计算,底层是一致性hash
    @Test
    public void test04(){
        //收集节点信息,告诉分片对象,一共多少各节点
        List<JedisShardInfo> nodes=new ArrayList<>();
        nodes.add(new JedisShardInfo("10.9.118.11",6379));
        nodes.add(new JedisShardInfo("10.9.118.11",6380));
        nodes.add(new JedisShardInfo("10.9.118.11",6381));
        //创建分片对象
        ShardedJedis sJedis=new ShardedJedis(nodes);
        sJedis.set("name","刘老师");
        System.out.println(sJedis.get("name"));
        sJedis.close();
    }
    //连接池
    @Test
    public void test05(){
        //连接池的属性最大连接,最小空闲,最大空闲等
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMinIdle(2);
        config.setMaxTotal(20);
        //创建一个连接9000端口的连接对象的连接池
        JedisPool pool=new JedisPool(config,"10.9.118.11",9000);
        //从池子中获取资源,jedis
        Jedis jedis = pool.getResource();

        jedis.set("name","王老师");
        jedis.close();
    }
    @Test
    public void test06(){
        //构造分片连接池
        //构建分片对象时的参数 集群信息
        List<JedisShardInfo> nodes=new ArrayList<>();
        nodes.add(new JedisShardInfo("10.9.118.11",6379));
        nodes.add(new JedisShardInfo("10.9.118.11",6380));
        nodes.add(new JedisShardInfo("10.9.118.11",6381));
        //连接池配置属性
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(5);
        config.setMinIdle(2);
        config.setMaxTotal(20);
        ShardedJedisPool pool=new ShardedJedisPool(config,nodes);
        ShardedJedis shardedJedis = pool.getResource();
        shardedJedis.set("name","王老师");
    }
    //主从高可用客户端代码:代码不变,功能依旧
    @Test
    public void test07(){
        //哨兵才知道动态的主节点是如何变化的
        //收集哨兵节点信息
        Set<String> sentinelNodes=new HashSet<>();
        sentinelNodes.add("10.9.118.11:26379");
        sentinelNodes.add("10.9.118.11:26380");
        sentinelNodes.add("10.9.118.11:26381");
        //创建一个哨兵集群连接池--连接池,是通过哨兵获取所有主从节点信息
        //从中获取的连接资源,是主节点的jedis对象
        JedisSentinelPool pool=
                new JedisSentinelPool(
                        "mymaster",
                        sentinelNodes);
        System.out.println("当前主节点:"+pool.getCurrentHostMaster());
        Jedis jedis = pool.getResource();
        //当前主从中主是谁,jedis链接对象就是谁
        jedis.set("name","刘老师");
        System.out.println(jedis.get("name"));
    }

    //jedis客户端访问集群
    @Test
    public void test08(){
        //"name"-->5798;5798--->8001
        Jedis jedis=new Jedis("10.9.118.11",8000);//redis-cli -p -h
        jedis.set("name","王老师");
    }
    //以8位二进制为例
    @Test
    public void test09(){

        int a=116;//01110100
        System.out.println(Integer.toBinaryString(a));
        //在8个槽道 号，使用a整数值记录当前节点 槽道管理权 0 -7号槽道
        for(int i=0;i<8;i++){
            int b=a>>(7-i);
            int manage = b & 1;
            System.out.println("当前槽道号:"+i+",管理权为:"+(manage==1));
        }
    }
    //连接集群JedisCluster
    @Test
    public void test10(){
        //收集节点信息,给几个,都只使用第一个可连接
        //只要连接上一个,就能获取集群所有节点信息
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("10.9.118.11",8000));
        //底层支持连接池
        JedisPoolConfig config=new JedisPoolConfig();
        //maxTotal maxIdel
        JedisCluster cluster=new JedisCluster(nodes,config);
        for(int i=0;i<1000;i++){
            String key=UUID.randomUUID().toString();
            String value="value_"+i;
            cluster.set(key,value);
        }

    }
}

