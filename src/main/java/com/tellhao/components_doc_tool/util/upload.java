package com.tellhao.components_doc_tool.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
@Service
public class upload {
    @Value("${fileaddress}")
    private String fileaddress;

    public String  fileUpload2(MultipartFile file, HttpServletRequest request) throws IOException {
        long  startTime=System.currentTimeMillis();
        String path=fileaddress+request.getParameter("address")+"\\"+new Date().getTime()+file.getOriginalFilename();
        File newFile=new File(path);
        //通过MultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long  endTime=System.currentTimeMillis();
        System.out.println("采用file.Transto的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/success";
    }
}
