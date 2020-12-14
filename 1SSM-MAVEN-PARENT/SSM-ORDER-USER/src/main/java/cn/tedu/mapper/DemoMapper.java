package cn.tedu.mapper;

import org.apache.ibatis.annotations.Param;

public interface DemoMapper {
    String selectUsernameById(@Param("id")Integer id);
}
