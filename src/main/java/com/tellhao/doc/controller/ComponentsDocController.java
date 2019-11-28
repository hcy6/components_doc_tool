package com.tellhao.doc.controller;

import com.tellhao.doc.entity.FileTree;
import com.tellhao.doc.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author: 韩聪寅
 * @create: 2019-11-27
 **/
@RestController
public class ComponentsDocController {
    @Value("${fileAddress}")
    private String fileAddress;

    @Autowired
    FileOperation fileOperation;
    @Autowired
    TextOperation textOperation;
    @Autowired
    FileLoad fileLoad;


    @RequestMapping("/updateText")
    public int updateText(String text, String fileName, String addressName) {
        return  textOperation.saveAsFileWriter(text, fileName, addressName);
    }
    @RequestMapping("/addText")
    public int addText(String text, String fileName, String addressName) {
        return  textOperation.newFileWriter(text, fileName, addressName);
    }
    @RequestMapping("/getText")
    public String getText(String addressName, String fileName) {
        return textOperation.turnFileTxt(addressName, fileName);
    }


    @RequestMapping("/delFile")
    public int delFile(String delName, String address) {
        return  fileOperation.delFile(delName, address) ;
    }
    @RequestMapping("/delFileFromDisk")
    public int delFileFromDisk(String delName, String address, int id) {
        return fileOperation.trueDelFile(delName, address, id);
    }


    @RequestMapping("/getFileTree")
    public List<FileTree> getFileTree(@Value("${fileAddress}") String path,  String fileAddress) {
        return  fileOperation.getFileTree(path, fileOperation.getFileTree(path, fileAddress));
    }
    @RequestMapping("/getDelFileTree")
    public List<FileTree> getDelFileTree(@Value("${fileAddress}") String path) {
        return fileOperation.getFileTree(path, fileOperation.getFileTree(path, 1));

    }



    @RequestMapping("/addFile")
    public String newFile(String path, String fileName) {
        return fileOperation.newFile(path, fileName);
    }


    @RequestMapping("/rename")
    public int rename(String path, String rename, String oldName) {
        return fileOperation.renameFile(path, rename, oldName);
    }

    @RequestMapping("/download")
    public String download(String address, String fileName, HttpServletResponse response, int i) {
        if (i == 1) {
            fileLoad.download(address, fileName, response, i);
        } else {
            fileLoad.download(fileOperation.zip(fileAddress + ".zip", fileAddress), fileName, response, i);
        }
        return "redirect:index.html";
    }
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileLoad.fileUpload(file, request);
    }


}
