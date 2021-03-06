package com.lb.hadoop.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by lb on 2014/11/3.
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>  {
    private IntWritable v = new IntWritable();
    private int sum = 0;
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
            InterruptedException {
        sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        v.set(sum);
        context.write(key, v);
    }
}
