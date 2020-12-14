package cn.tedu.mapper;

import cn.tedu.domain.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 利用user的id值查询当前user 对应的所有权限list
 */
public interface PermissionMapper {
    @Select("select * from tb_permission where user_id=#{userId}")
    List<Permission> selectPermissionsByUserid
            (@Param("userId")Long userId);
}
