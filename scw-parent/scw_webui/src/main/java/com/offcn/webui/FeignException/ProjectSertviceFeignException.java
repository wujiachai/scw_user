package com.offcn.webui.FeignException;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.webui.service.ProjectSertviceFeign;
import com.offcn.webui.vo.respons.ProjectInfoVO;
import com.offcn.webui.vo.respons.ProjectVO;
import com.offcn.webui.vo.respons.ReturnPayConfirmVo;
import com.offcn.webui.vo.respons.TReturn;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProjectSertviceFeignException  implements ProjectSertviceFeign {
    @Override
    public AppResponse<List<ProjectVO>> findAllProject() {
        AppResponse<List<ProjectVO>> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败【查询首页热点项目】");
        return fail;
    }

    @Override
    public AppResponse<ProjectInfoVO> findprojevctInfo(Integer projectid) {
        AppResponse<ProjectInfoVO> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败【查询详情】");
        return fail;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> findreturn(Integer returnid) {
        AppResponse<ReturnPayConfirmVo> fail = AppResponse.fail(null);
        fail.setMsg("远程调用失败【查询详情】");
        return fail;
    }
}
