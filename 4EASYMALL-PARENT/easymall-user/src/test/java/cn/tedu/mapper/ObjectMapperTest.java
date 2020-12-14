package cn.tedu.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperTest {

    public static void main(String[] args){
        Point point=new Point();
        point.setGame("雷电");
        point.setPoint(5000);
        point.setUserId("王晓晓");
        //{"userId":"王晓晓","point":5000,"game":"雷电"}
        try{
            ObjectMapper mapper=new ObjectMapper();
            //从对象转化成string的json,write 输出
            String json = mapper.writeValueAsString(point);
            System.out.println(json);
            //从json转化成对象 read 输入
            Point point1 = mapper.readValue(json, Point.class);
            System.out.println(point1);
        }catch (Exception e){

        }
    }
}
