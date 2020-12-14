package cn.tedu.product.api;

import com.jt.common.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="product-service")
@RequestMapping("/product/manage")
public interface ProductApi {
    //单个商品查询
    @RequestMapping(value="/item/{productId}",method= RequestMethod.GET)
    public Product queryOneProduct(@PathVariable("productId") String productId);
}
