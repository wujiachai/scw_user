package com.offcn.user.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.service.FindTmemberById;
import com.offcn.user.service.Userservice;
import com.offcn.user.vo.req.UserReqVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
@Api(tags = "用户注册")
public class UserController {
    @Autowired
    private Userservice userservice;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FindTmemberById findTmemberById;

    @ApiOperation("用户注册")
    @PostMapping("/addUser")
    public AppResponse<Object> addUser(UserReqVo userVo){
        //校验短信验证码
        String s = stringRedisTemplate.opsForValue().get(userVo.getLoginacct());
        if(!StringUtils.isEmpty(s)&&s.equals(userVo.getCode())){
            TMember tMember = new TMember();
            BeanUtils.copyProperties(userVo,tMember);
            try {
                userservice.addtMember(tMember);
                return AppResponse.ok("ok");
            } catch (Exception e) {
                e.printStackTrace();
                return AppResponse.fail("失败");

            }

        }else {
            return AppResponse.fail("验证码错误或过期");
        }

    }

    @ApiOperation("用户查询")
    @GetMapping("/findTMemberBYid/{id}")
    public AppResponse<UserReqVo> findTMemberBYid(@PathVariable(name = "id") Integer id){
        TMember tmemberbyid = findTmemberById.findTmemberbyid(id);
        UserReqVo userReqVo = new UserReqVo();
        BeanUtils.copyProperties(tmemberbyid,userReqVo);
        return AppResponse.ok(userReqVo);
    }

    @ApiOperation("查询用户地址列表")
    @GetMapping("/getTMemberAddress")
    public AppResponse<List<TMemberAddress>> findAddressList(String usertooken){
        String s = stringRedisTemplate.opsForValue().get(usertooken);
        if(StringUtils.isEmpty(s)){
            AppResponse<List<TMemberAddress>> fail = AppResponse.fail(null);
            fail.setMsg("未登录");
            return fail;
        }
        List<TMemberAddress> addressList = findTmemberById.findAddressList(s);
        return AppResponse.ok(addressList);
    }
}
