package cn.tedu.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    Integer selectPointsByUserid(@Param("userId")String userId);

    void updatePointsByUserid(@Param("points")Integer points,
                              @Param("userId")String userId);
}
