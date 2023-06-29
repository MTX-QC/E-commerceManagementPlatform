package com.mtx.cloud.mall.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.mtx.cloud.mall.categoryproduct.model.pojo.Product;
import com.mtx.cloud.mall.categoryproduct.model.request.AddProductReq;
import com.mtx.cloud.mall.categoryproduct.model.request.ProductListReq;


/**
 * 描述：     商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);

    void updateStock(Integer productId, Integer stock);

}
