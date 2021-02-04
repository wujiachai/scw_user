package com.offcn.user.controller;

import com.offcn.user.bin.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;

@RestController
@Api(tags = "第一个Swagger测试")
public class HelloController {
    @ApiOperation("测试方法hello")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),@ApiImplicitParam(name="email",value="邮箱",required = true)
    })

    @GetMapping("/hello")
    public User hello(String name, String email){
        User user = new User();
        user.setId(1);
        user.setName(name);
        user.setEmail(email);

        return user;
    }
}
