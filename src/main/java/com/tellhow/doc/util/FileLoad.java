package com.tellhow.doc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @author: 韩聪寅
 * @create: 2019-11-27
 **/
@Service
public class FileLoad {
    @Value("${fileAddress}")
    private String fileAddress;


    public void download(String address, String fileName, HttpServletResponse response, int i) {
        try {
            /*path是指欲下载的文件的路径。*/
            String path = "";
            path = i == 1 ? fileAddress + address + fileName : address;
            fileName = i == 1 ? fileName : "docs.zip";
            File file = new File(path);
            InputStream fileInput = new FileInputStream(path);
            int count = 0;
            while (count == 0) {
                count = fileInput.available();
            }
            byte[] buffer = new byte[count];
            fileInput.read(buffer);
            fileInput.close();
            /*清空response*/
            response.reset();
            /*设置response的Header*/
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            OutputStream toClient = response.getOutputStream();
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.close();
            if (i == 0) {
                file.delete();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String fileUpload(MultipartFile file, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        String path = fileAddress + request.getParameter("address") + "/" + file.getOriginalFilename();
        File newFile = new File(path);
        /*通过MultipartFile的方法直接写文件*/

        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("采用file.Transto的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "/success";
    }


}
