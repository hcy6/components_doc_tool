package com.tellhao.components_doc_tool.util;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetAll {
    List list = new ArrayList(100);

    public List find(String pathName) throws IOException {

        //获取pathName的File对象
        File dirFile = new File(pathName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            System.out.println("do not exit");
        }
        //判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
                System.out.println(dirFile.getCanonicalFile());
            }

        }
        String[] fileList = dirFile.list();
        for (int i = 0; i < fileList.length; i++) {
            //遍历文件目录
            String string = fileList[i];
            //File("documentName","fileName")是File的另一个构造器
            File file = new File(dirFile.getPath(), string);
            String name = file.getName();
            //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
            if (file.isDirectory()) {
                //递归
                find(file.getCanonicalPath());
            } else {
                //如果是文件，则直接输出文件名
                if (name.endsWith(".md")) {
                    list.add(name);
                    list.add(file.getCanonicalPath());
//                    System.out.print(name);
//                    System.out.print("       ");
//                    System.out.println(file.getCanonicalPath());
                }
            }
        }
        return list;
    }


}
