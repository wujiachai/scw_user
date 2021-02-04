package com.offcn.user.service;

import com.offcn.user.pojo.TMember;

public interface LoginService {
    public TMember loginUser(String loginacct,String password);
}
