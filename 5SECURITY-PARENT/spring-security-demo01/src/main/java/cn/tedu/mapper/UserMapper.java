package cn.tedu.mapper;

import cn.tedu.domain.TBUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 创建一个方法，利用username查询一个TBUser
 */
public interface UserMapper {
    @Select("select * from tb_user where username=#{username}")
    TBUser selectUserByUsername(@Param("username")String username);
}
