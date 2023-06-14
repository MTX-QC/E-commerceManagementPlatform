package com.mtx.mall.service;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.model.pojo.Product;
import com.mtx.mall.model.request.AddProductReq;
import com.mtx.mall.model.request.ProductListReq;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 描述： 商品Service
 * */
@Service
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus( Integer[] id,Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
