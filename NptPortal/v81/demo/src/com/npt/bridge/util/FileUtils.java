package com.npt.bridge.util;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 项目: NPTRPT
 * 作者: 张磊
 * 日期: 17/1/6 下午4:45
 * 备注: 文件下载辅助类
 */
public class FileUtils {
    /**
     * 作者: 张磊
     * 日期: 17/1/6 下午4:52
     * 备注:
     *
     * @param response
     * @param filePath 文件全路径
     * @param fileName 返回的文件名
     */
    public static void download(HttpServletResponse response, String filePath, String fileName) {
        try {
            // 定义输出流
            OutputStream out;

            response.reset();  //重置结果集
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));  //返回头 文件名
            // 自动判断下载文件类型
            response.setContentType("multipart/form-data");

            out = response.getOutputStream();

            InputStream in = new FileInputStream(filePath);

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
