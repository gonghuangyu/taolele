package cn.tedu.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchIndex {
    /*
        1.指向索引
        2.创建搜索对象(包装了输入流 先读倒排索引表,计算搜索)
        3.创建搜索query条件
        4.查询结果遍历循环输出(浅查询逻辑)
     */
    @Test
    public void termQuery() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //生成输入流reader
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //创建查询条件
        //TermQuery term词项 2个属性 域名 词语
        Term term=new Term("content","中");
        Query query=new TermQuery(term);
        //浅查询的过程
        TopDocs topDocs = searcher.search(query, 10);//10和分页有关,读取前10条的计算结果
        //TopDocs的内容是searcher读取倒排索引表,计算结果,前十条
        //进行计算TopDocs,计算结束,获取docId 在读取数据
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //数组中包含了查询到的最多前10条documentId 最多10个元素
        //每个元素包含一个docId 0 1 2 3 4 5
        /*int start=(page-1)*rows;
        for(int i=0;i<page*rows;i++){
            if(i>=start){
                //满足起始条件下标,开始读数据;
                int docId = scoreDocs[i].doc;
                Document doc = searcher.doc(docId);
                System.out.println("title:"+doc.get("title"));
                System.out.println("content:"+doc.get("content"));
                System.out.println("publisher:"+doc.get("publisher"));
                System.out.println("click:"+doc.get("click"));
                System.out.println("web:"+doc.get("web"));
            }
        }*/
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }
    //多域查询
    @Test
    public void multiFieldQuery() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //生成输入流reader
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //TODO 创建查询条件
        //多域查询
        String[] fields={"title","content"};
        //拿到文本解析器
        MultiFieldQueryParser parser=
                new MultiFieldQueryParser(fields,new StandardAnalyzer());
        Query query=parser.parse("中国,美国");
        //parse 多个域,和分词器,
        //使用分词器对text文本查询进行分词计算 中,国,美,国
        //做排列组合 title:中,title:美,title:国,content:中,content:美,content:国
        //浅查询的过程
        TopDocs topDocs = searcher.search(query, 10);//10和分页有关,读取前10条的计算结果
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }
    //布尔查询
    @Test
    public void booleanQuery() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //生成输入流reader
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //TODO 创建查询条件
        //准备子条件 子条件单独使用可以查询一批结果
        Query query1=new TermQuery(new Term("content","美"));
        Query query2=new TermQuery(new Term("title","美"));
        //利用2个query构造2个boolean子条件对象
        BooleanClause clause1=new BooleanClause(query1, BooleanClause.Occur.MUST_NOT);
        BooleanClause clause2=new BooleanClause(query2, BooleanClause.Occur.MUST);
        Query query=new BooleanQuery.Builder().add(clause1).add(clause2)
                .build();
        /*
            MUST: boolean结果必须是当前query的子集
            MUST_NOT:boolean结果必须不是当前query的子集
         */
        //浅查询的过程
        TopDocs topDocs = searcher.search(query, 10);//10和分页有关,读取前10条的计算结果
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            float score = scoreDoc.score;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }
    //范围查询
    @Test
    public void rangeQuery() throws Exception {
        //指向d:/index01
        Path path = Paths.get("d:/index01");
        FSDirectory dir=FSDirectory.open(path);
        //生成输入流reader
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher searcher=new IndexSearcher(reader);
        //TODO 创建查询条件
        //范围搜索条件,click 0-60 60-100搜索
        Query query =
                IntPoint.newRangeQuery(
                        "click",
                        60,
                        90);
        //浅查询的过程
        TopDocs topDocs = searcher.search(query, 10);//10和分页有关,读取前10条的计算结果
        System.out.println(topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int docId = scoreDoc.doc;
            //有了id才读数据
            Document doc = searcher.doc(docId);
            System.out.println("title:"+doc.get("title"));
            System.out.println("content:"+doc.get("content"));
            System.out.println("publisher:"+doc.get("publisher"));
            System.out.println("click:"+doc.get("click"));
            System.out.println("web:"+doc.get("web"));
        }
    }
}
