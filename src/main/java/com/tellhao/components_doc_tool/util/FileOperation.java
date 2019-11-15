package com.tellhao.components_doc_tool.util;

import com.tellhao.components_doc_tool.entity.Treevo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileOperation {
    @Value("${fileaddress}")
    private String fileaddress;

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
   public List getfiletree(String dir,List list) throws IOException {

        List<Treevo> tree=new ArrayList<Treevo>();
        File file=new File(dir.trim());
       Treevo treevo = new Treevo();
       treevo.setId(file.getName());
       treevo.setText(file.getName());
       treevo.setChildren(list);
       tree.add(treevo);
       return tree;
    }
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
    //真删除文件--------------------------------------------------------------------------
    public int truedelFile(String delname) {
        try {
            File file = new File(delname);
            if (file.exists()) {
                file.delete();
                return  1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    //文件名的修改-------------------------------------------------------------------------
    public int renameFile(String path, String rename,String oldname) {
        String oldnamed=fileaddress+path+oldname;
        File oldfile = new File(oldnamed);
        String  newname=fileaddress+path+rename;
        File  refile= new File(newname);
        //检查要重命名的文件是否存在，是否是文件
        if (!oldfile.exists() && refile.exists()){
            return 0;
        } else {
            return oldfile.renameTo(refile)?  1:0;
        }

    }

     // 新创文件夹
    public String newfile(String path,String filename){

        String address=fileaddress+path;
        int j=1;
           File file=new File(address);
        String[] fileList = file.list();
        for (int i = 0; i < fileList.length; i++) {
            if(fileList[i].equals(filename)){
                filename="Newfile"+j;
                j++;
                i=0;
            }
        }
  File newfile=new File(address+filename);
        newfile.mkdirs();
return filename;
    }


}
