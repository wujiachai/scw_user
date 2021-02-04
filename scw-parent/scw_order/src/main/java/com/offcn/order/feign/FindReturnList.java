package com.offcn.order.feign;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.pojo.TReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SCWPROJECT",fallback = ProjectServiceFeignException.class)//降级返回的类型
public interface FindReturnList {
    @GetMapping("/return/reternList/{projectid}")
    public AppResponse<List<TReturn>> findReturnList(@PathVariable(name = "projectid") Integer projectid);

}
