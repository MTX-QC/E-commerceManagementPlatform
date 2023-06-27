package com.mtx.mall.controller;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.common.ApiRestResponse;
import com.mtx.mall.common.Constant;
import com.mtx.mall.common.ValidList;
import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.exception.MtxMallExceptionEnum;
import com.mtx.mall.model.pojo.Product;
import com.mtx.mall.model.request.AddProductReq;
import com.mtx.mall.model.request.UpdateProductReq;
import com.mtx.mall.service.ProductService;
import com.mtx.mall.service.UploadService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;


/*
 * 描述： 后台商品管理Controller
 * */
@RestController
@Validated
//@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true", methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST})
public class ProductAdminController {
    @Autowired
    ProductService productService;
    @Autowired
    UploadService uploadService;

    @PostMapping("admin/product/add")
    @ApiOperation("添加商品")
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {
        productService.add(addProductReq);
        return ApiRestResponse.success();
    }

    @PostMapping("admin/upload/file")
    @ApiOperation("上传商品图片")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest,
                                  @RequestParam("file") MultipartFile file) {
        String result = uploadService.uploadFile(file);
        return ApiRestResponse.success(result);
    }


    @ApiOperation("后台更新商品")
    @PostMapping("admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台删除商品")
    @PostMapping("admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台批量上下架商品")
    @PostMapping("admin/product/batchUpdateSellStatus")
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids,
                                                 @RequestParam Integer sellStatus) {
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台商品列表接口")
    @GetMapping("/admin/product/list")
    public ApiRestResponse list(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("后台批量上传商品接口")
    @PostMapping("admin/upload/product")
    public ApiRestResponse uploadProduct(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String newFileName = uploadService.getNewFileName(multipartFile);
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);
        uploadService.createFile(multipartFile, fileDirectory, destFile);
        productService.addProductByExcel(destFile);
        return ApiRestResponse.success();
    }


    @PostMapping("admin/upload/image")
    @ApiOperation("对图片的编辑")
    public ApiRestResponse uploadImage(HttpServletRequest httpServletRequest,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        String result = uploadService.uploadImage(file);
        return ApiRestResponse.success(result);

    }

    @ApiOperation("后台批量更新商品")
    @PostMapping("/admin/product/batchUpdate")
    public ApiRestResponse batchUpdateProduct(@Valid @RequestBody List<UpdateProductReq> updateProductReqList) {
        for (int i = 0; i < updateProductReqList.size(); i++) {
            UpdateProductReq updateProductReq = updateProductReqList.get(i);
            //方法一，手动校验
            if (updateProductReq.getPrice() < 1) {
                throw new MtxMallException(MtxMallExceptionEnum.PRICE_TOO_LOW);
            }
            if (updateProductReq.getStock() > 10000) {
                throw new MtxMallException(MtxMallExceptionEnum.STOCK_TOO_MANY);
            }
            Product product = new Product();
            BeanUtils.copyProperties(updateProductReq, product);
            productService.update(product);
        }
        return ApiRestResponse.success();
    }

    @ApiOperation("后台批量更新商品，ValidList验证")
    @PostMapping("/admin/product/batchUpdate2")
    public ApiRestResponse batchUpdateProduct2(@Valid @RequestBody ValidList<UpdateProductReq> updateProductReqList) {
        for (int i = 0; i < updateProductReqList.size(); i++) {
            UpdateProductReq updateProductReq = updateProductReqList.get(i);
            Product product = new Product();
            BeanUtils.copyProperties(updateProductReq, product);
            productService.update(product);
        }
        return ApiRestResponse.success();
    }


    @ApiOperation("后台批量更新商品，@Validated验证")
    @PostMapping("/admin/product/batchUpdate3")
    public ApiRestResponse batchUpdateProduct3(@Valid @RequestBody List<UpdateProductReq> updateProductReqList) {
        for (int i = 0; i < updateProductReqList.size(); i++) {
            UpdateProductReq updateProductReq = updateProductReqList.get(i);
            Product product = new Product();
            BeanUtils.copyProperties(updateProductReq, product);
            productService.update(product);
        }
        return ApiRestResponse.success();
    }


}
