package com.npt.common.service.impl;

import com.jeecms.common.web.springmvc.RealPathResolver;
import com.npt.common.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/8 上午10:45
 * 备注:
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private RealPathResolver realPathResolver;

    @Override
    public String saveFile(HttpServletRequest request, String path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        File dest = new File(realPathResolver.get(path), fileName);
        file.transferTo(dest);
        return fileName;
    }

    @Override
    public String saveFile(HttpServletRequest request, String path, MultipartFile file,String fileName) throws IOException {
        File dest = new File(realPathResolver.get(path), fileName);
        file.transferTo(dest);
        return fileName;
    }

    @Override
    public void downloadFile(HttpServletResponse response, String filePath, String fileName) {
        try {
            // 定义输出流
            OutputStream out;
            response.reset();  //重置结果集
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  //返回头 文件名
            // 自动判断下载文件类型
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            InputStream in = new FileInputStream(realPathResolver.get(filePath));
            //写文件
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
