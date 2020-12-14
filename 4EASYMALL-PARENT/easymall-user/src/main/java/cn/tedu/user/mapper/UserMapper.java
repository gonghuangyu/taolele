package cn.tedu.user.mapper;

import com.jt.common.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int selectCountByUsername(@Param("userName")String userName);

    void insertUser(User user);

    User selectExistByUsernameAndPassword(User user);
}
