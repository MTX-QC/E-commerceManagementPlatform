package com.mtx.mall.service;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.model.pojo.Category;
import com.mtx.mall.model.request.AddCategoryReq;
import com.mtx.mall.model.vo.CartVO;
import com.mtx.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * 描述： 购物车Service
 * */
public interface CartService {

    List<CartVO> list(Integer userId);

    List<CartVO> add(Integer userId, Integer productId, Integer count);

    //更新购物车
    List<CartVO> update(Integer userId, Integer productId, Integer count);

    //删除购物车内的商品
    List<CartVO> delete(Integer userId, Integer productId);

    //选中购物车内的商品
    List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected);

    //全选中/全不选中购物车内的商品
    List<CartVO> selectAllOrNot(Integer userId, Integer selected);
}
