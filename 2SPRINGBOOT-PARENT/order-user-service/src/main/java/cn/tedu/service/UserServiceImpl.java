package cn.tedu.service;

import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired
    private UserMapper userMapper;
    public Integer myPoints(String userId) {
        //通过这种命名,可以明确sql的内容
        return userMapper.selectPointsByUserid(userId);
    }
}
