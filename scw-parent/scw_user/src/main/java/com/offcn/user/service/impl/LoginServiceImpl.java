package com.offcn.user.service.impl;

import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberExample;
import com.offcn.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private TMemberMapper tMemberMapper;
    @Override
    public TMember loginUser(String loginacct, String password) {
        TMemberExample tMemberExample = new TMemberExample();
        TMemberExample.Criteria criteria = tMemberExample.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<TMember> tMembers = tMemberMapper.selectByExample(tMemberExample);
        if(!CollectionUtils.isEmpty(tMembers)){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        TMember tMember = tMembers.get(0);
            boolean matches = bCryptPasswordEncoder.matches(password, tMember.getUserpswd());
            return matches?tMember:null;


        }
        return null;
    }
}
