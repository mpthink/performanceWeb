/******************************************************************************
 *                       COPYRIGHT 2002 - 2012 BY DELL INC.
 *                          ALL RIGHTS RESERVED
 *
 * THIS DOCUMENT OR ANY PART OF THIS DOCUMENT MAY NOT BE REPRODUCED WITHOUT
 * WRITTEN PERMISSION FROM DELL INC.
 *****************************************************************************/

package com.dell.petshow.common.velocityTest;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

/**
 * @author map6
 */
public class VelocityTest {

    @Test
    public void test() throws IOException {
        String vmPath = "C:\\project\\javawebProject\\githubProject\\performanceWeb\\src\\test\\java\\com\\dell\\petshow\\common\\velocityTest\\";
        Properties p = new Properties();
        //指定所有vm文件所在的文件夹路径
        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vmPath);
        p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        VelocityEngine engine = new VelocityEngine(p);

        String tempName = "test.java.vm";
        Template template = engine.getTemplate(tempName);

        VelocityContext context = new VelocityContext();

        String jobs = "No Jobs";

        context.put("jobs", jobs);

        Writer writer = new StringWriter();
        template.merge(context, writer);
        writer.flush();
        System.out.println(writer.toString());

    }
}
