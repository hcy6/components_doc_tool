package com.tellhao.components_doc_tool.controller;

import com.tellhao.components_doc_tool.entity.Treevo;
import com.tellhao.components_doc_tool.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    public int del(String delname,int i) {

        return i==0? fileOperation.delFile(delname):fileOperation.truedelFile(delname);
    }


    @RequestMapping("/getfiletree")
    @ResponseBody
    public List<Treevo> getfiletree(@Value("${fileaddress}") String dir,int id,String fileaddress) throws IOException {

        return id==0? fileOperation.getfiletree(dir,fileOperation.getfiletree(dir,fileaddress)):fileOperation.getfiletree(dir,".back");
    }



    @RequestMapping("/newfile")
    @ResponseBody
    public String getdelfiletree(String path,String filename) throws IOException {

        return fileOperation.newfile(path,filename);
    }


    @RequestMapping("/renamed")
    @ResponseBody
    public int Renamed(String path, String Rename,String oldname) {
//        String a = Rname.substring(0, Rname.lastIndexOf("\\")) + "\\" + Dname;
//        int i = );
        return fileOperation.renameFile(path,Rename,oldname);
    }

    @RequestMapping("/download")
    public String download(String path, HttpServletResponse response) throws IOException {
       filedownload.download1(path,response);

        return "redirect:sl.html";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {

        return  upload.fileUpload2(file,request);
    }




}

