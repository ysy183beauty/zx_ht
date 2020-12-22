package com.npt.common.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 项目: zxcms
 * 作者: 张磊
 * 日期: 17/3/8 上午10:43
 * 备注: 文件上传
 */
public interface FileUploadService {
    String saveFile(HttpServletRequest request, String path, MultipartFile file) throws IOException;
    String saveFile(HttpServletRequest request, String path, MultipartFile file,String fileName) throws IOException;
    void downloadFile(HttpServletResponse response, String attach, String substring);
}
