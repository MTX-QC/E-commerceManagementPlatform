package com.mtx.mall.service;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.model.pojo.Category;
import com.mtx.mall.model.request.AddCategoryReq;
import com.mtx.mall.model.vo.CategoryVO;

import java.util.List;

/**
 * 描述： 目录分类Service
 * */
public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
