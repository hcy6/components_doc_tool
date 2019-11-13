package com.tellhao.components_doc_tool.controller;

import com.tellhao.components_doc_tool.entity.Treevo;
import com.tellhao.components_doc_tool.util.FileOperation;
import com.tellhao.components_doc_tool.util.GetAll;
import com.tellhao.components_doc_tool.util.TextOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

public class testcontroller {
    @Autowired
    FileOperation fileOperation;
    @Autowired
    TextOperation textOperation;
    @Autowired
    GetAll getAll;

    @RequestMapping("/uploadtext")
    @ResponseBody
    public String uploadtext(String text, String filename, String addressname) {
        textOperation.saveAsFileWriter(text, filename,addressname);
        return "redirect:sl.html";
    }

//    @RequestMapping("/gettext")
//    @ResponseBody
//    public String gettext(String addressname) {
//        return textOperation.turnFileTxt(addressname);
//    }

    @RequestMapping("/getwen")
    @ResponseBody
    public List getwen() {
        return fileOperation.getTrailFileTs("G:/JAVA/components-doc/docs/guide");
    }

    @RequestMapping("/getmun")
    @ResponseBody
    public String getmun() {
        return textOperation.munFileTxt();
    }

    @RequestMapping("/delfile")
    @ResponseBody
    public int del(String delname) {
        int i = fileOperation.delFile(delname);
        return i;
    }

    @RequestMapping("/getall")
    @ResponseBody
    public List Getall() throws IOException {
        List list = getAll.find("G:/JAVA/components-doc/docs");
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

//    @RequestMapping("/getfiletree")
//    @ResponseBody
//    public List<Treevo> getfiletree(@Value("${fileaddress}") String dir) throws IOException {
//        List<Treevo> list = fileOperation.getfiletree(dir);
//        return list;
//    }
//    @RequestMapping("/getdelfiletree")
//    @ResponseBody
//    public List<Treevo> getdelfiletree(@Value("${fileaddress}") String dir) throws IOException {
//        List<Treevo> list = fileOperation.getfiletree(dir,".back");
//        return list;
//    }

    @RequestMapping("/renamed")
    @ResponseBody
    public int Renamed(String Dname, String Rname) {
        String a = Rname.substring(0, Rname.lastIndexOf("\\")) + "\\" + Dname;
        int i = fileOperation.renameFile(Rname, a);
        return i;
    }

}
