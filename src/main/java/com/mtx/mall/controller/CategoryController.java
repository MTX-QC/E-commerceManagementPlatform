package com.mtx.mall.controller;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.common.ApiRestResponse;
import com.mtx.mall.common.Constant;
import com.mtx.mall.exception.MtxMallExceptionEnum;
import com.mtx.mall.filter.UserFilter;
import com.mtx.mall.model.pojo.Category;
import com.mtx.mall.model.pojo.User;
import com.mtx.mall.model.request.AddCategoryReq;
import com.mtx.mall.model.request.UpdateCategoryReq;
import com.mtx.mall.model.vo.CategoryVO;
import com.mtx.mall.service.CategoryService;
import com.mtx.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 描述：    目录Controller
 * */
@Controller
//@CrossOrigin(origins = "http://localhost:8080", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS})
public class CategoryController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;
    /**
     * 后台添加目录
     * */
    @ApiOperation("后台添加目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryReq addCategoryReq){
//        User currentUser  = (User)session.getAttribute(Constant.MTX_MALL_USER);
        User currentUser = UserFilter.userThreadLocal.get();
        if (currentUser == null){
            return ApiRestResponse.error(MtxMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole){
            //是管理员，执行操作
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MtxMallExceptionEnum.NEED_ADMIN);
        }

    }

    /**
     * 后台更新目录
     * */
    @ApiOperation("后台更新目录")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq,HttpSession session){
//        User currentUser  = (User)session.getAttribute(Constant.MTX_MALL_USER);
        User currentUser = UserFilter.userThreadLocal.get();
        if (currentUser == null){
            return ApiRestResponse.error(MtxMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole){
            //是管理员，执行操作
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }else {
            return ApiRestResponse.error(MtxMallExceptionEnum.NEED_ADMIN);
        }
    }

    /**
     * 后台删除目录
     * */
    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    /**
     * 后台目录列表
     * */
    @ApiOperation("后台目录列表")
    @GetMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,
                                                @RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
    /**
     * 前台目录列表
     * */
    @ApiOperation("前台目录列表")
    @GetMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer() {
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryVOS);
    }


}
