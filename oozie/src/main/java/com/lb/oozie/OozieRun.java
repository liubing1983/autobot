package com.lb.oozie;

/**
 * Created by liub on 2016/11/30.
 */

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.OozieClientException;
import org.apache.oozie.client.WorkflowJob;
import org.apache.oozie.client.WorkflowJob.Status;

import java.util.Properties;

/**
 * oozie java api  demo
 */
public class OozieRun {

    private static String OOZIE_URL = "http://10.95.3.136:11000/oozie/";
    private static String JOB_PATH = "hdfs://10.95.3.138:8020/user/tescomm/oozie/ad/ad_user/";
    private static String JOB_Tracker = "cloud136:8032";
    private static String NAMENode = "hdfs://10.95.3.138:8020";

    // 是否使用系统类库
    private static String system_libpath = "ture";
    // 用户自定义类库位置
    private static String user_libpath = NAMENode + "/user/tescomm/oozie/ad/libserver";


    OozieClient wc = null;

    public OozieRun(String url) {
        wc = new OozieClient(url);
    }

    /**
     * 启动oozie， 并返回job id
     *
     * @return
     * @throws OozieClientException
     */
    public String startJob() throws OozieClientException {
        // 配置config文件， 向oozie传递参数
        Properties conf = wc.createConfiguration();
        conf.setProperty(OozieClient.APP_PATH, JOB_PATH);
        conf.setProperty("jobTracker", JOB_Tracker);
        conf.setProperty("nameNode", NAMENode);
        conf.setProperty(OozieClient.USE_SYSTEM_LIBPATH, system_libpath);
        conf.setProperty(OozieClient.LIBPATH, user_libpath);
        return wc.run(conf);
    }

    /**
     * 根据oozie id得到job状态
     *
     * @param jobID
     * @return
     * @throws OozieClientException
     */
    public Status getJobStatus(String jobID) throws OozieClientException {
        WorkflowJob job = wc.getJobInfo(jobID);
        return job.getStatus();
    }

    public static void main(String[] args) throws OozieClientException, InterruptedException {
        // Create client
        OozieRun client = new OozieRun(OOZIE_URL);

        // Start Oozing
        String jobId = client.startJob();
        Status status = client.getJobStatus(jobId);
        if (status == Status.RUNNING)
            System.out.println("Workflow job running");
        else
            System.out.println("Problem starting Workflow job");
    }
}