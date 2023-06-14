package com.mtx.mall.service;

import com.github.pagehelper.PageInfo;
import com.mtx.mall.model.request.CreateOrderReq;
import com.mtx.mall.model.vo.CartVO;
import com.mtx.mall.model.vo.OrderVO;

import java.util.List;

/**
 * 描述：  订单Service
 * */
public interface OrderService {


    String create(CreateOrderReq createOrderReq);

    OrderVO detail(String orderNo);

    //前台订单列表
    PageInfo listForCustomer(Integer pageNum, Integer pageSize);

    //前台取消订单
    void cancel(String orderNo);

    //生成支付二维码
    String grcode(String orderNo);

    //支付接口
    void pay(String orderNo);

    //后台订单列表
    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    //发货
    void deliver(String orderNo);

    //完结订单
    void finish(String orderNo);
}
