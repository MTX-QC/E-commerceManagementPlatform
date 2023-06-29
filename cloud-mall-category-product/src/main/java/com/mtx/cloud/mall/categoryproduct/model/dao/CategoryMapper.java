package com.mtx.cloud.mall.categoryproduct.model.dao;



import java.util.List;

import com.mtx.cloud.mall.categoryproduct.model.pojo.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    Category selectByName(String name);

    List<Category> selectList();

    List<Category> selectCategoriesByParentId(Integer parentId);
}