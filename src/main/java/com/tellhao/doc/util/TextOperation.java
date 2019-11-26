package com.tellhao.doc.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TextOperation {
    @Value("${fileaddress}")
    private String fileaddress;

    public int fileWrter(String filePath, String content) {
        FileWriter filewriter = null;
        try {
            filewriter = new FileWriter(filePath);
            filewriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("存入失败");
            return 0;
        } finally {
            try {
                filewriter.flush();
                filewriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("关闭流失败");
            }
        }
        return 1;
    }

    public int saveAsFileWriter(String content, String filename, String addressname) {
        String filePath = fileaddress + addressname + filename;
        File file = new File(filePath);
        return !file.isDirectory() ? file.exists() ? fileWrter(filePath, content) : 0 : 0;
    }

    public int newFileWriter(String content, String filename, String addressname) {
        String filePath = (filename.lastIndexOf(".") < 0) ? fileaddress + addressname + "/" + filename + ".md" : fileaddress + addressname + "/" + filename;
        File file = new File(filePath);
        return !file.exists() ? fileWrter(filePath, content) : 0;


    }

    //    文本转字符串
    public String turnFileTxt(String path, String Filename) {
        StringBuffer content = new StringBuffer();
        InputStreamReader reader = null;
        BufferedReader outreader = null;
        try {
            String pathname = fileaddress + path + Filename;
            File filename = new File(pathname);
            reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
            outreader = new BufferedReader(reader);
            String line = "";
            while (line != null) {
                line = outreader.readLine();
                if (line == null) {
                    content.append(" ");
                } else {
                    content.append("\r\n").append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("读数据有误");
        } finally {
            try {
                outreader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("关闭流失败");
            }

        }
        return content.toString();
    }

}


