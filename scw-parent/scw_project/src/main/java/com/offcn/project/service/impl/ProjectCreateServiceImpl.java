package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.project.contants.PeojectConstens;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String initproject(Integer menberid) {
        //初始化用户对象设置用户idProjectRedisStorageVo
        ProjectRedisStorageVo redisStorageVo = new ProjectRedisStorageVo();
        redisStorageVo.setMemberid(menberid);
        String projectToken = UUID.randomUUID().toString().replace("-", "");
        redisStorageVo.setProjectToken(projectToken);
        //将项目通过令牌放入缓存
        //令牌key拼接常量
        stringRedisTemplate.opsForValue().set(PeojectConstens.TEMP_PROJECT_PREFIX+projectToken, JSON.toJSONString(redisStorageVo));
        return projectToken;
    }
}
