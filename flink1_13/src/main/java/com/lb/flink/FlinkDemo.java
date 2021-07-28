package com.lb.flink;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName FlinkDemo
 * @Description @TODO
 * @Author liubing
 * @Date 2021/7/14 16:57
 * @Version 1.0
 **/
public class FlinkDemo {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 创建数据源
        DataStreamSource data = env.socketTextStream("127.0.0.1", 8099);

        // 打印数据
        data.print();

        // 启动程序
        env.execute();

    }

}
