package com.mtx.cloud.mall.cartorder.model.dao;


import java.util.List;

import com.mtx.cloud.mall.cartorder.model.pojo.Cart;
import com.mtx.cloud.mall.cartorder.model.vo.CartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<CartVO> selectList(@Param("userId") Integer userId);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId,
            @Param("productId") Integer productId);

    Integer selectOrNot(@Param("userId") Integer userId, @Param("productId") Integer productId,
            @Param("selected") Integer selected);
}