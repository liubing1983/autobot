package com.lb.hadoop.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by lb on 2014/11/3.
 * 统计一个文档的单词个数
 */
public class WordCountDriver  extends Configured implements Tool {

    public int run(String[] args) throws Exception{
        // 判断入参个数
        if (args.length != 2) {
            System.out.printf( "参数个数不对. Usage: %s [generic options] <input dir> <output dir>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.out);
            return -1;
        }

        // 删除output目录
        FileSystem fs = FileSystem.get(getConf());
        fs.delete(new Path(args[1]), true);

        // 设置文件大小
         Configuration conf  = new Configuration();
        conf.set("mapreduce.input.fileinputformat.split.maxsize", "102400000");


        Job job = new Job(conf);
        job.setJarByClass(WordCountDriver.class);
        job.setJobName("lb worldcount");

        job.setInputFormatClass(CombineTextInputFormat.class);

        // 设置文件输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
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
        System.out.println(ToolRunner.run(new WordCountDriver(), args));
    }
}
