package com.mtx.mall.model.dao;

import com.mtx.mall.model.pojo.User;
import org.springframework.data.repository.query.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    User selectByName(String userName);

    User selectLogin(@Param("userName") String userName, @Param("password") String password);

    User selectOneByEmailAddress(String emailAddress);

}