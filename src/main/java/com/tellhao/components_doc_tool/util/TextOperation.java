package com.tellhao.components_doc_tool.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class TextOperation {
    @Value("${fileaddress}")
    private String fileaddress;
    //, @Value("hgf") String sss
    //    字符串存文本
//    public String saveAsFileWriter(String content, String addressname) {
//        String filePath = "";
//        if (addressname.equals("_sidebar.md")) {
//            filePath = "G:/JAVA/components-doc/docs/" + addressname;
//        } else {
//            filePath = "G:/JAVA/components-doc/docs/guide/" + addressname;
//        }
//
//        FileWriter fwriter = null;
//        try {
//            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
//            fwriter = new FileWriter(filePath);
//            fwriter.write(content);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } finally {
//            try {
//                fwriter.flush();
//                fwriter.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return filePath;
//    }

    public int saveAsFileWriter(String content, String filename,String addressname){
        String filePath="";
        if(addressname.lastIndexOf(".")<0){
            filePath=(filename.lastIndexOf(".")<0)? fileaddress+addressname+"\\"+filename+".md" : fileaddress+addressname+"\\"+filename;
        }else {
            filePath = addressname;
        }
        FileWriter fwriter = null;
        try {
            // 覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    //    文本转字符串
    public String turnFileTxt(String path,String Filename) {
        String content = "";
        try {
            /* 读入TXT文件 */
            String pathname = fileaddress+path+Filename; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line ="";
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line == null) {
                    content = content+"";
                } else {
                    content += "\r\n" + line;
                }
//                System.out.println(content);
            }
            br.close();
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public String munFileTxt() {
        String content = "";
        try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
            String pathname = "G:/JAVA/components-doc/docs/_sidebar.md"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
            File filename = new File(pathname); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据
                if (line == null) {
                    content += "\r\n";
                } else {
                    content = content + "\r\n" + line;
                }
//                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
