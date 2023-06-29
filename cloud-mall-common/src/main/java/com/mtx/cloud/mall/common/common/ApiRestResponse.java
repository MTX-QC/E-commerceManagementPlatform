package com.mtx.cloud.mall.common.common;

import com.mtx.cloud.mall.common.exception.MtxMallExceptionEnum;

/**
 * 描述：    通用返回对象
 * */
public class ApiRestResponse<T> {
    //状态码
    private Integer status;

    //信息
    private String msg;

    //数据对象
    private T data;

    //常量成功码
    private static final int OK_CODE = 1000;

    //常量返回信息
    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 当我们不需要传递信息时，表明请求成功
     * */
    public ApiRestResponse() {
        this(OK_CODE,OK_MSG);
    }

    /**
     * 返回我们通用的响应对象
     * */
    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }

    public static <T> ApiRestResponse<T> error(MtxMallExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(),ex.getMsg());
    }

    /**
     * 打印状态信息
     * */
    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
