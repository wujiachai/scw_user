package com.offcn.project.service.impl;


import com.offcn.project.mapper.*;
import com.offcn.project.pojo.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceimpl implements ProjectInfoService {
    @Autowired
    private TReturnMapper tReturnMapper;

    @Autowired
    private TProjectMapper tProjectMapper;

    @Autowired
    private TProjectImagesMapper tProjectImagesMapper;

    @Autowired
    private TTagMapper tTagMapper;

    @Autowired
    private TTypeMapper tTypeMapper;
    @Override
    public List<TReturn> findReternList(Integer projectid) {
        TReturnExample tReturnExample = new TReturnExample();
        TReturnExample.Criteria criteria = tReturnExample.createCriteria();
        criteria.andProjectidEqualTo(projectid);
        List<TReturn> tReturns = tReturnMapper.selectByExample(tReturnExample);
        return tReturns;
    }

    @Override
    public List<TProject> findAllproject() {
        return tProjectMapper.selectByExample(null);
    }

    @Override
    public List<TProjectImages> findImageByproid(Integer projectid) {
        TProjectImagesExample tProjectImagesExample = new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = tProjectImagesExample.createCriteria();
        criteria.andProjectidEqualTo(projectid);
        return tProjectImagesMapper.selectByExample(tProjectImagesExample);
    }

    @Override
    public TProject findProjectinfo(Integer projectid) {
        return tProjectMapper.selectByPrimaryKey(projectid);
    }

    @Override
    public List<TTag> findAllTag() {
        List<TTag> tTags = tTagMapper.selectByExample(null);
        return tTags;
    }

    @Override
    public List<TType> findAllTYpe() {
        return tTypeMapper.selectByExample(null);
    }

    @Override
    public TReturn findRTurnbyreturnId(Integer returnid) {
        return tReturnMapper.selectByPrimaryKey(returnid);
    }
}
