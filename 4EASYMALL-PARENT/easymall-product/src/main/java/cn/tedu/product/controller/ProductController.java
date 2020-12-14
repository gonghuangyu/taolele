package cn.tedu.product.controller;

import cn.tedu.product.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/manage")
public class ProductController {
    /*
        /product/manage/pageManage
        Integer page,Integer rows
        EasyUIResult common-resources
     */
    @Autowired
    private ProductService productService;
    @RequestMapping(value="/pageManage",method = {RequestMethod.GET,RequestMethod.POST})
    public EasyUIResult queryByPage(Integer page,Integer rows){
        //直接调用service功能
        EasyUIResult result=productService.queryByPage(page,rows);
        return result;
    }
    //单个商品查询
    @RequestMapping("/item/{productId}")
    public Product queryOneProduct(@PathVariable("productId")
                                   String productId){
        return productService.queryOneProduct(productId);
    }
    //新增商品
    @RequestMapping("/save")
    public SysResult addProduct(Product product){
        try{
            productService.addProduct(product);
            return SysResult.ok();
            //SysResult.build(200,"新增成功","1");
            //{"status":200,"msg":"ok","data":null}
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "新增商品失败",
                    null);
        }
    }
    //更新商品
    @RequestMapping("/update")
    public SysResult editProduct(Product product){
        try{
            productService.editProduct(product);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return SysResult.build(
                    201,
                    "更新失败",
                    null);
        }
    }
}
