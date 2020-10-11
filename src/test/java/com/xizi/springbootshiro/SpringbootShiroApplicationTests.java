package com.xizi.springbootshiro;

import com.xizi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootShiroApplicationTests {


    @Autowired
    UserServiceImpl userServiceImpl;
    @Test
    void contextLoads() {

        System.out.println(userServiceImpl.queryUserByName("戏子1"));
    }

}
