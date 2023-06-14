package com.mtx.mall.config;

import com.mtx.mall.filter.AdminFilter;
import com.mtx.mall.filter.UserFilter;
import com.mtx.mall.model.pojo.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述： User过滤器的配置
 */
@Configuration
public class UserFilterConfig {
    public static ThreadLocal<User> userThreadLocal = new ThreadLocal();
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }

    @Bean(name = "userFilterConf")
    public FilterRegistrationBean userFilterConf() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        filterRegistrationBean.addUrlPatterns("/user/update");
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }

}
