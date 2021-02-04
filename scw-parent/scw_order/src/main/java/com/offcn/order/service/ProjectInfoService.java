package com.offcn.order.service;

import com.offcn.order.pojo.TReturn;

import java.util.List;

public interface ProjectInfoService {
    public List<TReturn> findReternList(Integer projectid);
}
