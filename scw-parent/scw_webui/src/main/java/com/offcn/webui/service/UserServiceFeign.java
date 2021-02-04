package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.FeignException.UserServiceFeignException;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.pojo.TMemberAddress;
import com.offcn.webui.vo.req.UserReqVo;
import com.offcn.webui.vo.respons.UserAddressVo;
import com.offcn.webui.vo.respons.UserRespVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value = "SCWUSER",configuration = FeignConfig.class,fallback = UserServiceFeignException.class)
public interface UserServiceFeign {
    @GetMapping("/user/login")
    public AppResponse<UserRespVo> login(@RequestParam(name = "loginacct") String loginacct, @RequestParam(name = "password")String password);

    @GetMapping("/User/findTMemberBYid/{id}")
    public AppResponse<UserReqVo> findTMemberBYid(@PathVariable(name = "id") Integer id);

    @GetMapping("/User/getTMemberAddress")
    public AppResponse<List<UserAddressVo>> findAddressList(@RequestParam(name = "usertooken") String usertooken);

}
