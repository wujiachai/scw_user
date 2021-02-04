package com.offcn.webui.service;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.FeignException.ProjectSertviceFeignException;
import com.offcn.webui.config.FeignConfig;
import com.offcn.webui.vo.respons.ProjectInfoVO;
import com.offcn.webui.vo.respons.ProjectVO;
import com.offcn.webui.vo.respons.ReturnPayConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SCWPROJECT",configuration = FeignConfig.class,fallback = ProjectSertviceFeignException.class)
public interface ProjectSertviceFeign {
    @GetMapping("/return/findAllProject")
    public AppResponse<List<ProjectVO>> findAllProject();

    @GetMapping("/return/findprojevctInfo")
    public AppResponse<ProjectInfoVO> findprojevctInfo(@RequestParam(name = "projectid") Integer projectid);

    @GetMapping("/return/findreturn/{returnid}")
    public AppResponse<ReturnPayConfirmVo> findreturn(@PathVariable(name = "returnid") Integer returnid);

}
