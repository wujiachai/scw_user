package com.offcn.webui.vo.respons;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressVo implements Serializable {
    private Integer id;

    //会员地址
    private String address;
}
