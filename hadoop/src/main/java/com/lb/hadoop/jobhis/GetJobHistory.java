package com.lb.hadoop.jobhis;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.mapreduce.v2.api.records.JobId;
import org.apache.hadoop.mapreduce.v2.app.job.Job;
import org.apache.hadoop.mapreduce.v2.hs.JobHistory;

import java.io.IOException;
import java.util.Map;


/**
 * @ClassName GetJobHistory
 * @Description @TODO
 * @Author liubing
 * @Date 2019/11/26 11:05
 * @Version 1.0
 **/
public class GetJobHistory {

    public static void main(String[] args) {

        JobClient jobClient = null;
        JobStatus[] jobs;

        //Configuration conf = new Configuration();
        //conf.addResource(new Path("/hadoop/etc/hadoop/mapred-site.xml"));

        try {
            jobClient = new JobClient(new JobConf());
            Configuration conf = new Configuration();
            JobHistory his = new JobHistory();
            his.init(conf);
            Map<JobId, Job> jobHis = his.getAllJobs();
            jobs = jobClient.getAllJobs();



            for (Map.Entry<JobId, Job> entry : jobHis.entrySet()) {
            }

        } catch (IOException e) {

        }


    }


}


