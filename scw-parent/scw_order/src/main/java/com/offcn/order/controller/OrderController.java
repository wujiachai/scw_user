package com.offcn.order.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.order.pojo.TOrder;
import com.offcn.order.service.OrderService;
import com.offcn.order.vo.req.OrderinfoSubmitvo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@Api(tags = "订单模块")
public class OrderController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    @ApiOperation("保存订单")
    public AppResponse<TOrder> saveOrder(@RequestBody OrderinfoSubmitvo orderinfoSubmitvo){
        String s = stringRedisTemplate.opsForValue().get(orderinfoSubmitvo.getAccessToken());
        if(StringUtils.isEmpty(s)){
            AppResponse.fail("未登录");
        }
        TOrder tOrder = orderService.saveOrder(orderinfoSubmitvo);
        return AppResponse.ok(tOrder);
    }

}
