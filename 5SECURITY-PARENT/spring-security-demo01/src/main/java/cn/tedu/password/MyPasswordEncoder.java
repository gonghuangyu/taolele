package cn.tedu.password;

import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.provider.MD5;

public class MyPasswordEncoder implements PasswordEncoder {
    //加密计算,参数是明文,返回是加密的密文
    //CharSequence 也是字符串,更适合在java语言中进行加密计算
    @Override
    public String encode(CharSequence rawPassword) {
        //rawPassword明文 123456
        String encode=rawPassword+"asdklsd";
        return encode;
    }
    //对比明文和密文的结果是否满足匹配
    //参数rawPassword 明文,encoded 从数据库查询的密文
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encode = encode(rawPassword);
        return encode.equals(encodedPassword);
    }
}
