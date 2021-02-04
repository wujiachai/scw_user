package com.offcn.project.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.respon.ProjectInfoVO;
import com.offcn.project.vo.respon.ProjectVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Api(tags="返回return信息")
@RestController
@RequestMapping("return")
public class ProjectInfoController {
    @Autowired
    private ProjectInfoService projectInfoService;


    @ApiOperation("返回return信息")
    @ApiImplicitParam(name = "projectid" ,value = "项目id")
    @GetMapping("/reternList/{projectid}")
    public AppResponse<List<TReturn>> findReturnList(@PathVariable(name = "projectid") Integer projectid){
        List<TReturn> reternList = projectInfoService.findReternList(projectid);
        return AppResponse.ok(reternList);
    }

    @ApiOperation("返回project信息")
    @GetMapping("/findAllProject")
    public AppResponse<List<ProjectVO>> findAllProject(){
        List<TProject> allproject = projectInfoService.findAllproject();
        List<ProjectVO> projectVOSList = new ArrayList<>();
        for (TProject tProject : allproject) {
            ProjectVO projectVO = new ProjectVO();
            BeanUtils.copyProperties(tProject,projectVO);
            List<TProjectImages> imageByproid = projectInfoService.findImageByproid(tProject.getId());
            for (TProjectImages tProjectImages : imageByproid) {
                if(tProjectImages.getImgtype()== ProjectImageTypeEnume.HEADER.getCode().byteValue()){
                    projectVO.setHeaderImage(tProjectImages.getImgurl());
                }
            }
            projectVOSList.add(projectVO);
        }
        return AppResponse.ok(projectVOSList);
    }

    @ApiOperation("查看项目详情")
    @GetMapping("/findprojevctInfo")
    public AppResponse<ProjectInfoVO> findprojevctInfo(Integer projectid){
        TProject projectinfo = projectInfoService.findProjectinfo(projectid);
        ProjectInfoVO projectInfoVO = new ProjectInfoVO();
        BeanUtils.copyProperties(projectinfo,projectInfoVO);
        //图片信息
        List<TProjectImages> imageByproid = projectInfoService.findImageByproid(projectid);
        //定义详情图list
        ArrayList<String> detailsImagelist = new ArrayList<>();
        for (TProjectImages tProjectImages : imageByproid) {
            if(tProjectImages.getImgtype()== ProjectImageTypeEnume.HEADER.getCode().byteValue()){
                projectInfoVO.setHeaderImage(tProjectImages.getImgurl());
            }
            if(tProjectImages.getImgtype()== ProjectImageTypeEnume.DETAILS.getCode().byteValue()){
                detailsImagelist.add(tProjectImages.getImgurl());
            }
        }
        projectInfoVO.setDetailsImage(detailsImagelist);

        //返回项目的所有支持回报；
        List<TReturn> reternList = projectInfoService.findReternList(projectid);
        projectInfoVO.setProjectReturns(reternList);
        return AppResponse.ok(projectInfoVO);
    }

    //获得项目标签
    @ApiOperation("获得项目标签")
    @GetMapping("findAllTag")
    public AppResponse<List<TTag>> findAllTag(){
        List<TTag> allTag = projectInfoService.findAllTag();
        return AppResponse.ok(allTag);
    }

    @ApiOperation("获取分类标签")
    @GetMapping("findAllType")
    public AppResponse<List<TType>> findAllType(){
        List<TType> allTYpe = projectInfoService.findAllTYpe();
        return AppResponse.ok(allTYpe);
    }

    @ApiOperation("根据returnid查询返回return详情")
    @GetMapping("findreturn/{returnid}")
    public AppResponse<TReturn> findreturn(@PathVariable(name = "returnid")Integer returnid){
        TReturn rTurnbyreturnId = projectInfoService.findRTurnbyreturnId(returnid);
        return AppResponse.ok(rTurnbyreturnId);
    }


}
