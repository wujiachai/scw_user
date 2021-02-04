package com.offcn.project.service;

import com.offcn.project.pojo.*;
import io.swagger.models.auth.In;

import java.util.List;

public interface ProjectInfoService {
    //查询根据项目id查询返回信息
    public List<TReturn> findReternList(Integer projectid);

    //查询所有项目
    public List<TProject>findAllproject();

    //根据项目id查询图片
    public List<TProjectImages> findImageByproid(Integer projectid);

    //项目详情
    public TProject findProjectinfo(Integer projectid);

    //获得项目标签
    public List<TTag>findAllTag();

    //获取分类标签
    public List<TType>findAllTYpe();

    //根据returnid查询返回return详情
    public TReturn findRTurnbyreturnId(Integer returnid);

}
