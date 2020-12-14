package cn.tedu.controller;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ESController {
    /*
        创建索引文件
        请求地址:/{indexName} put请求 将索引创建
        参数:路径
        返回值:{"acknowledeged":"true"}
     */
    @RequestMapping(value="/{indexName}",method = RequestMethod.PUT)
    public String createIndex(@PathVariable(value = "indexName")String indexName){
        //lucene 代码创建索引
        //所有的索引都创建在d:/indieces/
        String url="d:/indices/"+indexName;
        String url1="d:/indices/"+indexName+"/0";
        String url2="d:/indices/"+indexName+"/1";
        String url3="d:/indices/"+indexName+"/2";
        String url4="d:/indices/"+indexName+"/3";
        String url5="d:/indices/"+indexName+"/4";

        //如果发现这个文件夹存在,则报错,告诉客户端已经有了,你还建什么
        File dir=new File(url);
        try{
            if(dir.exists()){
                return "{\"acknowledeged\":false}";
            }else{
                Path path = Paths.get(url2);
                FSDirectory _dir=FSDirectory.open(path);
                IndexWriterConfig config=new IndexWriterConfig(new StandardAnalyzer());
                config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
                IndexWriter writer =new IndexWriter(_dir,config);
                writer.commit();
                writer.deleteAll();
                return  "{\"acknowledeged\":true}";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "{\"acknowledeged\":false}";
        }
    }
    @RequestMapping(value="/{indexName}",method=RequestMethod.DELETE)
    public String deleteIndex(@PathVariable(value = "indexName")String indexName){
        String url="d:/indices/"+indexName;
        File file=new File(url);
        System.out.println("删除了该文件:"+file.getAbsolutePath());
        return "{\"acknowledeged\":true}";
    }
    /*
        接收数据,放入索引
        请求地址:
     */
}
