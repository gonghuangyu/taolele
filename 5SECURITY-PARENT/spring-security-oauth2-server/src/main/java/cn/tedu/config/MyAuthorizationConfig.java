package cn.tedu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

//引入oauth2整合后,本质上就是在seucirty框架基础
//多添加了一批过滤器,通过这个配置类,修改编辑自定义你想要的
//和oauth相关过滤逻辑
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    //一旦配置了持久层相关内容
    @Autowired
    private DataSource dataSource;
    //包装2个处理client token存储数据库的对象
    @Bean("myClientDetailsService")
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }
    //存储Token信息到数据库
    @Bean("myTokenStore")
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }
    //endpoint
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //从数据库存储token
        endpoints.tokenStore(tokenStore());
        //实现refresh_token的使用 用户认证之后,在授权服务器使用refresh_token
        //和认证时的UserDetailsService绑定 绑定到当前用户信息
        endpoints.userDetailsService(userDetailsService);
    }

    //构造获取客户端client身份信息
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
        /*//构造一个内存client第三方客户端身份
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code","refresh_token")//允许客户端使用的授权模型
                .scopes("app")//客户端,代表的是用,权限被用户信息限制的,如果代表客户端本省,
        //客户端权限 scope=范围
                .redirectUris("https://localhost:8091/callback")//回调,重定向给客户端的地址
                .autoApprove(true);*/
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()");
    }
}
