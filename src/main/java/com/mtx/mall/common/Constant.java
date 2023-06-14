package com.mtx.mall.common;

/*
* 描述： 常量值
*
* */

import com.google.common.collect.Sets;
import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.exception.MtxMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Constant {

    public static final String MTX_MALL_USER = "mtx_mall_user";
    public static final String SALT = "qingchen,jiayou!";
    public static final String EMAIL_SUBJECT = "您的验证码";
    public static final String EMAIL_FROM = "3091266568@qq.com";


    public static String FILE_UPLOAD_DIR;
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }


    /*
    * 定义了一个接口 ProductListOrderBy，其中声明了一个常量 PRICE_ORDER_ENUM。
      PRICE_ORDER_ENUM 是一个 Set<String> 类型的常量，它使用了 Google Guava 库中的 Sets.newHashSet() 方法来创建一个包含排序字段的集合。
      该集合中包含了两个字符串元素："price desc" 和 "price asc"，分别表示价格降序和价格升序的排序方式。
      通过将排序字段定义为一个接口常量，可以集中管理排序字段的取值，并且避免在代码中多次硬编码相同的字符串。
      这样可以提高代码的可读性和可维护性，并且在需要修改排序字段时只需修改常量的取值即可。
    * */
    public interface ProductListOrderBy {
        Set<String> PRICE_ORDER_ENUM = Sets.newHashSet("price desc", "price asc");
    }

    public interface SaleStatus{
        int NOT_SALE = 0;   //商品下架状态
        int SALE = 1;       //商品上架状态
    }

    public interface Cart{
        int UN_CHECKED = 0;   //购物车未选中状态
        int CHECKED = 1;      //购物车选中状态
    }


    public enum OrderStatusEnum{
        CANCELED(0,"用户已取消"),
        NOT_PAID(10,"未付款"),
        PAID(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");

        private String value;
        private int code;

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum: values()) {
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new MtxMallException(MtxMallExceptionEnum.NO_ENUM);
        }



        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public static final String JWT_KEY = "mtx-mall";
    public static final String JWT_TOKEN = "Authorization";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_ROLE = "user_role";
    public static final Long EXPIRE_TIME = 60 * 1000 * 60 * 24 * 1000L;//单位是毫秒

}

