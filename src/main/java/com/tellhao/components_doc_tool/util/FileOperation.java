package com.tellhao.components_doc_tool.util;

import com.tellhao.components_doc_tool.entity.Treevo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class FileOperation {
    @Value("${fileaddress}")
    private String fileaddress;

    //取出目录下的所有文件-------------------------------------------------------------
    public List getfiletree(String dir, List list) throws IOException {

        List<Treevo> tree = new ArrayList<Treevo>();
        File file = new File(dir.trim());
        Treevo treevo = new Treevo();
        treevo.setId("/");
        treevo.setText("/");
        treevo.setChildren(list);
        tree.add(treevo);
        return tree;
    }

    public List getfiletree(String dir, String extension) throws IOException {

        FileOperation get = new FileOperation();
        List<Treevo> tree = new ArrayList<Treevo>();
        File trailDir = new File(dir.trim());
        List<Treevo> tree1 = new ArrayList<Treevo>();
        for (File file : trailDir.listFiles()) {
            Treevo treevo = new Treevo();
            if (file.isFile()) {
                if (extensionfile(file.getName(), extension)) {
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getName());
//                    file.getAbsolutePath()
                    tree.add(treevo);
                }
            }
            if (file.isDirectory() && !file.getName().endsWith(".back")) {
                tree1 = get.getfiletree(file.getCanonicalPath(), extension);
//                if(tree1.size()>0){}
                treevo.setId(file.getName());
                treevo.setText(file.getName());
                treevo.setPid(file.getName());
                treevo.setChildren(tree1);
                treevo.setUid(1);
                tree.add(0, treevo);

            }
        }
        return tree;

    }

    public List getdelfiletree(String dir, String extension) throws IOException {

        FileOperation get = new FileOperation();
        List<Treevo> tree = new ArrayList<Treevo>();
        File trailDir = new File(dir.trim());
        List<Treevo> tree1 = new ArrayList<Treevo>();
        for (File file : trailDir.listFiles()) {
            Treevo treevo = new Treevo();
            if (file.isFile()) {
                if (extensionfile(file.getName(), extension)) {
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getName());
//                    file.getAbsolutePath()
                    tree.add(treevo);
                }
            } else if (file.isDirectory() && file.getName().endsWith("back")) {
                tree1 = get.getdelfiletree(file.getCanonicalPath(), "true");
//                if (tree1.size() > 0) {}
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getName());
                    treevo.setChildren(tree1);
                    treevo.setUid(1);
                    tree.add(0, treevo);

            } else {
                tree1 = get.getdelfiletree(file.getCanonicalPath(), extension);
                if (tree1.size() > 0) {
                    treevo.setId(file.getName());
                    treevo.setText(file.getName());
                    treevo.setPid(file.getName());
                    treevo.setChildren(tree1);
                    treevo.setUid(1);
                    tree.add(0, treevo);
            }
            }
        }
        return tree;

    }
    //    public List getfiletree(String dir,String extension) throws IOException {
//
//        FileOperation get = new FileOperation();
//        List<Treevo> tree = new ArrayList<Treevo>();
//        File trailDir = new File(dir.trim());
//        List<Treevo> tree1 = new ArrayList<Treevo>();
//        for (File file : trailDir.listFiles()) {
//            Treevo treevo = new Treevo();
//            if (file.isFile()) {
//                if (extensionfile(file.getName(),extension)) {
//                    treevo.setId(file.getName());
//                    treevo.setText(file.getName());
//                    treevo.setPid(file.getName());
////                    file.getAbsolutePath()
//                    tree.add(treevo);
//                }
//            } else {
//                tree1=get.getfiletree(file.getCanonicalPath(),extension);
//                if(tree1.size()>0){
//                    treevo.setId(file.getName());
//                    treevo.setText(file.getName());
//                    treevo.setPid(file.getName());
//                    treevo.setChildren(tree1);
//                    tree.add(0, treevo);
//                }
//            }
//        }
//        return tree;
//
//    }


    public Boolean extensionfile(String file, String extension) {
        String f = file.substring(file.lastIndexOf("."));
        if (extension != "") {
            if (extension=="true"){return true;}else {
            int flag = 0;
            String[] strAry = extension.split(",");
            for (int i = 0; i < strAry.length; i++) {
                if (f.equals(strAry[i])) {
                    flag = 1;
                    break;
                }
            }
            return flag == 1 ? true : false;
        }
        } else if( !f.equals(".back") ) {
            return true;
        }else  {
            return false;
        }


    }


    //删除文件--------------------------------------------------------------------------
    public int delFile(String delname, String address) {
        try {
            String path = fileaddress + address + delname;
            File file = new File(path);
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
            String date = dateformat.format(new Date());
            if (file.exists()) {
                File back = new File(path + "." + date + ".back");
                return file.renameTo(back) ? 1 : 0;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public int delFile(String delname,String address,String extension) {
//        try {
//            String path=fileaddress+address+delname;
//            File file = new File(path);
//            SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
//            String date = dateformat.format(new Date());
//            if(file.isDirectory()){
//                delFile(path,date,1,extension);
//                return 1;
//            }else if (file.exists()) {
//                File back=new File(path+"."+date+".back");
//                return  file.renameTo(back)? 1:0;
//            } else {
//                return 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }

    //    public void delFile(String path,String date,String extension){
//     File delfile=new File(path);
//     String delpath="";
////     String[] fileList=file.list();
//        for (File file : delfile.listFiles()) {
//            if(file.isFile()){
//               if(extensionfile(file.getName(),extension)){
//                    File back=new File( path+"/"+file.getName()+"."+date+".back");
////                return  ? 1:0
//                    file.renameTo(back);
//                }
//
//            }else {
//                delpath=path+"/"+file.getName();
//                delFile(delpath,date,extension);
//            }
//        }
//    }
    //真删除文件--------------------------------------------------------------------------
//    真删除文件
    public int truedelFile(String delname, String address, int id) {

        try {
            File file = new File(fileaddress + address + delname);
            String delfilename = "";

            if (id == 0) {

                if (file.exists()) {
                    if (file.isFile()) {
                        file.delete();
                        return 1;
                    } else {
                        for (File delfile : file.listFiles()) {
                            if (delfile.isFile()) {
                                if (delfile.getName().endsWith(".back")) {
                                    delfile.delete();
                                }
                            } else {
                                delfilename = delname + "/" + delfile.getName();
                                truedelFile(delfilename, address, id);
                            }
                        }
                        return 2;
                    }


                } else {
                    return 0;
                }
            } else {
                for (File delfile : file.listFiles()) {
                    if (delfile.isFile()) {
                        delfile.delete();
                    } else {
                        delfilename = delname + "/" + delfile.getName();
                        truedelFile(delfilename, address, id);
                    }
                    file.delete();
                }
                file.delete();
                return 1;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    //文件名的修改-------------------------------------------------------------------------
    public int renameFile(String path, String rename, String oldname) {
        String oldnamed = fileaddress + path + oldname;
        File oldfile = new File(oldnamed);
        String newname = fileaddress + path + rename;
        File refile = new File(newname);
        //检查要重命名的文件是否存在，是否是文件
        if (!oldfile.exists() && refile.exists()) {
            return 0;
        } else {
            return oldfile.renameTo(refile) ? 1 : 0;
        }

    }

    // 新创文件夹
    public String newfile(String path, String filename) {

        String address = fileaddress + path;
        int j = 1;
        File file = new File(address);
        String[] fileList = file.list();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].equals(filename)) {
                filename = "Newfile" + j;
                j++;
                i = 0;
            }
        }
        File newfile = new File(address + "/" + filename);
        newfile.mkdirs();
        return filename;
    }


}
