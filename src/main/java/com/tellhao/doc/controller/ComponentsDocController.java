package com.tellhao.doc.controller;

import com.tellhao.doc.entity.FileTree;
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

/**
 * @author: 韩聪寅
 * @create: 2019-11-27
 **/
@Controller
public class ComponentsDocController {
    @Value("${fileAddress}")
    private String fileAddress;

    @Autowired
    FileOperation fileOperation;
    @Autowired
    TextOperation textOperation;
    @Autowired
    FileLoad fileLoad;


    @RequestMapping("/uploadText")
    @ResponseBody
    public int uploadText(String text, String fileName, String addressName, int id) {

        return id == 0 ? textOperation.saveAsFileWriter(text, fileName, addressName) : textOperation.newFileWriter(text, fileName, addressName);
    }

    @RequestMapping("/getText")
    @ResponseBody
    public String getText(String addressName, String fileName) {
        return textOperation.turnFileTxt(addressName, fileName);
    }

    @RequestMapping("/delFile")
    @ResponseBody
    public int delFile(String delName, int ctrl, String address, int id) {
        return ctrl == 0 ? fileOperation.delFile(delName, address) : fileOperation.trueDelFile(delName, address, id);
    }


    @RequestMapping("/getFileTree")
    @ResponseBody
    public List<FileTree> getFileTree(@Value("${fileAddress}") String path, int id, String fileAddress) {
        return id == 0 ? fileOperation.getFileTree(path, fileOperation.getFileTree(path, fileAddress)) : fileOperation.getFileTree(path, fileOperation.getFileTree(path, 1));

    }


    @RequestMapping("/newFile")
    @ResponseBody
    public String newFile(String path, String fileName) {
        return fileOperation.newFile(path, fileName);
    }


    @RequestMapping("/rename")
    @ResponseBody
    public int rename(String path, String rename, String oldName) {
        return fileOperation.renameFile(path, rename, oldName);
    }

    @RequestMapping("/download")
    @ResponseBody
    public String download(String address, String fileName, HttpServletResponse response, int i) {
        if (i == 1) {
            fileLoad.download(address, fileName, response, i);
        } else {
            fileLoad.download(fileOperation.zip(fileAddress + ".zip", fileAddress), fileName, response, i);
        }
        return "redirect:index.html";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        return fileLoad.fileUpload(file, request);
    }


}
