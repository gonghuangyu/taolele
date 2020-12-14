package cn.tedu.user.controller;

import cn.tedu.user.service.UserService;
import com.jt.common.pojo.User;
import com.jt.common.utils.CookieUtils;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage")
public class UserController {
    @Autowired
    private UserService userService;
    //检查,注册表单中的用户名是否存在
    //存在,不可以使用,不存在,可以使用
    @RequestMapping("/checkUserName")
    public SysResult checkUsename(String userName){
        boolean available=userService.checkUsername(userName);
        //available true 可用 false不可用
        if(available){
            return SysResult.ok();
        }else{
            return SysResult.build(
                    201,"不可用",
                    null);
        }
    }
    //注册用户
    @RequestMapping("/save")
    public SysResult doRegister(User user){
        try{
            userService.doRegister(user);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "注册失败",
                    null);
        }
    }
    //登录接口
    @RequestMapping("/login")
    public SysResult doLogin(User user,
                             HttpServletResponse response,
                             HttpServletRequest request){
        //业务层service处理对redis的写操作,登录成功才写
        //才有key值 ticket
        String ticket=userService.doLogin(user);
        //ticket是否为空,判断登录成功失败,返回sysResult对象
        if(StringUtils.isNotEmpty(ticket)){
            //利用工具类CookieUtils 设置cookie值
            CookieUtils.setCookie
                    (request,response,
                            "EM_TICKET",ticket);
            //不空,登录校验成功
            return SysResult.ok();
        }else{
            return SysResult.build(
                    201,
                    "登录错误",
                    null);
        }
    }
    //获取登录用户信息
    @RequestMapping("/query/{ticket}")
    public SysResult queryUserJson(@PathVariable String ticket,
                                   HttpServletRequest request,
                                   HttpServletResponse response){
        String userJson=
                userService.queryUserJson(ticket);
        //如果超时了,ticket存在,userJson不会返回 null
        if(StringUtils.isEmpty(userJson)){
            //获取失败,没有登录信息,一定是登录超时了
            //ticket从请求中删除,客户端
            CookieUtils.deleteCookie(request,response,
                    "EM_TICKET");
            return SysResult.build(201,"超时了",null);
        }else{
            return SysResult.build(200,"获取成功",userJson);
        }

    }
    //登出功能
    @RequestMapping("/logout")
    public SysResult doLogout(HttpServletResponse response,
                              HttpServletRequest request){
        //拿到ticket
        String em_ticket =
                CookieUtils.getCookieValue(request, "EM_TICKET");
        //cookie删除EM_TICKET
        CookieUtils.deleteCookie(request,response,
                "EM_TICKET");
        //删除redis
        userService.doLogout(em_ticket);
        return SysResult.ok();
    }
}
