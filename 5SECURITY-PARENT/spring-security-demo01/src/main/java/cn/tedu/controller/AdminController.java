package cn.tedu.controller;
import cn.tedu.domain.Permission;
import cn.tedu.domain.TBUser;
import cn.tedu.mapper.PermissionMapper;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @RequestMapping("/admin/write")
    public String write(){
        return "写入数据";
    }
    @RequestMapping("/admin/update")
    public String update(){
        return "更新数据";
    }
    @RequestMapping("/admin/delete")
    public String delete(){
        return "删除数据";
    }
    @RequestMapping("/user/read")
    public String read(){
        return "读取数据";
    }
    @RequestMapping("/allow")
    public String all(){
        return "来者不拒";
    }
    @RequestMapping("/hello")
    public String sayHi(){
        return "hello world security";
    }
    @Autowired
    private UserMapper userMapper;
    //注入测试
    @RequestMapping("/user")
    public TBUser user(String username){
        return userMapper.selectUserByUsername(username);
    }
    @Autowired
    private PermissionMapper permissionMapper;
    @RequestMapping("/permission")
    public List<Permission> permission(Long userId){
        return permissionMapper.selectPermissionsByUserid(userId);
    }
    //在登录成功之后,如果没有其他跳转页页面可以向/succes进行跳转
    @RequestMapping("/success")
    public String loginSuccess(){
        return "{\"code\":\"L200\",\"msg\":\"恭喜你登录成功\"}";
    }
}
