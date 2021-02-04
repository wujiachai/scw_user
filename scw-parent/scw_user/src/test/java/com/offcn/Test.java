package com.offcn;

import com.offcn.user.Userstart;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Userstart.class)
public class Test {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @org.junit.Test
    public void redis(){
        //redisTemplate.opsForValue().set("msg1","欢迎来优就业学习");
        stringRedisTemplate.opsForValue().set("msg1","踩踩踩踩踩踩踩踩踩");
    }

}
