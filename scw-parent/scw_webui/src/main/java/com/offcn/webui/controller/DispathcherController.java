package com.offcn.webui.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.ProjectSertviceFeign;
import com.offcn.webui.service.UserServiceFeign;
import com.offcn.webui.vo.respons.ProjectVO;
import com.offcn.webui.vo.respons.UserRespVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DispathcherController {

    @Autowired
    private UserServiceFeign userServiceFeign;
    @Autowired
    private ProjectSertviceFeign projectSertviceFeign;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/toIndex")
    public String toIndex(Model model){

        String projectStr = stringRedisTemplate.opsForValue().get("projectStr");
        List<ProjectVO> projectVOS = JSON.parseArray(projectStr, ProjectVO.class);
        if(CollectionUtils.isEmpty(projectVOS)){
            AppResponse<List<ProjectVO>> allProject = projectSertviceFeign.findAllProject();
            List<ProjectVO> data = allProject.getData();
            projectVOS = data;
            stringRedisTemplate.opsForValue().set("projectStr",JSON.toJSONString(data));
        }
        model.addAttribute("projectList",projectVOS);
        return "index";
    }

    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String password, HttpSession session){
        AppResponse<UserRespVo> login = userServiceFeign.login(loginacct, password);
        UserRespVo data = login.getData();
        if(data==null){
            return "redirect:/login"; //redirect资源重定向
        }
        //如果登陆成功将用户信息保存到session
        session.setAttribute("sessionMember",data);
        //在session中取得当前页面路径
        String preUrl = (String) session.getAttribute("preUrl");
        if(StringUtils.isEmpty(preUrl)){
            return "redirect:/toIndex";
        }
        return "redirect:/"+preUrl;
    }
}
