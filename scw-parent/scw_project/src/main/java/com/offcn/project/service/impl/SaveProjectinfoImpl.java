package com.offcn.project.service.impl;

import com.offcn.dycomment.enums.ProjectStatusEnume;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.SaveProjectinfo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SaveProjectinfoImpl implements SaveProjectinfo {
    @Autowired
    private TProjectMapper tProjectMapper;
    @Autowired
    private TProjectImagesMapper tProjectImagesMapper;
    @Autowired
    private TProjectTagMapper tProjectTagMapper;
    @Autowired
    private TProjectTypeMapper tProjectTypeMapper;
    @Autowired
    private TReturnMapper tReturnMapper;

    @Override
    public void saveproject(ProjectRedisStorageVo projectVo, ProjectStatusEnume auth) {
        //创建项目对象并复制属性
        TProject tProject = new TProject();
        BeanUtils.copyProperties(projectVo,tProject);
        tProject.setMoney(new Long(projectVo.getMoney()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tProject.setCreatedate(simpleDateFormat.format(new Date()));
        tProject.setStatus(auth.getCode()+"");
        //保存项目对象
        tProjectMapper.insertSelective(tProject);
        //保存图片信息
        Integer id = tProject.getId();
        String headerImage = projectVo.getHeaderImage();
        TProjectImages tProjectImages = new TProjectImages(null,id,headerImage, ProjectImageTypeEnume.HEADER.getCode().byteValue());
        tProjectImagesMapper.insertSelective(tProjectImages);
        List<String> detailsImage = projectVo.getDetailsImage();
        for (String s : detailsImage) {
            TProjectImages tProjectImages1 = new TProjectImages(null,id,s,ProjectImageTypeEnume.DETAILS.getCode().byteValue());
            tProjectImagesMapper.insertSelective(tProjectImages1);
        }

        //保存标签关系
        List<Integer> tagids = projectVo.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag tProjectTag = new TProjectTag(null,id,tagid);
            tProjectTagMapper.insert(tProjectTag);
        }
        //保存分类关系信息
        List<Integer> typeids = projectVo.getTypeids();
        for (Integer typeid : typeids) {
            TProjectType tProjectType = new TProjectType(null,id,typeid);
            tProjectTypeMapper.insertSelective(tProjectType);
        }
        //保存回报信息
        List<TReturn> projectReturns = projectVo.getProjectReturns();
        for (TReturn projectReturn : projectReturns) {
            projectReturn.setProjectid(id);
            tReturnMapper.insertSelective(projectReturn);
        }
    }
}
