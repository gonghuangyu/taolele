package cn.tedu.cart.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> selectCartsByUserid(String userId);

    Cart selectExistByUseridAndProductid(Cart cart);

    void updateNumByUseridAndProductid(Cart cart);

    void insertCart(Cart cart);

    void deleteCartByUseridAndProductid(Cart cart);
}
