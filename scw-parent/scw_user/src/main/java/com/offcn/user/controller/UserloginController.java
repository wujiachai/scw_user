package com.offcn.user.controller;

import com.fasterxml.jackson.databind.BeanProperty;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.bin.User;
import com.offcn.user.pojo.TMember;
import com.offcn.user.service.LoginService;
import com.offcn.user.sms.SmsTemplate;
import com.offcn.user.vo.respons.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Api(tags = "发送验证码")
public class UserloginController {
    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private LoginService loginService;

    @ApiOperation("测试方法验证码")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="phone",value="电话",required = true)
    })
    @GetMapping ("/sendCode")
    public AppResponse<Object> sendCode(String phone){
        try {
            String code = UUID.randomUUID().toString().substring(0, 4);
            stringRedisTemplate.opsForValue().set(phone,code);
            HttpResponse httpResponse = smsTemplate.sendSms(phone, code);
            return AppResponse.ok(httpResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail("发送失败");
        }
    }


    @ApiOperation("登陆")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="loginacct",value="用户名",required = true),
            @ApiImplicitParam(name="password",value="密码",required = true)
    })
    @GetMapping ("/login")
    public AppResponse<UserRespVo> login(String loginacct,String password){
        TMember tMember = loginService.loginUser(loginacct, password);
        if(tMember==null){
          AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户名密码错误");
            return fail;
        }
        String Token = UUID.randomUUID().toString().replace("-", "");
        stringRedisTemplate.opsForValue().set(Token,tMember.getId()+"");
        UserRespVo userRespVo = new UserRespVo();
        BeanUtils.copyProperties(userRespVo,tMember);
        userRespVo.setAccessToken(Token);
        return AppResponse.ok(userRespVo);
    }
}
