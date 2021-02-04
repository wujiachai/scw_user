package com.offcn.webui.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.pojo.TMemberAddress;
import com.offcn.webui.service.ProjectSertviceFeign;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.req.UserReqVo;
import com.offcn.webui.vo.respons.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectSertviceFeign projectSertviceFeign;
    @Autowired
    private UserServiceFeign userServiceFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/projectInfo")
    public String findProjectDetail(@RequestParam(name = "id") Integer id, Model model, HttpSession session){
        AppResponse<ProjectInfoVO> projectInfoVOAppResponse = projectSertviceFeign.findprojevctInfo(id);
        session.setAttribute("DetailVo",projectInfoVOAppResponse.getData());
        model.addAttribute("DetailVo",projectInfoVOAppResponse.getData());
        return "project/project";
    }

    @RequestMapping("/confirm/returns/{projectId}/{returnId}")
    public String findReturnInfo(Model model, @PathVariable(name = "projectId") Integer projectId, @PathVariable(name = "returnId")Integer returnId, HttpSession session){
        //获取project
        ProjectInfoVO detailVo = (ProjectInfoVO)session.getAttribute("DetailVo");
        AppResponse<ReturnPayConfirmVo> findreturn = projectSertviceFeign.findreturn(returnId);
        ReturnPayConfirmVo data = findreturn.getData();
        data.setProjectid(projectId);
        data.setProjectName(detailVo.getName());//项目回报名称
        data.setProjectRemark(detailVo.getRemark());
        //远程服务调用查询发起人的详情信息
        AppResponse<UserReqVo>  tMemberBYid = userServiceFeign.findTMemberBYid(detailVo.getMemberid());
        data.setMemberId(tMemberBYid.getData().getId());
        data.setMemberName(tMemberBYid.getData().getRealname());
        //计算zongjine
        //添加项目回报信息到session
        session.setAttribute("returnConfirm",data);
        model.addAttribute("returnConfirm",data);
        return "project/pay-step-1";
    }


    @RequestMapping("/confirm/order/{num}")
    public String confirm(Model model,@PathVariable(name = "num") Integer num,HttpSession session){
        //判断是否登陆
        UserRespVo sessionMember = (UserRespVo)session.getAttribute("sessionMember");
        if(sessionMember==null){
            session.setAttribute("preUrl","project/confirm/order/"+num);
            return "redirect:/login";
        }
        //根据令牌查询地址列表
        AppResponse<List<UserAddressVo>> addressList = userServiceFeign.findAddressList(sessionMember.getAccessToken());
        List<UserAddressVo> data = addressList.getData();
        //根据购买数量num重新计算金额
        ReturnPayConfirmVo returnConfirm =(ReturnPayConfirmVo) session.getAttribute("returnConfirm");
        returnConfirm.setNum(num);
        returnConfirm.setTotalPrice(new BigDecimal(returnConfirm.getNum()*returnConfirm.getSupportmoney()+returnConfirm.getFreight()));
        //设置收货人地址列表
        model.addAttribute("addresses",data);
        session.setAttribute("returnConfirmSession",returnConfirm);
        return "project/pay-step-2";
    }
}
