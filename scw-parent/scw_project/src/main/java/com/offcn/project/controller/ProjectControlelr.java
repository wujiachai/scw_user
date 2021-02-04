package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycomment.enums.ProjectStatusEnume;
import com.offcn.dycomment.oss.OSSTemplete;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.contants.PeojectConstens;
import com.offcn.project.pojo.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.service.SaveProjectinfo;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(tags="项目基本功能模块（文件上传、项目信息获取等）")
@RestController
@RequestMapping("/project")
public class ProjectControlelr {
    @Autowired
    private OSSTemplete ossTemplete;

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SaveProjectinfo saveProjectinfo;

   @ApiOperation("文件上传oss")
    @PostMapping("/uplode")
    public AppResponse<Map<String,Object>> uplode(@RequestParam("file")MultipartFile[] multipartFiles){
        try {
            Map<String, Object> resultmap = new HashMap<>();
            List<Object> list = new ArrayList<>();
            if(multipartFiles!=null&&multipartFiles.length>0){
                for (MultipartFile multipartFile : multipartFiles) {
                    //获得文件名称+.jpg
                    String Filename = multipartFile.getOriginalFilename();
                    String uplode = ossTemplete.uplode(multipartFile.getInputStream(), Filename);
                    list.add(uplode);
                }
            }
            resultmap.put("url",list);
            return AppResponse.ok(resultmap);
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse fail = AppResponse.fail(null);
            fail.setMsg("上传失败");
            return fail;
        }
    }


    @ApiOperation("初始化获取令牌项目发起第1步-阅读同意协议")
    @PostMapping("/init")
    public AppResponse init(String token){
        String memberid = stringRedisTemplate.opsForValue().get(token);
        if(StringUtils.isEmpty(memberid)){
            AppResponse fail = AppResponse.fail(null);
            fail.setMsg("用户不存在");
            return fail;
        }
        String initproject = projectCreateService.initproject(Integer.parseInt(memberid));
       return AppResponse.ok(initproject);
    }

    @PostMapping("/saveBaseinfo")
    @ApiOperation("添加保存项目信息")
    public AppResponse<String> saveBaseinfo(ProjectBaseInfoVo vo){
        String token = stringRedisTemplate.opsForValue().get(PeojectConstens.TEMP_PROJECT_PREFIX + vo.getProjectToken());
        if(!StringUtils.isEmpty(token)){
        ProjectRedisStorageVo redisStorageVo = JSON.parseObject(token, ProjectRedisStorageVo.class);
        BeanUtils.copyProperties(vo,redisStorageVo);
        stringRedisTemplate.opsForValue().set(PeojectConstens.TEMP_PROJECT_PREFIX +redisStorageVo.getProjectToken(),JSON.toJSONString(redisStorageVo));
        return AppResponse.ok("添加成功");
    }
        return AppResponse.fail("茶无此信息");

   }


   @ApiOperation("添加回报信息")
   @PostMapping("/saveReturninfo")
   public AppResponse saveReturninfo(@RequestBody List<ProjectReturnVo> pro){
        //根据令牌查询项目信息
       String Base = stringRedisTemplate.opsForValue().get(PeojectConstens.TEMP_PROJECT_PREFIX + pro.get(0).getProjectToken());
       if(!StringUtils.isEmpty(Base)){
       ProjectRedisStorageVo RedisStorageVo = JSON.parseObject(Base, ProjectRedisStorageVo.class);
       List<TReturn> returns = new ArrayList<>();
       for (ProjectReturnVo projectReturnVo : pro) {
           TReturn tReturn = new TReturn();
           BeanUtils.copyProperties(projectReturnVo,tReturn);
           returns.add(tReturn);
       }
       RedisStorageVo.setProjectReturns(returns);
       //更新数据
       stringRedisTemplate.opsForValue().set(PeojectConstens.TEMP_PROJECT_PREFIX+pro.get(0).getProjectToken(),JSON.toJSONString(RedisStorageVo));
       return AppResponse.ok("添加成功");
       }
       return AppResponse.fail("添加失败");
   }

   @ApiOperation("添加到数据库")
   @ApiImplicitParams({
           @ApiImplicitParam(name ="projectTooken",value = "项目令牌",required = true),
           @ApiImplicitParam(name ="userTooken",value = "用户令牌",required = true),
           @ApiImplicitParam(name ="ops",value = "用户操作类型 0-保存草稿 1-提交审核",required = true)
   })
   @PostMapping("/submit")
   public AppResponse<Object> submit(String projectTooken,String userTooken,String ops){
       //判断用户是否登陆
       String memberid = stringRedisTemplate.opsForValue().get(userTooken);
       if(StringUtils.isEmpty(memberid)){
           return AppResponse.fail("请登陆");
       }
       //根据令牌获取临时对象
       String project = stringRedisTemplate.opsForValue().get(PeojectConstens.TEMP_PROJECT_PREFIX + projectTooken);
       ProjectRedisStorageVo projectRedisStorageVo = JSON.parseObject(project, ProjectRedisStorageVo.class);
       if(ops.equals("1")){
           saveProjectinfo.saveproject(projectRedisStorageVo,ProjectStatusEnume.SUBMIT_AUTH);
       }else if (ops.equals("0")){
           saveProjectinfo.saveproject(projectRedisStorageVo,ProjectStatusEnume.DRAFT);
       }
       stringRedisTemplate.delete(PeojectConstens.TEMP_PROJECT_PREFIX + projectTooken);
       return AppResponse.ok("提交通过");
   }

}
