package cn.tedu.service;

import cn.tedu.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl{
    @Autowired
    private DemoMapper demoMapper;
    public String getData(Integer id) {
        return demoMapper.selectUsernameById(id);
    }
}
