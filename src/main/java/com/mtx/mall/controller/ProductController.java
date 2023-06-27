package com.mtx.mall.controller;

/*
 * 描述： 前台商品Controller
 * */

import com.github.pagehelper.PageInfo;
import com.mtx.mall.common.ApiRestResponse;
import com.mtx.mall.model.pojo.Product;
import com.mtx.mall.model.request.ProductListReq;
import com.mtx.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST})
public class ProductController {
    @Autowired
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id){
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation("前台商品列表")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq){
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }


}
