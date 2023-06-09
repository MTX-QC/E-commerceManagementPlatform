package com.mtx.mall.service.impl;

import com.mtx.mall.common.Constant;
import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.exception.MtxMallExceptionEnum;
import com.mtx.mall.model.dao.CartMapper;
import com.mtx.mall.model.dao.ProductMapper;
import com.mtx.mall.model.pojo.Cart;
import com.mtx.mall.model.pojo.Product;
import com.mtx.mall.model.vo.CartVO;
import com.mtx.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 描述：购物车Service实现类
 *
 * */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVO> list(Integer userId) {
        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }


    //添加商品到购物车
    @Override
    public List<CartVO> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经再购物车里了，则数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    private void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，商品是否上架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new MtxMallException(MtxMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()) {
            throw new MtxMallException(MtxMallExceptionEnum.NOT_ENOUGH);
        }
    }


    //更新购物车
    @Override
    public List<CartVO> update(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法更新
            throw new MtxMallException(MtxMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经再购物车里了，则更新数量
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setId(cart.getId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setUserId(cart.getUserId());
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return this.list(userId);
    }

    //删除购物车内的商品
    @Override
    public List<CartVO> delete(Integer userId, Integer productId) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法删除
            throw new MtxMallException(MtxMallExceptionEnum.DELETE_FAILED);
        } else {
            //这个商品已经再购物车里了，则可以删除
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
        return this.list(userId);
    }

    //选中购物车内的商品
    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法选中，没意义
            throw new MtxMallException(MtxMallExceptionEnum.UPDATE_FAILED);
        } else {
            //这个商品已经再购物车里了，则可以选中
            cartMapper.selectOrNot(userId, productId, selected);
        }
        return this.list(userId);
    }

    //全选中/全不选中购物车内的商品
    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected) {
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.list(userId);
    }

}
