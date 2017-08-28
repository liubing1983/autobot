package com.lb.hadoop.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by lb on 2014/11/3.
 */
public class WordCountMapper extends Mapper<Object, Text, Text, IntWritable> {

private final static IntWritable one = new IntWritable(1);
private Text word = new Text();
Pattern p = Pattern.compile("\\t");

public void map(Object key, Text value, Context context)throws IOException,InterruptedException {
        String s[]  = p.split(value.toString(), -1);
        for(String ss : s) {
            word.set(ss);
            context.write(word, one);
        }
    }
}