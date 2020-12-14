package cn.tedu.password;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encoder {
    @Test
    public void test(){
        PasswordEncoder passwordEncoder
                =new MyPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }
}
