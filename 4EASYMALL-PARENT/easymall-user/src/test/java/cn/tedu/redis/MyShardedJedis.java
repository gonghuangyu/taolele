package cn.tedu.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
   封装了hash取余计算逻辑的一个分片对象
 */
public class MyShardedJedis {
    private List<Jedis> nodes;
    public MyShardedJedis(List<Jedis> nodes){
        //当前集群所有节点信息
        this.nodes=nodes;
    }
    //核心算法
    //给传递一个key值,能获取对应key的节点jeids
    public Jedis getShard(String key){
        //利用hash取余计算结果,从nodes找到对应节点jedis
        int index = (key.hashCode() & Integer.MAX_VALUE) % nodes.size();
        return nodes.get(index);
    }
    //重写所有jedis的操作key的方法 set/get/hset/hget/lpush/rpush/lpop/rpop...
    public void set(String key,String value){
        Jedis shard = getShard(key);
        shard.set(key,value);
    }
    public String get(String key){
        Jedis shard = getShard(key);
        return shard.get(key);
    }
}
