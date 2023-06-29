package com.mtx.cloud.mall.common.exception;

/**
 * 描述：   异常枚举
 */
public enum MtxMallExceptionEnum {
    REQUEST_PARAM_ERROR(10001, "参数有误，请重试"),
    NO_ENUM(10002, "找不到枚举"),
    NEED_USER_NAME(10003, "需要用户名"),
    NEED_PASSWORD(10004, "需要密码"),
    PASSWORD_TOO_SHORT(10005, "密码不能过短"),
    NAME_EXISTED(10006, "用户名不能重名"),
    INSERT_FAILED(10007, "插入失败，请重试"),
    WRONG_PASSWORD(10008, "密码错误"),
    UPDATE_FAILED(10009, "更新错误"),
    NEED_LOGIN(10010, "请登录"),
    NEED_ADMIN(10011, "非管理员，无法操作"),
    CREATE_FAILED(10012, "创建失败"),
    DELETE_FAILED(10013, "删除失败"),
    MKDIR_FAILED(10014, "创建文件夹失败"),
    UPLOAD_FAILED(10015, "上传失败"),
    NOT_SALE(10016, "商品未上架"),
    NOT_ENOUGH(10017, "商品库存不足"),
    CART_EMPTY(10018, "购物车为空"),
    NO_ORDER(10019, "订单不存在"),
    NOT_YOUR_ORDER(10020, "不能操作非自己的订单"),
    WRONG_ORDER_STATUS(10021, "订单状态不符"),
    WRONG_EMAIL(10023, "非法的邮件地址"),
    EMAIL_ALREADY_BEEN_REGISTERED(10024, "email地址已被注册"),
    EMAIL_ALREADY_BEEN_SEND(10025, "email已发送，若无法收到，请稍后再试"),
    NEED_EMAIL_ADDRESS(10026, "email不能为空"),
    NEED_VERIFICATION_CODE(10027, "验证码不能为空"),
    WRONG_VERIFICATION_CODE(10028, "验证码错误"),
    SYSTEM_ERROR(20000, "系统异常") ;

    /**
     * 异常码
     */

    Integer code;
    /**
     * 异常信息
     * */
    String msg;

    MtxMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
