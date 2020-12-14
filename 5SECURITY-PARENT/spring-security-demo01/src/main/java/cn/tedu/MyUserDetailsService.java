package cn.tedu;

import cn.tedu.domain.Permission;
import cn.tedu.domain.TBUser;
import cn.tedu.mapper.PermissionMapper;
import cn.tedu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 替换掉目前默认InMemoryUserDetailsService从内存读用户user
 * 改成从数据库
 */
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /*
        利用方法,参数是登录时提交的用户名,查询一个具有3个属性的对象
        username,password,权限list
        用我们的持久层查询数据,按照security要求封装一个userDetails接口实现类
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //利用准备好的TBUser permission封装一个具有3个属性的userDetails对象
        //利用username查询TBUser对象
        TBUser tbUser = userMapper.selectUserByUsername(username);
        if (tbUser==null){
            throw new UsernameNotFoundException("你给的什么用户名");
        }
        //tbUser中的userId 查询List<Permission>
        List<Permission> permissions = permissionMapper.selectPermissionsByUserid(tbUser.getId());
        tbUser.setAuthorities(permissions);
        return tbUser;
        //给UserDetials准备一个authorities
        /*List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        for(Permission permission:permissions){
            //从permission数据库字段authority获取字符串,封装一个SimpleGrantedAuthority对象
            SimpleGrantedAuthority authority=new SimpleGrantedAuthority(permission.getAuthority());
            authorities.add(authority);
        }
        User user=new User(username,tbUser.getPassword(),authorities);
        return user;*/
    }
}
