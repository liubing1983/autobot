package com.lb.hadoop.multiple.output;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;


/**
 * Created by liub on 2016/12/9.
 */
public class MultipleOutputReducer extends Reducer<Text, IntWritable, Text, LongWritable> {

        private LongWritable count = new LongWritable();
        private MultipleOutputs outputs;

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            outputs = new MultipleOutputs(context);
        }

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable value : values){
                sum += value.get();
            }
            count.set(sum);

            Configuration conf = context.getConfiguration();
            String type = conf.get("type");
            if(type.equalsIgnoreCase("namedOutput")) {
                if(key.toString().equals("hello")) {
                    outputs.write("hello", key, count);
                }
                else {
                    outputs.write("IT", key, count);
                }
            }
            else if(type.equalsIgnoreCase("baseOutputPath")){
                outputs.write(key, count, key.toString());
            }
            else {
                if(key.toString().equals("hello")) {
                    outputs.write("hello", key, count, key.toString());
                }
                else {
                    outputs.write("IT", key, count, key.toString());
                }
            }
        }

        @Override
        protected void cleanup(Reducer.Context context) throws IOException, InterruptedException {
            outputs.close();
        }
    }
