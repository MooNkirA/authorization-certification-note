package com.moon.spring.security.boot.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Spring Security 的 BCrypt 加密测试
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-28 16:54
 * @description
 */
@SpringBootTest
public class BCryptTest {

    @Test
    public void testBCryptBasic() {
        // 对密码进行 BCrypt 加密
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println("密码”123“加密后: " + hashpw);
        String hashpw2 = BCrypt.hashpw("123", BCrypt.gensalt());
        System.out.println("密码”123“第二次加密: " + hashpw2);

        // 校验密码
        boolean checkpw = BCrypt.checkpw("123", hashpw);
        System.out.println("密码校验结果: " + checkpw);
        boolean checkpw2 = BCrypt.checkpw("123", hashpw2);
        System.out.println("第二次加密密码校验结果: " + checkpw2);
    }

}
