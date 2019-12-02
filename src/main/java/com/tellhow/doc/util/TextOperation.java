package com.tellhow.doc.util;





import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author: 韩聪寅
 * @create: 2019-11-27
 **/
@Service
public class TextOperation {
    @Value("${fileAddress}")
    private String fileAddress;

    private Logger logger=Logger.getLogger(TextOperation.class);

    public int fileWriter(String filePath, String content) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath);
            fileWriter.write(content);
        } catch (IOException ex) {
            logger.error("异常问题："+ex.getMessage());
            return 0;
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                logger.error("异常问题："+ex.getMessage());
            }
        }
        return 1;
    }

    public int saveAsFileWriter(String content, String fileName, String addressName) {
        String filePath = fileAddress + addressName + fileName;
        File file = new File(filePath);
        return !file.isDirectory() ? file.exists() ? fileWriter(filePath, content) : 0 : 0;
    }

    public int newFileWriter(String content, String fileName, String addressName) {
        String filePath = (fileName.lastIndexOf(".") < 0) ? fileAddress + addressName + "/" + fileName + ".md" : fileAddress + addressName + "/" + fileName;
        File file = new File(filePath);
        return !file.exists() ? fileWriter(filePath, content) : 0;

    }

    /**
     * 文本转字符串
     */
    public String turnFileTxt(String path, String fileName) {
        StringBuffer content = new StringBuffer();
        InputStreamReader reader = null;
        BufferedReader outReader = null;
        try {
            String pathname = fileAddress + path + fileName;
            File file = new File(pathname);
            reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            outReader = new BufferedReader(reader);
            String line = "";
            while (line != null) {
                line = outReader.readLine();
                if (line == null) {
                    content.append(" ");
                } else {
                    content.append("\r\n").append(line);
                }
            }
        } catch (Exception ex) {
            logger.error("异常问题："+ex.getMessage());
        } finally {
            try {
                outReader.close();
                reader.close();
            } catch (Exception e) {
                logger.error("异常问题："+e.getMessage());
            }

        }
        return content.toString();
    }

}


