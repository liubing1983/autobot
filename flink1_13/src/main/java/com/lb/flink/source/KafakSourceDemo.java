package com.lb.flink.source;


import org.apache.flink.api.common.serialization.SimpleStringSchema;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName KafakSourceDemo
 * @Description @TODO
 * @Author liubing
 * @Date 2021/7/20 15:07
 * @Version 1.0
 **/
public class KafakSourceDemo {

    public static void main(String[] args) throws Exception {


        // 初始化上下文
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //  开启checkpoint指定时间间隔
        env.enableCheckpointing(TimeUnit.SECONDS.toMillis(1));
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);
        env.setParallelism(5);


        Properties prop = new Properties();
        prop.put("bootstrap.servers", "localhost:9092");
        prop.put("zookeeper.connect", "localhost:2181");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("group.id", "lb");
        prop.put("max.partition.fetch.bytes", "4194304");


        //构建FlinkKafkaConsumer
        FlinkKafkaConsumer<String> myConsumer = new FlinkKafkaConsumer<>("k1", new SimpleStringSchema(), prop);
        //指定偏移量

        // Flink从topic中最初的数据开始消费
        // consumer.setStartFromEarliest();


        // Flink从topic中指定的时间点开始消费，指定时间点之前的数据忽略
        // consumer.setStartFromTimestamp(1559801580000l);

        // Flink从topic中指定的offset开始，这个比较复杂，需要手动指定offset
        // consumer.setStartFromSpecificOffsets(offsets);

        // Flink从topic中最新的数据开始消费
        // consumer.setStartFromLatest();

        // Flink从topic中指定的group上次消费的位置开始消费，所以必须配置group.id参数
        // consumer.setStartFromGroupOffsets();

        myConsumer.setStartFromGroupOffsets();


        env.addSource(myConsumer).print();

        env.execute();

    }

}



