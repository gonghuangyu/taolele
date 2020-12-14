package cn.tedu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {
    //使用session读写域属性
    @RequestMapping("/read")
    public String read(HttpSession session,String name){
        return (String)session.getAttribute(name);
    }
    @RequestMapping("/write")
    public String read(HttpSession session,String name,String value){
        session.setAttribute(name,value);
        return "成功写入域属性";
    }
}
