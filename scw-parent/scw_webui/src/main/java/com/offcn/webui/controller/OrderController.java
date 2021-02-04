package com.offcn.webui.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.pojo.TOrder;
import com.offcn.webui.service.OrderServiceFeign;
import com.offcn.webui.vo.respons.OrderinfoSubmitvo;
import com.offcn.webui.vo.respons.ReturnPayConfirmVo;
import com.offcn.webui.vo.respons.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderServiceFeign orderServiceFeign;

    @RequestMapping("/save")
    public String saveOrder( OrderinfoSubmitvo or, HttpSession session){
        //是否登陆
        UserRespVo sessionMember = (UserRespVo)session.getAttribute("sessionMember");
        if(sessionMember==null){
            return "redirect:/login";
        }
        //获取订单数据设置订单数据
        ReturnPayConfirmVo returnConfirm = (ReturnPayConfirmVo)session.getAttribute("returnConfirmSession");
        if(returnConfirm==null){
            return "redirect:/login";
        }
        or.setAccessToken(sessionMember.getAccessToken());
        or.setProjectid(returnConfirm.getProjectid());
        or.setReturnid(returnConfirm.getId());
        or.setRtncount(returnConfirm.getNum());
        //远程调用保存订单
        AppResponse<TOrder> tOrderAppResponse = orderServiceFeign.saveOrder(or);
        //通过日志打印保存没保存
        TOrder data = tOrderAppResponse.getData();
        log.info("ordernum:{}",data.getOrdernum());
        log.info("money:{}",data.getMoney());
        log.info("ordername:{}",returnConfirm.getProjectName());
        return "member/minecrowdfunding";

    }
}
