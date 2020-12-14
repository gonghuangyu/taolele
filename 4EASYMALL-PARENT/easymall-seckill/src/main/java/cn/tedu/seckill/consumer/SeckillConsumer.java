package cn.tedu.seckill.consumer;

import cn.tedu.seckill.mapper.SeckillMapper;
import com.jt.common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SeckillConsumer {
    @Autowired(required = false)
    private SeckillMapper seckillMapper;
    //编写秒杀的消费逻辑代码
    /*
        减库存,成功失败
        成功之后,将用户和商品信息绑定写入success
     */
    @Autowired
    private StringRedisTemplate redisTemplate;
    @RabbitListener(queues={"seckill-queue"})
    public void consume(String msg){
        //msg="13388992726/1"
        /*
            1.解析 userPhone和seckillId
            2.减库存
                update seckill set number=number-1
                number>0
                start_time<nowTime<end_time
            3.减库存成功失败判断执行逻辑
                成功,写入success
         */
        Long userPhone=Long.parseLong(msg.split("/")[0]);
        Long seckillId=Long.parseLong(msg.split("/")[1]);
        Date nowTime=new Date();
        //opp0--100个 魅族 80
        Long decrement = redisTemplate.opsForValue().decrement("seckill_" + seckillId);
        if(decrement<-10){
            //对不起,这个商品已经秒杀完毕,请您明天再来
            System.out.println("对不起,当前商品"+seckillId+"已经秒杀完毕");
            return;
        }
        //减库存
        int result=seckillMapper.updateNumber(seckillId,nowTime);
        if(result==0){
            //更新失败,当前用户秒杀商品失败
            System.out.println(userPhone+"用户秒杀失败");
            return;
        }
        Success suc=new Success();
        suc.setCreateTime(nowTime);
        suc.setUserPhone(userPhone);
        suc.setSeckillId(seckillId);
        suc.setState(0);
        seckillMapper.insertSuccess(suc);
    }
}
