package cn.tedu.config;

import cn.tedu.MyUserDetailsService;
import cn.tedu.password.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(redisNamespace = "easymall")
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    //密码加密器 spring security是5.x 必须提供加密器
    //明文加密器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new MyPasswordEncoder();
        //return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }
    @Bean("myUserDetailsService")
    public UserDetailsService userDetailsService(){
        return new MyUserDetailsService();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //user 内存用户对象 用户名 密码 权限
        auth.userDetailsService(userDetailsService());//通过这个方法，可以修改，替换InmemoryUserDetailsService

    }
    //控制访问权限 资源访问安全配置
    //利用创建RedisOperationsSessionRepository
    @Autowired
    private RedisOperationsSessionRepository redisOperationsSessionRepository;
    //修改security操作注册表逻辑 SessionRegistryImpl
    @Bean("mySessionRegistry")
    public SessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(redisOperationsSessionRepository);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
            .antMatchers("/allow").permitAll()//请求要进行授权 ** * ?
            .antMatchers("/admin/write").hasAuthority("write")
            .antMatchers("/admin/update").hasAuthority("update")
            .antMatchers("/admin/delete").hasAuthority("delete")
            .antMatchers("/user/read").hasAuthority("read")
                //还有一些资源可能不需要权限判断的,只要登录了就能访问
            .anyRequest()//其他资源请求
            .authenticated();//只要登录了,认证了就行
        //由于覆盖了父类中认证方式,添加上一个表单认证
        http.csrf().disable();//关闭csrf过滤器,不在为所有post提交绑定一个客户端的参数
        http.formLogin().loginProcessingUrl("/mylogin")
                .usernameParameter("un")
                .passwordParameter("pw")
                //如果请求 之前有想要访问的地址,成功登录后,访问之前的地址
                //如果是直接访问的登录,将会跳转到/success
                .defaultSuccessUrl("/success");
                //failureForwordUrl 登录失败跳转地址
                //只要登录 成功,一定访问/success
                //.successForwardUrl("/success");//修改表单相关认证逻辑时,可以在这个方法后继续添加内容
        http.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry());
    }
}
