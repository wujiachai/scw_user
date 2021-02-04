package com.offcn.webui.FeignException;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.pojo.TMemberAddress;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.req.UserReqVo;
import com.offcn.webui.vo.respons.UserAddressVo;
import com.offcn.webui.vo.respons.UserRespVo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceFeignException implements UserServiceFeign {
    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【登录】");
        return fail;
    }

    @Override
    public AppResponse<UserReqVo> findTMemberBYid(Integer id) {
        AppResponse<UserReqVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【根据id返回详情】");
        return fail;
    }

    @Override
    public AppResponse <List<UserAddressVo>>findAddressList(String usertooken) {
        AppResponse fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【地址查询失败】");
        return fail;
    }
}
