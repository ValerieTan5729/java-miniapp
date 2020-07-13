package com.github.valerie.wx.miniapp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class FileUtils {
    // public static final String UPLOAD_DIR = "D://upload//";
    public static final String UPLOAD_DIR = "/clock/upload/";

    // 上传文件
    public static Map<String, Object> upload(MultipartFile file) {
        String filePath = "";
        File dest = null;
        try {
            String pathDir = createDir(getUploadDir());
            System.out.println("pathDir = " + pathDir);
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String storeName = rename(fileName);
                String pathName = getUploadDir() + pathDir + storeName;
                dest = new File(pathName);
                System.out.println("pathName : " + pathName);
                file.transferTo(dest);
                filePath = pathDir + storeName;
            } else {
                log.info("上传的文件为空");
            }
        } catch (Exception e) {
            System.out.println("catch Exception : " + e.getClass() + e);
            // throw e;
            // logger.error(e.getClass(), e);
        }
        filePath = filePath.replaceAll("\\\\", "/");
        Map<String, Object> param = new HashMap<>();
        param.put("file", dest);
        param.put("path", filePath);
        return param;
    }

    // 下载本地文件
    // 若找不到本地文件，不知道如何解决异常问题
    public static void downloadFile(HttpServletResponse response, String filePath, String fileName) throws IOException {
        File file = new File(getUploadDir() + filePath);
        System.out.println("file " + fileName + " exists : " + file.exists() + ", filePath is " + filePath);
        if (!file.exists()) {
            System.out.println("下载资源已被删除");
            /*
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("<script>parent.layer.msg('您要下载的资源不存在或者已被删除！', {icon:5});</script>");
            */
        } else {
            // response.setContentType("application/force-download");
            response.setContentType("application/octet-stream");
            // fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));
            FileInputStream input = new FileInputStream(file);
            ServletOutputStream output = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
            input.close();
            output.close();
        }
    }

    public static String getUploadDir() {
        return UPLOAD_DIR;
    }

    public static String createDir(String root) {
        String sp = File.separator;
        SimpleDateFormat firstFormat = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat secondFormat = new SimpleDateFormat("yyyyMMdd");
        String first = firstFormat.format(new Date());
        String second = secondFormat.format(new Date());
        File dir = null;
        makeDirs(dir, root + sp + first);
        makeDirs(dir, root + sp + first + sp + second);
        return first + sp + second + sp;
    }

    public static void makeDirs(File file, String path) {
        file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static String rename(String name) {
        String fileName = name;
        if (name.indexOf(".") != -1) {
            fileName = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
        } else {
            fileName = UUID.randomUUID().toString();
        }
        return fileName;
    }
}
