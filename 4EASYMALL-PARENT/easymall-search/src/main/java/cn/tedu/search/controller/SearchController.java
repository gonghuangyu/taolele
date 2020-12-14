package cn.tedu.search.controller;

import cn.tedu.search.service.SearchService;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;
    /*
        easymall 首页搜索数据 query 是个文本
        page rows分页数据
     */
    @RequestMapping("/search/manage/query")
    public List<Product> search(
            @RequestParam("query") String text,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "rows",defaultValue = "5") Integer rows){
        return searchService.search(text,page,rows);
    }
}
