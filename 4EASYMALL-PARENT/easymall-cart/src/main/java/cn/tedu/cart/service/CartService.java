package cn.tedu.cart.service;

import cn.tedu.cart.mapper.CartMapper;
import cn.tedu.product.api.ProductApi;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class CartService {
    @Autowired(required = false)
    private CartMapper cartMapper;
    public List<Cart> queryMyCarts(String userId) {
        return cartMapper.selectCartsByUserid(userId);
    }
    /*@Autowired
    private RestTemplate restTemplate;*/
    @Autowired
    private ProductApi productApi;
    public void addCart(Cart cart) {
        /*  cart userId productId num
            1.从数据查询是否已存在exist
                1.1存在:执行更新逻辑 num
                1.2不存在:执行新增
            2.更新num
                exist.num+cart.num 更新数据库
            3.新增cart
                3.1从商品微服务中,调用接口 http://product-service
                /product/manage/item/{productId}
                3.2补齐 数据cart
         */
        Cart exist=
                cartMapper.selectExistByUseridAndProductid(cart);
        if(exist!=null){
            //更新,不空是存在
            cart.setNum(exist.getNum()+cart.getNum());
            cartMapper.updateNumByUseridAndProductid(cart);
        }else{

            Product product = productApi
                    .queryOneProduct(cart.getProductId());
            //补齐cart对象
            cart.setProductImage(product.getProductImgurl());
            cart.setProductPrice(product.getProductPrice());
            cart.setProductName(product.getProductName());
            cartMapper.insertCart(cart);
        }
    }

    public void updateCart(Cart cart) {
        cartMapper.updateNumByUseridAndProductid(cart);
    }

    public void deleteCart(Cart cart) {
        cartMapper.deleteCartByUseridAndProductid(cart);
    }
}
