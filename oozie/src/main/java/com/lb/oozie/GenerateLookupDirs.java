package com.lb.oozie;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *  oozie节点中传递数据
 */
public class GenerateLookupDirs {

    // oozie 默认的properties文件名称
    private static final String OOZIE_ACTION_OUTPUT_PROPERTIES = "oozie.action.output.properties";

    public static void main(String[] args) throws Exception {
        String oozieProp = System.getProperty(OOZIE_ACTION_OUTPUT_PROPERTIES);
        if (oozieProp != null) {
            File propFile = new File(oozieProp);
            Properties props = new Properties();
            // 向文件中写入数据
            props.setProperty("hello", "lb");

            OutputStream os = new FileOutputStream(propFile);
            props.store(os, "");
            os.close();
        } else
            throw new RuntimeException(OOZIE_ACTION_OUTPUT_PROPERTIES + " System property not defined");
    }
}
