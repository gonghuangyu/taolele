package cn.tedu.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class TestAnalyzer {
    //利用分词器的api对一个文本进行分词计算
    //打印分词的词语计算结果
    public void printTerm(Analyzer analyzer,String msg) throws IOException {
        //analyzer 传递的一个实现类对象 simple standard whiteSpace
        //msg 计算的文本
        //msg转化成流
        StringReader reader=new StringReader(msg);
        //analyzer的api将reader流数据进行分词计算
        TokenStream tokenStream =
                analyzer.tokenStream("test", reader);
        //reset 流指针
        tokenStream.reset();
        //获取其中token携带的所有分词词项属性 文本 偏移量等等
        CharTermAttribute attribute = tokenStream
                .getAttribute(CharTermAttribute.class);
        //移动指针,挨个获取文本打印结果
        while(tokenStream.incrementToken()){
            System.out.println(attribute.toString());
        }
    }
    //test调用
    @Test
    public void run() throws IOException {
        //准备一批分词器
        Analyzer a1=new StandardAnalyzer();//标准分词器,中文字,英文词
        Analyzer a2=new SimpleAnalyzer();//按照标点符号切分
        Analyzer a3=new WhitespaceAnalyzer();//空格分词器
        Analyzer a4=new SmartChineseAnalyzer();//智能中文分词 词语
        Analyzer a5=new IKAnalyzer6x();
        String msg="java编程思想";
        System.out.println("*********标准分词器***********");
        printTerm(a1,msg);
        System.out.println("*********简单分词器***********");
        printTerm(a2,msg);
        System.out.println("*********空格分词器***********");
        printTerm(a3,msg);
        System.out.println("*********中文分词器***********");
        printTerm(a4,msg);
        System.out.println("*********IK分词器***********");
        printTerm(a5,msg);
    }
}
