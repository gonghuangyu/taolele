package cn.tedu.product.service;

import cn.tedu.product.mapper.ProductMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {
    @Autowired(required = false)
    private ProductMapper productMapper;

    public EasyUIResult queryByPage(Integer page, Integer rows) {
        /*
            目的:新建一个EasyUIResult返回的对象,将其属性数据封装
            int total
            List rows
         */
        EasyUIResult result=new EasyUIResult();
        //total 是总数 select count(*) from t_product;
        int total=productMapper.selectCount();
        result.setTotal(total);
        //封装当前分页对应的一批product集合
        //select * from t_product limit start,rows;
        int start=(page-1)*rows;
        List<Product> products
            =productMapper.selectProductByPage(start,rows);
        result.setRows(products);
        return result;
        //{"total":65,"rows":[{"productName":"计算机"},{"productName":"计算机"}]}
    }
    @Autowired
    private StringRedisTemplate redisTemplate;
    public Product queryOneProduct(String productId) {
        /*
            准备一个商品对应的key 一个商品一个key
            查询商品数据,是否缓存命中
            命中,直接使用数据
            未命中,到数据库查询,放到缓存一份,供后续使用
         */
        String key="product_"+productId;
        if(redisTemplate.hasKey(key)){
            //命中,pJson商品json字符串
            String pJson = redisTemplate.opsForValue().get(key);
            try{
                return MapperUtil.MP.readValue(pJson,Product.class);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }else {
            //没命中,访问数据库
            Product product=
                    productMapper.selectProductById(productId);
            //创建value值,使用代码存储到redis一份
            try{
                String pJson
                        = MapperUtil.MP.writeValueAsString(product);
                //设置2天超时时间
                redisTemplate.opsForValue()
                        .set(key,pJson,2, TimeUnit.DAYS);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return product;
        }
    }

    public void addProduct(Product product) {
        /*
            product 的id是空的 补充完成
            1100(南京) 89955(江宁区) 20100908(时间) 0776(操作员编号)
            其他方式,生成字符串,保证唯一
            product_+currentTime/UUID 通用唯一标识码,没有任何业务意义
            05e20c1a-0401-4c0a-82ab-6fb0f37db397,仅有的特点,就是每次生成
            都是一个独一无二的字符串.
         */
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //创建value值,使用代码存储到redis一份
        String key="product_"+productId;
        try{
            String pJson
                    = MapperUtil.MP.writeValueAsString(product);
            //设置2天超时时间
            redisTemplate.opsForValue()
                    .set(key,pJson,2, TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
        productMapper.insertProduct(product);
    }

    public void editProduct(Product product) {
        /*
            每次修改商品数据,对应缓存查看是否存在缓存,存在先将缓存删除,再更新数据库
            直接发现缓存存在,更新缓存内容
         */
        String key="product_"+product.getProductId();
        try{
            String pJson
                    = MapperUtil.MP.writeValueAsString(product);
            redisTemplate.opsForValue()
                    .setIfPresent(
                            key,
                            pJson,
                            2,
                            TimeUnit.DAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
        productMapper.updateProductById(product);
    }
}
