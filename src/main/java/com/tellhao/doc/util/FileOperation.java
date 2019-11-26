package com.tellhao.doc.util;

import com.tellhao.doc.entity.TreeMember;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class FileOperation {
    @Value("${fileaddress}")
    private String fileaddress;


    //取出目录下的所有文件-------------------------------------------------------------
    public List<TreeMember> getfiletree(String path, List list) {

        List<TreeMember> tree = new ArrayList<TreeMember>();
        File file = new File(path.trim());
        TreeMember treemember = new TreeMember();
        treemember.setId("/");
        treemember.setText("/");
        treemember.setChildren(list);
        tree.add(treemember);
        return tree;
    }

    public List<TreeMember> getfiletree(String path, String extension) {

        FileOperation get = new FileOperation();
        List<TreeMember> tree = new ArrayList<TreeMember>();
        File trailDir = new File(path.trim());
        List<TreeMember> tree1 = new ArrayList<TreeMember>();
        for (File file : trailDir.listFiles()) {
            TreeMember treemember = new TreeMember();
            if (file.isFile()) {
                if (extensionfile(file.getName(), extension)) {
                    treemember.setId(file.getName());
                    treemember.setText(file.getName());
                    treemember.setPid(file.getName());
//                    file.getAbsolutePath()
                    tree.add(treemember);
                }
            }
            if (file.isDirectory() && !file.getName().endsWith(".back")) {
                try {
                    tree1 = get.getfiletree(file.getCanonicalPath(), extension);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                if(tree1.size()>0){}
                treemember.setId(file.getName());
                treemember.setText(file.getName());
                treemember.setPid(file.getName());
                treemember.setChildren(tree1);
                treemember.setUid(1);
                tree.add(0, treemember);

            }
        }
        return tree;

    }

    public List<TreeMember> getdelfiletree(String path, int id) {

        FileOperation get = new FileOperation();
        List<TreeMember> tree = new ArrayList<TreeMember>();
        File trailDir = new File(path.trim());
        List<TreeMember> tree1 = new ArrayList<TreeMember>();
        for (File file : trailDir.listFiles()) {
            TreeMember treemember = new TreeMember();
            if (file.isFile()) {
                if (extensionfile(file.getName(), id)) {
                    treemember.setId(file.getName());
                    treemember.setText(file.getName());
                    treemember.setPid(file.getName());
//                    file.getAbsolutePath()
                    tree.add(treemember);
                }
            } else if (file.isDirectory() && file.getName().endsWith(".back")) {
                try {
                    tree1 = get.getdelfiletree(file.getCanonicalPath(), 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                if (tree1.size() > 0) {}
                treemember.setId(file.getName());
                treemember.setText(file.getName());
                treemember.setPid(file.getName());
                treemember.setChildren(tree1);
                treemember.setUid(1);
                tree.add(0, treemember);

            } else {
                try {
                    tree1 = get.getdelfiletree(file.getCanonicalPath(), id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (tree1.size() > 0) {
                    treemember.setId(file.getName());
                    treemember.setText(file.getName());
                    treemember.setPid(file.getName());
                    treemember.setChildren(tree1);
                    treemember.setUid(1);
                    tree.add(0, treemember);
                }
            }
        }
        return tree;

    }


    public Boolean extensionfile(String file, int id) {
        if (id == 1) {
            return file.endsWith(".back") ? true : false;
        } else {
            return true;
        }
    }

    public Boolean extensionfile(String file, String extension) {
        if (!"".equals(extension)) {
            if (file.lastIndexOf(".") > 0) {
                String Suffix = file.substring(file.lastIndexOf("."));
                int flag = 0;
                String[] strAry = extension.split(",");
                for (int i = 0; i < strAry.length; i++) {
                    if (Suffix.equals(strAry[i])) {
                        flag = 1;
                        break;
                    }
                }
                return flag == 1 ? true : false;
            } else {
                return false;
            }
        } else {
            return !file.endsWith(".back") ? true : false;
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


    public int truedelFile(String delname, String address, int id) {
        //老师我想问这个方法，要不要分出两个子函数出来，然后这个函数判段用哪个，然后各自递归调用
        try {
            File file = new File(fileaddress + address + delname);
            String delfilename = "";
//            id=0,删除.back文件
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

            } else {  //id！=0 删除文件夹下得所有文件（包括本身）
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

    //文件的压缩

    public String zip(String zipFileName, String sourceFileName) {
        System.out.println("压缩中...");
        ZipOutputStream out = null;
        try {
            //创建zip输出流
            out = new ZipOutputStream(new FileOutputStream(zipFileName));
            File sourceFile = new File(sourceFileName);
            out.setLevel(0);
            //调用函数
            compression(out, sourceFile, sourceFile.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("压缩完成");
        return zipFileName;
    }

    public void compression(ZipOutputStream out, File sourceFile, String base) {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] filelist = sourceFile.listFiles();
            if (filelist.length == 0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                try {
                    out.putNextEntry(new ZipEntry(base + "/"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < filelist.length; i++) {
                    compression(out, filelist[i], base + "/" + filelist[i].getName());
                }
            }
        } else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            BufferedInputStream bis = null;
            FileInputStream fos = null;
            try {
                out.putNextEntry(new ZipEntry(base));
                fos = new FileInputStream(sourceFile);
                bis = new BufferedInputStream(fos);
                int tag;
                //将源文件写入到zip文件中
                while ((tag = bis.read()) != -1) {
                    out.write(tag);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bis.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }

}
