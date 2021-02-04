package com.offcn.order.service.impl;

import com.offcn.order.mapper.TReturnMapper;
import com.offcn.order.pojo.TReturn;
import com.offcn.order.pojo.TReturnExample;
import com.offcn.order.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectInfoServiceimpl implements ProjectInfoService {
    @Autowired
    private TReturnMapper tReturnMapper;
    @Override
    public List<TReturn> findReternList(Integer projectid) {
        TReturnExample tReturnExample = new TReturnExample();
        TReturnExample.Criteria criteria = tReturnExample.createCriteria();
        criteria.andProjectidEqualTo(projectid);
        List<TReturn> tReturns = tReturnMapper.selectByExample(tReturnExample);
        return tReturns;
    }
}
