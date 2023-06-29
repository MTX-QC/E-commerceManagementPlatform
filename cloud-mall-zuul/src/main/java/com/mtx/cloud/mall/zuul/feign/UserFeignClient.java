package com.mtx.cloud.mall.zuul.feign;

import com.mtx.cloud.mall.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 描述：     UserFeignClient
 */
@FeignClient(value = "cloud-mall-user")
public interface UserFeignClient {

    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);
}
