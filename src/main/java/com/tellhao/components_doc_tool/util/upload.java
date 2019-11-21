package com.tellhao.components_doc_tool.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


@Service
public class upload {
    @Value("${fileaddress}")
    private String fileaddress;

    public String fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {
        long startTime = System.currentTimeMillis();
        String path = fileaddress + request.getParameter("address") + "/" + file.getOriginalFilename();
        File newFile = new File(path);

        //通过MultipartFile的方法直接写文件
        file.transferTo(newFile);
//        deleteFile(file);
        long endTime = System.currentTimeMillis();
        System.out.println("采用file.Transto的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "/success";
    }


//    public String upload(MultipartFile[] file, HttpServletRequest request){
//
//
//        for(MultipartFile f:file){
//
//            File file1 ;
//            String name="";
//            try {
//                if (f instanceof MultipartFile) {
//                    //转换成这个对象，然后我们需要通过里面的FileItem来获得相对路径
//                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
//                    name = f2.getFileItem().getName();
//                    System.out.println(name + "        ---------相对路径");
//
//                    file1 = new File("G:/pr/qwe" + "/" + name);
//                    file1.mkdirs();
//                    file1.createNewFile();
//                    f.transferTo(file1);
//                }
//                System.out.println(f.getOriginalFilename() + "   iii         --------");
//                System.out.println("sssss   ");
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        }
//
//
//        return "/";
//    }

}
