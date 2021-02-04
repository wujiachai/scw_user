package com.offcn.webui.vo.respons;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderinfoSubmitvo  implements Serializable {
    private Integer projectid;//项目ID
    private Integer returnid;//回报ID
    private Integer rtncount;//回报数量
    private String address;//收货地址
    private Byte invoice;//是否开发票 0 - 不开发票， 1 - 开发票
    private String invoictitle;//发票名头
    private String remark;//备注
    private String accessToken;
}
