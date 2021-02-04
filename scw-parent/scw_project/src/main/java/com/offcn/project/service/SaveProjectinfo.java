package com.offcn.project.service;

import com.offcn.dycomment.enums.ProjectStatusEnume;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

public interface SaveProjectinfo {
    public void saveproject(ProjectRedisStorageVo projectRedisStorageVo, ProjectStatusEnume auth);
}
