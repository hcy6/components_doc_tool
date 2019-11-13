package com.tellhao.components_doc_tool.util;

import com.tellhao.components_doc_tool.entity.Treevo;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileOperation {


    //    实验代码
    public List getTrailFileTs(String dir) {

        int i=dir.lastIndexOf("/");
        int j=dir.lastIndexOf(".");
        List trailFilesTsArray = new ArrayList<>();
        File trailDir = new File(dir.trim());
        for (File file : trailDir.listFiles()) {
            if (file.isFile()) {
                trailFilesTsArray.add(file.getName());
//               String p= file.getParentFile().getName();
//               String a=p;
            }
        }
        Arrays.sort(trailFilesTsArray.toArray());
        return trailFilesTsArray;

    }

    //取出目录下的所有文件-------------------------------------------------------------
//    public List getfiletree(String dir) throws IOException {
//
//     return getfiletree(dir,".md");
//    }
    public List getfiletree(String dir,String extension) throws IOException {

        FileOperation get = new FileOperation();
        List<Treevo> tree = new ArrayList<Treevo>();
        File trailDir = new File(dir.trim());
        List<Treevo> tree1 = new ArrayList<Treevo>();
        for (File file : trailDir.listFiles()) {
            Treevo treevo = new Treevo();
            if (file.isFile()) {
                if (extensionfile(file.getName(),extension)) {
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getAbsolutePath());
                    tree.add(treevo);
                }
            } else {
                tree1=get.getfiletree(file.getCanonicalPath(),extension);
                if(tree1.size()>0){
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getAbsolutePath());
                    treevo.setChildren(tree1);
                    tree.add(0, treevo);
            }
            }
        }
//    if(tree.size()!=0 && tree.get(0).getId()!=file.getParentFile().getName()) {
//        treevo.setId(file.getParentFile().getName());
//        treevo.setText(file.getParentFile().getName());
//        treevo.setChildren(tree);
//        tree.add(0,treevo);
//    }
        return tree;

    }
    public  Boolean extensionfile(String file,String extension){

        int flag=0;
        String f=file.substring(file.lastIndexOf("."));
        String[] strAry = extension.split(",");
        for(int i=0;i<strAry.length;i++){
            if(f.equals(strAry[i])){
                flag=1;
                break;
            }
        }
        return flag==1? true:false;
    }



    //删除文件--------------------------------------------------------------------------
    public int delFile(String delname) {
        try {
            File file = new File(delname);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
            String date = dateformat.format(new Date());
//                DateFormat date = new SimpleDateFormat("yyyyMMdd");
                if (file.exists()) {
                File back=new File(delname+"."+date+".back");
                return  file.renameTo(back)? 1:0;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    //文件名的修改-------------------------------------------------------------------------
    public int renameFile(String file, String toFile) {
        File Renamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件
        if (!Renamed.exists()) {
            System.out.println("File does not exist: " + file);
            return 0;
        } else {
//            String newFile=new String(toFile);
            File f = new File(toFile);
            if (Renamed.renameTo(f)) {
                System.out.println(f.getName());
                return 1;
            } else {
                return 0;
            }
        }

    }


}
