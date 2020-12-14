package cn.tedu.seckill.mapper;

import com.jt.common.pojo.Seckill;
import com.jt.common.pojo.Success;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

public interface SeckillMapper {
    //SELECT * FROM seckill
    @Select("SELECT * FROM seckill")
    List<Seckill> selectSeckills();
    //SELECT * FROM seckill where seckill_id=#{seckillId}
    @Select("SELECT * FROM seckill where seckill_id=#{seckillId}")
    Seckill selectSeckillById(@Param("seckillId")Long seckillId);
    @Update("UPDATE seckill set number=number-1\n" +
            "where seckill_id=#{seckillId} and\n"+
            "#{nowTime} > start_time and\n"+
            "#{nowTime} < end_time and\n"+"" +
            "number >0")
    int updateNumber(@Param("seckillId")Long seckillId, @Param("nowTime")Date nowTime);
    @Insert("insert into success " +
            "(seckill_id,user_phone,state,create_time)\n" +
            "values (#{seckillId},#{userPhone}," +
            "#{state},#{createTime})")
    void insertSuccess(Success suc);
    @Select("select * from success where seckill_id=#{seckillId}")
    List<Success> selectSeccess(@Param("seckillId")Long seckillId);
}

