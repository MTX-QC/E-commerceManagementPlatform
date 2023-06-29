package com.mtx.cloud.mall.categoryproduct.controller;

import com.github.pagehelper.PageInfo;

import com.mtx.cloud.mall.categoryproduct.model.pojo.Product;
import com.mtx.cloud.mall.categoryproduct.model.request.ProductListReq;
import com.mtx.cloud.mall.categoryproduct.service.ProductService;
import com.mtx.cloud.mall.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：     前台商品Controller
 */
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("商品详情")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation("商品详情")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }
    @GetMapping("product/detailForFeign")
    public Product detailForFeign(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return product;
    }

    @PostMapping("product/updateStock")
    public void updateStock(@RequestParam Integer productId, @RequestParam Integer stock) {
        productService.updateStock(productId, stock);
    }
}
