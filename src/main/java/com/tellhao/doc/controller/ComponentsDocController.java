package com.tellhao.doc.controller;

import com.tellhao.doc.entity.TreeMember;
import com.tellhao.doc.util.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class ComponentsDocController {
    @Value("${fileaddress}")
    private String fileaddress;
    @Value("${zipFileName}")
    private String zipFilenName;

    @Autowired
    FileOperation fileOperation;
    @Autowired
    TextOperation textOperation;
    @Autowired
    FileLoad fileload;


    @RequestMapping("/uploadtext")
    @ResponseBody
    public int uploadtext(String text, String filename, String addressname, int id) {

        return id == 0 ? textOperation.saveAsFileWriter(text, filename, addressname) : textOperation.newFileWriter(text, filename, addressname);
    }

    @RequestMapping("/gettext")
    @ResponseBody
    public String gettext(String addressname, String filename) {
        return textOperation.turnFileTxt(addressname, filename);
    }

    @RequestMapping("/delfile")
    @ResponseBody
    public int del(String delname, int ctrli, String address, int id) {
        return ctrli == 0 ? fileOperation.delFile(delname, address) : fileOperation.truedelFile(delname, address, id);
    }


    @RequestMapping("/getfiletree")
    @ResponseBody
    public List<TreeMember> getfiletree(@Value("${fileaddress}") String path, int id, String fileaddress) {
        return id == 0 ? fileOperation.getfiletree(path, fileOperation.getfiletree(path, fileaddress)) : fileOperation.getfiletree(path, fileOperation.getdelfiletree(path, 1));

    }


    @RequestMapping("/newfile")
    @ResponseBody
    public String getdelfiletree(String path, String filename) {
        return fileOperation.newfile(path, filename);
    }


    @RequestMapping("/renamed")
    @ResponseBody
    public int Renamed(String path, String Rename, String oldname) {
        return fileOperation.renameFile(path, Rename, oldname);
    }

    @RequestMapping("/download")
    @ResponseBody
    public String download(String address, String filename, HttpServletResponse response, int i) {
        if (i == 1) {
            fileload.download(address, filename, response, i);
        } else {
            fileload.download(fileOperation.zip(zipFilenName, fileaddress), filename, response, i);
        }
        return "redirect:index.html";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileload.fileUpload(file, request);
    }


//    @RequestMapping("/test")
//    @ResponseBody
//    public String test(String addressname, String filename){
//        return textOperation.readFileByByte(addressname,filename);
//    }

}
