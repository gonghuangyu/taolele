package cn.tedu.service;

import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    @Autowired(required = false)
    private UserMapper userMapper;
    public Integer myPoints(String userId) {
        //通过这种命名,可以明确sql的内容
        return userMapper.selectPointsByUserid(userId);
    }

    public void updatePoint(Double money, String userId) {
        //根据金额计算积分数值,根据userId实现积分给谁
        //2倍
        int points=money.intValue()*2;
        userMapper.updatePointsByUserid(points,userId);
    }
}
