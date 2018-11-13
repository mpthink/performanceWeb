/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common.phantomJsTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * refer to:
 * http://www.cnblogs.com/han108/p/9216583.html   --very useful
 * https://blog.csdn.net/u010142437/article/details/79101206
 * https://gitee.com/saintlee/echartsconvert
 *
 * @author map6
 */
public class PhantomJsVerify {


    private static String phantomjsHome = "C:\\project\\tempProject\\phantomjs-2.1.1-windows\\";
    private static String binPath = phantomjsHome + "bin\\phantomjs.exe";
    private static String jsPath = "C:\\project\\javawebProject\\githubProject\\performanceWeb\\src\\test\\java\\com\\dell\\petshow\\common\\phantomJsTest\\testJS.js";
    private static String tempPath = "C:\\project\\javawebProject\\githubProject\\performanceWeb\\src\\test\\java\\com\\dell\\petshow\\common\\phantomJsTest";// 图片保存目录
    private static String BLANK = " ";

    // 执行cmd命令
    public static String cmd(String imgagePath, String url) {
        return binPath + BLANK + jsPath + BLANK + url + BLANK + imgagePath;
    }

    //关闭命令
    public static void close(Process process, BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
    /**
     * @param userId
     * @param url
     * @throws IOException
     */
    public static void printUrlScreen2jpg(String url) throws Exception{
        String imgagePath = tempPath+"/"+System.currentTimeMillis()+".png";//图片路径
        //Java中使用Runtime和Process类运行外部程序
        Process process = Runtime.getRuntime().exec(cmd(imgagePath,url));
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String tmp = "";

        while ((tmp = reader.readLine()) != null) {
            //System.out.println(tmp);

        }
        close(process,reader);
        System.out.println("success");
    }

    public static void main(String[] args) throws Exception {
        //String url = "http://echarts.baidu.com/echarts2/doc/example/line1.html";//以百度网站首页为例
        String url = "http://localhost:8080/vtas/jobRuntime/jobStatus";
        printUrlScreen2jpg(url);
    }
}
