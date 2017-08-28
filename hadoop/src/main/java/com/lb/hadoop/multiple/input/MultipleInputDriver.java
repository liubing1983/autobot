package com.lb.hadoop.multiple.input;

import com.lb.hadoop.wordcount.WordCountMapper;
import com.lb.hadoop.wordcount.WordCountReducer;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by liub on 2016/12/9.
 */
public class MultipleInputDriver extends Configured implements Tool {

    public int run(String[] args) throws Exception{
        // 判断入参个数
        if (args.length != 3) {
            System.out.printf( "参数个数不对. Usage: %s [generic options] <input dir1> <<input dir2> <output dir>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.out);
            return -1;
        }

        // 删除output目录
        FileSystem fs = FileSystem.get(getConf());
        fs.delete(new Path(args[1]), true);

        Job job = new Job(getConf());
        job.setJarByClass(MultipleInputDriver.class);
        job.setJobName("lb MultipleInput ");

        // 多目录方式1 单独制定map程序
        //MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, Map1.class);
        //MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, Map2.class);

        // 多目录方式2 , 使用同一个map处理
        MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class);
        MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class);
        // 设置结果文件输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 设置map类
        job.setMapperClass(WordCountMapper.class);
        // 手动设置reduce个数
        job.setNumReduceTasks(2);
        // 设置reduce类
        job.setReducerClass(WordCountReducer.class);

        // job.setSortComparatorClass(LongWritableDecreasingComparator.class);

        // map输出key/value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(ToolRunner.run(new MultipleInputDriver(), args));
    }
}
