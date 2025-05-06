package com.bc.finance.common.utils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUtils {

    public static void exportByteFile(byte buf[], String filename, String contentType, HttpServletResponse response) throws IOException {
        ByteArrayInputStream in = null;
        ServletOutputStream ouputStream = null;
        try {
            in = new ByteArrayInputStream(buf);

            //设置相应类型application/octet-stream
            response.setContentType(contentType);
            //设置头信息                 Content-Disposition为属性名  附件形式打开下载文件   指定名称  为 设定的filename
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO_8859_1"));
            ouputStream = response.getOutputStream();

            byte b[] = new byte[1024 * 1024 * 5];
            int n;
            //循环读取 !=-1读取完毕
            while ((n = in.read(b)) != -1) {
                //写入到输出流 从0读取到n
                ouputStream.write(b, 0, n);
            }
            //关闭流、释放资源
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
            ouputStream.close();
        }
    }

    public static void exportByteFile(InputStream is, String filename, String contentType, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            exportByteFile(outStream.toByteArray(), filename, contentType, response);
        } finally {
            outStream.close();
            is.close();
        }
    }


        /**
         * 已比特数组形式保存文件
         * @param buf     文件比特数组
         * @param filename  文件名称，仅只能保存英文字符
         */
    public static void exportByteFile(byte buf[], String filename, HttpServletRequest request, HttpServletResponse resp) throws IOException {
        exportByteFile(buf, filename, "application/x-msdownload", resp);
    }

    public static void exportByteFile(InputStream is, String fileNames, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream outStream = null;
        try {
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            exportByteFile(outStream.toByteArray(), fileNames, request, response);
        } finally {
            outStream.close();
            is.close();
        }
    }
}
