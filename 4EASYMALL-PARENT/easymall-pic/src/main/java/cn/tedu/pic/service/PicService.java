package cn.tedu.pic.service;

import com.jt.common.utils.UploadUtil;
import com.jt.common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class PicService {
    /*
        1.校验图片是否正确 (后缀验证 png gif jpg jpeg...)
        2.生成该图片的多级路径 /uplaod/a/v/3/d/f/6/h/d/
        3.重命名图片文件,避免考虑覆盖的问题
        4.将流数据输出到磁盘文件,图片上传保存完成了
        5.利用之前生成的变量,拼接url地址,存储到PicUploadResult中返回
        给ajax使用.
     */
    public PicUploadResult picUpload(MultipartFile pic) {
        PicUploadResult result=new PicUploadResult();
        //拿到原名称 originalFileName
        String oName = pic.getOriginalFilename();
        //解析后缀 sky.png
        String extName=oName.substring(oName.lastIndexOf("."));
        //比对名称.图片后缀中的议员
        //利用正则表达式 match
        String regex=".(png|gif|jpg|jpeg)$";
        boolean matches = extName.matches(regex);
        if(!matches){
            //文件名称后缀不合法 返回一个PicUploadResult
            result.setError(1);
            return result;
        }
        //uplaod/a/v/3/d/f/6/h/d/ upload:业务前缀,
        //上传的图片很多种,一个上传图片服务,提供多个系统调用
        //user-head-img/ shop-logo/ product-img/
        //后面的是多级路径 多级路径特点 分开处理小量的图片
        //调用UploadUtil /easymall/a/c/e/g/5/g/4/d/
        String path=UploadUtil.getUploadPath(
                UUID.randomUUID().toString(),
                "/easymall")+"/";
        //图片上传,有原名称,原名称是可以重复的,每次让他不重复
        String newFileName=System.currentTimeMillis()+extName;
        //path+newFileName d:/img/easymall/a/c/e/g/5/g/4/d/163726282743.png
        //多级路径创建出来
        File dir=new File("d:/img"+path);
        if(!dir.exists()){
            //不存在,创建多级目录
            dir.mkdirs();
        }
        try{
            //使用pic数据,输出图片文件到磁盘中
            pic.transferTo(new File(
                    "d:/img"
                    +path+newFileName));
        }catch(Exception e){
            e.printStackTrace();
            result.setError(1);
            return result;
        }
        //d:/img/easymall/a/c/e/g/5/g/4/d/163726282743.png
        //url:http://image.jt.com/easymall/a/c/e/g/5/g/4/d/163726282743.png
        String url="http://image.jt.com/"+path+newFileName;
        result.setUrl(url);
        return result;//{"error":0,"url":"http://image.jt.com/easymall/a/c/e/g/5/g/4/d/163726282743.png"}
    }
}
