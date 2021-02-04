package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.FeignException.OrderServiceFeignException;
import com.offcn.webui.FeignException.ProjectSertviceFeignException;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.pojo.TOrder;
import com.offcn.webui.vo.respons.OrderinfoSubmitvo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SCWORDER",configuration = FeignConfig.class,fallback = OrderServiceFeignException.class)
public interface OrderServiceFeign {
    @PostMapping("/order/saveOrder")
    public AppResponse<TOrder>  saveOrder(@RequestBody OrderinfoSubmitvo orderinfoSubmitvo);
}
