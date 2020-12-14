package cn.tedu.seckill.controller;

import cn.tedu.seckill.mapper.SeckillMapper;
import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;
import com.jt.common.vo.SysResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/seckill/manage")
public class SeckillController {
    @Autowired(required = false)
    private SeckillMapper seckillMapper;
    //查询所有的seckill表格的行数据
    @RequestMapping("/list")
    public List<Seckill> list(){
        return seckillMapper.selectSeckills();
    }
    //根据seckillId查询具体的商品详情
    @RequestMapping("/detail")
    public Seckill detail(Long seckillId){
        return seckillMapper.selectSeckillById(seckillId);
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //生产端消息发送,谁秒杀了什么
    @RequestMapping("/{seckillId}")
    public SysResult startSeckill(@PathVariable String seckillId){
        //seckillId表示秒杀了什么
        String userPhone=
                "1338899"+(new Random()
                        .nextInt(8999)+1000);
        String msg=userPhone+"/"+seckillId;
        //使用redis限制当前 计步器
        Long increment = redisTemplate.
                opsForValue().
                increment(msg);//incr msg
        if(increment>1){
            //当前商品被这个用户秒杀了多次
            return SysResult.build(
                    201,
                    "占便宜没够啊",
                    null);
        }
        //seckill-ex 携带路由seckill最终到了seckill-queue
        rabbitTemplate.convertAndSend(
                "seckill-ex",
                "seckill",
                msg);
        return SysResult.ok();
    }
    //根据传递商品id值,展示查询成功者的电话号码
    @RequestMapping("/{seckillId}/userPhone")
    public List<Success> success(@PathVariable Long seckillId){
        return seckillMapper.selectSeccess(seckillId);
    }
}
