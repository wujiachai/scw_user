package com.offcn.user.service;

import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;

import java.util.List;

public interface FindTmemberById {
    public TMember findTmemberbyid(Integer id);
    public List<TMemberAddress>findAddressList(String Tmemberid);
}
