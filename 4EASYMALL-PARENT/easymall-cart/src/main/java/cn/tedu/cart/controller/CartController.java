package cn.tedu.cart.controller;

import cn.tedu.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/cart/manage")
public class CartController {
    @Autowired
    private CartService cartService;
    //查询我的购物车,使用userId查询
    @RequestMapping("/query")
    public List<Cart> queryMyCarts(String userId){
        return cartService.queryMyCarts(userId);
    }
    //新增我的购物车
    @RequestMapping("/save")
    public SysResult addCart(Cart cart){
        //cart只有userId productId num
        try{
            cartService.addCart(cart);
            return SysResult.ok();
        }catch (Exception e){
           e.printStackTrace();
           return SysResult.build(
                   201,
                   "新增购物车失败",
                   null);
        }
    }
    /*更新num*/
    @RequestMapping("/update")
    public SysResult updateCart(Cart cart){
        try{
            cartService.updateCart(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "更新失败",
                    null);
        }
    }
    /*删除购物车*/
    @RequestMapping("/delete")
    public SysResult deleteCart(Cart cart){
        try{
            cartService.deleteCart(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "删除失败",
                    null);
        }
    }
}
