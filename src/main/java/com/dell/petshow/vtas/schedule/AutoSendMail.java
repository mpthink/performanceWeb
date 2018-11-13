/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.vtas.schedule;

import com.dell.petshow.common.util.DateTimeUtil;
import com.dell.petshow.common.util.SendMailUtil;
import com.dell.petshow.vtas.service.IJobRuntimeService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author map6
 */
@Component
public class AutoSendMail {

    public static final Logger LOG = LoggerFactory.getLogger(AutoSendMail.class);

    @Autowired
    private IJobRuntimeService jobRuntimeService;

    private final String emailVelociyTemplate = "template/sendMailForArrayStatus.java.vm";

    private final String phantomjsBinPath_WIN ="static/plugins/phantomJS-win/bin/phantomjs.exe";
    private final String phantomjsBinPath_Linux ="static/plugins/phantomJS-linux64/bin/phantomjs";
    private final String generateFileJSPath = "static/my/vtas/crawlEchartForArrayStatus.js";
    private final String urlHome = "http://10.207.120.10:8888/";  //  http://10.207.120.10:8888/   http://localhost:8080/
    private final String arrayStatusURL = urlHome + "vtas/jobRuntime/jobStatus";

    private final String mailFrom = "Vitality@dell.com";
    private final String mailCC = "Ming.Yue@emc.com";

    public void sendMailForArrayStatus(){

        Map<String, Object> result = jobRuntimeService.exeutionStatusForMail();
        Properties properties = new Properties();
        properties.put("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.put("input.encoding", "UTF-8");
        properties.put("output.encoding", "UTF-8");
        VelocityEngine engine = new VelocityEngine(properties);
        Template template = engine.getTemplate(emailVelociyTemplate);
        VelocityContext context = new VelocityContext();
        Writer writer = new StringWriter();

        try{
            //generate file
            String webHomePath = getPath() + "/WEB-INF/";

            String outputPath = webHomePath + "output/";
            String jsPath = webHomePath + generateFileJSPath;
            String nowValue = String.valueOf(System.currentTimeMillis());
            String imageName = nowValue + ".png";
            String htmlName = nowValue + ".html";
            String htmlPath = outputPath + htmlName;
            String imagePath = outputPath + imageName; //图片路径
            String imageAccessUrl = "";
            if(crawlImageForArrayStatus(webHomePath, arrayStatusURL, jsPath, imagePath));{
                imageAccessUrl = urlHome + "output/" + imageName;
            }

            context.put("fields",result.get("arrayListData"));
            context.put("imageUrl", imageAccessUrl);
            template.merge(context,writer);
            writer.flush();
            //写入文件
            String fileContent = writer.toString();
            generateHtmlFile(htmlPath, fileContent);
            //send mail
            String mailTo = getToMailList((List<Map<String, Object>>) result.get("arrayListData"));
            //for testing
            //String mailTo = "paul.p.ma@emc.com";
            //String mailCC = "paul.p.ma@emc.com";
            String mailTitle = "Live Array Status " + DateTimeUtil.getCurrentChinaDateWithTimezone();
            SendMailUtil.sendMail(mailFrom, mailTo, mailCC, mailTitle, fileContent);

            writer.close();
        } catch (IOException e) {
            LOG.error("Auto send mail failed, because merge issue!");
            LOG.error(e.getMessage());
        }
    }

    /**
     * crwal image and save as a file and return file path
     * @return  file path
     */
    private Boolean crawlImageForArrayStatus(String homePath, String url, String jsPath, String imagePath) {
        String os = System.getProperty("os.name").toLowerCase();
        String binPath;
        if(os.contains("win")){
            binPath = homePath + phantomjsBinPath_WIN;
        }else{
            binPath = homePath + phantomjsBinPath_Linux;
        }
        String command = binPath + " " + jsPath + " " + url + " " + imagePath;
        try{
            Process process = Runtime.getRuntime().exec(command);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String tmp = "";
            while ((tmp = reader.readLine()) != null) {
            }
            if (reader != null) {
                reader.close();
            }
            if (process != null) {
                process.destroy();
                process = null;
            }
            LOG.info("Generate image file successfully!");
        }catch (Exception e){
            LOG.error("Generate file failed!");
            e.printStackTrace();
            return false;
        }

        return true;
    }


    private String getPath() throws UnsupportedEncodingException {
        String path = AutoSendMail.class.getResource("AutoSendMail.class")
                .toString();
        path = URLDecoder.decode(path,"UTF-8");
        path.replaceAll("\\\\", "/");
        int index = path.indexOf("WEB-INF");
        if (index == -1) {
            index = path.indexOf("bin");
        }
        if (index == -1) {
            index = path.indexOf("lib");
        }
        if (index == -1) {
            int index2 = path.indexOf("jar!");
            if (index2 != -1) {
                path = path.substring(0, index2);
                System.out.println(path);
                System.out.println(File.separator);
                index = path.lastIndexOf("/");
                System.out.println(index);
            }
        }
        path = path.substring(0, index);
        if (path.startsWith("jar")) {
            path = path.substring(9);
        }
        if (path.startsWith("file")) {
            path = path.substring(5);
        }
        if (path.endsWith("/") || path.endsWith("\\")) {
            path = path.substring(0, path.length() - 1);
        }
        // linux环境下第一个/是需要的
        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            path = path.substring(1);
        }
        return path;
    }


    private void generateHtmlFile(String filePath, String content) {
        BufferedWriter output = null;
        try {
            File file = new File(filePath);
            output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            LOG.error("Generate html file failed!");
            e.printStackTrace();
        }
    }

    private String getToMailList(List<Map<String, Object>> arrayDatas){

        Set<String> mails = new HashSet<>();
        arrayDatas.stream().forEach(arrayData->{
            mails.add((arrayData.get("arrayOwnerEmail").toString().trim()));
        });

        StringBuffer buffer = new StringBuffer();
        mails.stream().forEach(mail->{
            buffer.append(mail).append(",");
        });
        return buffer.toString().substring(0,buffer.length()-1);
    }

}
