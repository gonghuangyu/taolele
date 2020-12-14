package cn.tedu.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface HelloMapper {
    //单表,简单的entity对象封装
    //mybatis支持0xml映射文件配置
    //@Select("select points from t_user where user_id=#{userId}")
    Integer selectPointsById(@Param("userId")String userId);
}
