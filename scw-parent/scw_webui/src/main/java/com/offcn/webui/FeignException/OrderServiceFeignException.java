package com.offcn.webui.FeignException;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.pojo.TOrder;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.respons.OrderinfoSubmitvo;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignException implements OrderServiceFeign {
    @Override
    public AppResponse<TOrder>  saveOrder(OrderinfoSubmitvo orderinfoSubmitvo) {
        AppResponse<TOrder> fail = AppResponse.fail(null);
        fail.setMsg("保存订单失败");
        return fail;
    }
}
