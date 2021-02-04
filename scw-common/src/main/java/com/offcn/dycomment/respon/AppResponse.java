package com.offcn.dycomment.respon;

import com.offcn.dycomment.enums.ResponsCode;

public class AppResponse<T>{

    private Integer code;  //相应编码
    private String message; //提示信息
    private T data;  //返回数据的信息

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 快速响应成功
     * @param data
     * @return
     */
    public static<T> AppResponse<T> ok(T data){
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResponsCode.SUCCESS.getCode());
        resp.setMessage(ResponsCode.SUCCESS.getMsg());
        resp.setData(data);
        return resp;
    }

    /**
     * 快速响应失败
     */
    public static<T> AppResponse<T> fail(T data){
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResponsCode.FAIL.getCode());
        resp.setMessage(ResponsCode.FAIL.getMsg());
        resp.setData(data);
        return resp;
    }
}
