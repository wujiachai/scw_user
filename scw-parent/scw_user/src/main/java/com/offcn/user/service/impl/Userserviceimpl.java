package com.offcn.user.service.impl;

import com.offcn.user.enums.UserExceptionEnum;
import com.offcn.user.exception.UserException;
import com.offcn.user.mapper.TMemberMapper;
import com.offcn.user.pojo.TMember;
import com.offcn.user.pojo.TMemberExample;
import com.offcn.user.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Userserviceimpl implements Userservice {
    @Autowired
    private TMemberMapper memberMapper;
    @Override
    public void addtMember(TMember tMember) {
        //检查手机号是否存在
        TMemberExample tMemberExample = new TMemberExample();
        TMemberExample.Criteria criteria = tMemberExample.createCriteria();
        criteria.andLoginacctEqualTo(tMember.getLoginacct());
        long l = memberMapper.countByExample(tMemberExample);
        if(l>0){
           throw  new  UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        //密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(tMember.getUserpswd());
        tMember.setUserpswd(encode);

        tMember.setUsername(tMember.getLoginacct());//用户名
        tMember.setAuthstatus("0");
        tMember.setUsertype("0");
        tMember.setRealname("赵老师");
        tMember.setAccttype("2");
        memberMapper.insertSelective(tMember);
    }
}
