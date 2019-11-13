package com.tellhao.components_doc_tool.controller;

import com.tellhao.components_doc_tool.entity.Treevo;
import com.tellhao.components_doc_tool.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class Components_doccontroller {

    @Autowired
    FileOperation fileOperation;
    @Autowired
    TextOperation textOperation;
    @Autowired
    GetAll getAll;
    @Autowired
    filedownload filedownload;
    @Autowired
    upload upload;


    @RequestMapping("/uploadtext")
    @ResponseBody
    public int uploadtext(String text, String filename, String addressname) {

        return textOperation.saveAsFileWriter(text, filename,addressname);
    }

    @RequestMapping("/gettext")
    @ResponseBody
    public String gettext(String addressname,String filename) {
        return textOperation.turnFileTxt(addressname,filename);
    }

    @RequestMapping("/delfile")
    @ResponseBody
    public int del(String delname) {
        int i = fileOperation.delFile(delname);
        return i;
    }


    @RequestMapping("/getfiletree")
    @ResponseBody
    public List<Treevo> getfiletree(@Value("${fileaddress}") String dir,int id,String fileaddress) throws IOException {

        return id==0? fileOperation.getfiletree(dir,fileaddress):fileOperation.getfiletree(dir,".back");
    }



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

    @RequestMapping("/download")
    public String download(String path, HttpServletResponse response) throws IOException {
       filedownload.download1(path,response);

        return "redirect:sl.html";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") CommonsMultipartFile file) throws IOException {

        return  upload.fileUpload2(file);
    }




}

