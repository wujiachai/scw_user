package com.offcn.order.feign;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.pojo.TReturn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceFeignException implements FindReturnList {
    @Override
    public AppResponse<List<TReturn>> findReturnList(Integer projectid) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMsg("远程服务调用失败");
        return fail;
    }
}
