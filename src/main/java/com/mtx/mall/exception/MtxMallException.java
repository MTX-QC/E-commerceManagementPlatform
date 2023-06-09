package com.mtx.mall.exception;
/**
 * 描述：    统一异常
 * */
public class MtxMallException extends RuntimeException{
    private final Integer code;
    private final String message;


    public MtxMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public MtxMallException(MtxMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
