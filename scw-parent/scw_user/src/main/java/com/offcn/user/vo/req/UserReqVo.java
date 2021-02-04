package com.offcn.user.vo.req;

import com.offcn.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@ApiModel("测试实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private int id;
    @ApiModelProperty(value = "姓名")
    private String realname;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "手机号")
    String loginacct;

    @ApiModelProperty(value = "密码")
    String userpswd;


}
