package com.mtx.cloud.mall.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.mtx.cloud.mall.categoryproduct.model.pojo.Category;
import com.mtx.cloud.mall.categoryproduct.model.request.AddCategoryReq;
import com.mtx.cloud.mall.categoryproduct.model.vo.CategoryVO;


import java.util.List;

/**
 * 描述：     分类目录Service
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
