package com.springboot.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 上传文件控制器
 * 直接上传到服务器
 * @author 刘厅
 * 2019.3.25
 */
@Controller
public class UploadController {
    //指定一个临时路径
//    private static String UPLOAD_FOLDER="E:/Temp/";

    @GetMapping("/")
    public String index(){
        return "upload";
    }
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file")MultipartFile srcFile,
                             RedirectAttributes redirectAttributes){
        //前端没有上传文件
        if(srcFile.isEmpty ()){
            redirectAttributes.addFlashAttribute ( "message","请选择一个文件" );
            return "redirect:upload_status";
        }
        //选择了文件，开始进行上传操作
        try{
            //格式化一个UUID字符串
            java.util.UUID uuid = java.util.UUID.fromString("63982000-bc30-ab45-df12-8752fbac99");
            System.out.println("格式化后的值: " + uuid);
            //生成一个UUID字符串
            System.out.println("生成一个UUID: " + uuid.randomUUID());
            String newImgPath = "";
            int fs = srcFile.getOriginalFilename().lastIndexOf(".");
            String extention= srcFile.getOriginalFilename().substring(fs);
            newImgPath = uuid.randomUUID() + extention;
            //构建上传目标路径,找到了项目的target的classes目录
            File descFile =new File (
                    ResourceUtils.getURL("classpath:").getPath());
            if(!descFile.exists ()){
                descFile =new File ( "" );
            }
            //输出目标文件的绝对路径
            System.out.println ( "file path:"+descFile.getAbsolutePath () );
            //拼接static目录
            Date date = new Date();
            String fn=descFile.getAbsoluteFile ()+new SimpleDateFormat ("yyyy/MM/dd/").format(date);
            File upload=new File (fn);
            //若目标文件夹不存在，则创建一个
            if(!upload.exists ()){
                upload.mkdirs ();
            }
            System.out.println ( "完整的上传路径："+upload.getAbsolutePath () +"/"+newImgPath);
            //根据srcFile的大小，准备一个字节数组
            byte[] bytes=srcFile.getBytes ();
            //拼接上传路径
//            Path path= Paths.get ( UPLOAD_FOLDER + srcFile.getOriginalFilename () );
            //通过项目路径，拼接上传路径
            Path path= Paths.get ( upload.getAbsolutePath ()+"/"+newImgPath );
            //最重要的一步，将源文件写入目标地址！！！
            Files.write ( path,bytes);
            //将文件上传成功的信息写入message
            redirectAttributes.addFlashAttribute ( "message","文件上传成功！" +newImgPath);
        }catch (IOException e){
            e.printStackTrace ();
        }
        return "redirect:upload_status";
    }
    //匹配upload_status页面
    @GetMapping("/upload_status")
    public String uploadStatusPage(){
        return "upload_status";
    }
}
