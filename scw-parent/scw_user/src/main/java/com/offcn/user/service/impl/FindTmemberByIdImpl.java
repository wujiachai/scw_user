package com.offcn.user.service.impl;

import com.offcn.user.mapper.TMemberAddressMapper;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberAddress;
import com.offcn.user.pojo.TMemberAddressExample;
import com.offcn.user.service.FindTmemberById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindTmemberByIdImpl implements FindTmemberById {

    @Autowired
    private TMemberMapper tMemberMapper;

    @Autowired
    private TMemberAddressMapper tMemberAddressMapper;

    @Override
    public TMember findTmemberbyid(Integer id) {
        TMember tMember = tMemberMapper.selectByPrimaryKey(id);
        return tMember;
    }


    @Override
    public List<TMemberAddress> findAddressList(String Tmemberid) {
        TMemberAddressExample tMemberAddressExample = new TMemberAddressExample();
        TMemberAddressExample.Criteria criteria = tMemberAddressExample.createCriteria();
        criteria.andMemberidEqualTo(Integer.parseInt(Tmemberid));
        return tMemberAddressMapper.selectByExample(tMemberAddressExample);
    }
}
