package cn.tedu.mapper;

import cn.tedu.domain.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    Order selectOrderByOrderid(@Param("orderId")String orderId);
}
