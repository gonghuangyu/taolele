package cn.tedu.index;

import com.mysql.jdbc.Driver;
import com.sun.codemodel.internal.fmt.JTextFile;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateIndex {
    /*
        1.索引创建在磁盘中的,指定一个磁盘目录 d:/index01
        2.准备输出流writer向文件夹写出索引数据
        3.封装一下需要输出到索引文件的所有document对象
            了解document的结构 和域属性特点
        4.writer将输出输出
     */

    @Test
    public void createIndex() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //创建一个writer输出流 计算分词合并,分词器,输出位置dir
        IndexWriterConfig config=
                new IndexWriterConfig(new StandardAnalyzer());
        //输出,判断d:/index01是否存在
        /*
               CREATE: 存在则覆盖,不存在则创建
               APPEND: 存在则追加,不存在则报错
               CREATE_OR_APPEND:有则追加,无则创建
         */
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer=new IndexWriter(dir,config);
        //封装document对象
        Document doc1=new Document();
        Document doc2=new Document();
        /*
           	title:美国耍赖
			content:频繁甩锅,控诉中国疫情信息透露不明确
			publisher:新华网
			click:58
			webaddress: http://www.news.com/1.html
        */
        doc1.add(new TextField(
                "title","美国耍赖", Field.Store.YES));
        //域属性有什么类型,对应什么数据
        //Store.YES/NO什么意思
        doc1.add(new TextField("content","频繁甩锅,控诉中国疫情信息透露不明确",
                Field.Store.YES));
        doc1.add(new TextField("publisher","新华网", Field.Store.YES));
        doc1.add(new IntPoint("click",58));
        doc1.add(new TextField("web","http://www.news.com/1.html", Field.Store.YES));
        /*
            title:美国甩锅
			content:美国谎称,援助中国1亿美金,中国外交部称从未收到
			publisher:新华网
			click:66
			webaddress: http://www.news.com/2.html
         */

        doc2.add(new TextField(
                "title","美国甩锅", Field.Store.YES));
        //域属性有什么类型,对应什么数据
        //Store.YES/NO什么意思
        doc2.add(new TextField("content","美国谎称,援助中国1亿美金,中国外交部称从未收到",
                Field.Store.YES));
        doc2.add(new TextField("publisher","新华网", Field.Store.YES));
        doc2.add(new IntPoint("click",66));
        doc2.add(new StringField("click","66次", Field.Store.YES));
        doc2.add(new StringField("web","这个网站不存在", Field.Store.YES));
        //writer创建索引
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
        System.out.println("索引创建完毕");
    }
}
