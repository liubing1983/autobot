package com.lb.kafka.demo;


import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * kafka 2.0
 */
public class ProducerDemo_2_0 {

    static Logger log =  LoggerFactory.getLogger(ProducerDemo_2_0.class);

    public static void main(String[] args) throws  Exception {

        Properties props = new Properties();

        // 必须指定
        props.put ("bootstrap.servers", "localhost:9092 ");
        props.put ("key.serializer",   "org.apache.kafka.common.serialization.StringSerializer");
        props.put ("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // 可选配置
        props.put ("acks", "1") ;
        props.put ("retries", 3) ;
        props.put ("batch.size", 323840) ;
        props.put ("linger.ms", 10) ;
        props.put ("buffer.memory", 33554432);
        props.put ("max.block.ms", 3000);

        Producer<String, String> producer = new KafkaProducer<>(props);
        for(int i = 0; i < 100; i++){

            // 异步发送
            // 不获取发送结果
            producer.send(new ProducerRecord<>("my-topic1", Integer.toString(i+1), Integer.toString(i)));

            // 使用Callback 获取发送结果,  Callback可以自定义
            producer.send(new ProducerRecord<>("my-topic2", Integer.toString(i+2), Integer.toString(i)), new Callback(){
                // RecordMetadata 和 Exception 在成功或失败的时候会有一个为null
                @Override
                public void  onCompletion(RecordMetadata metadata, Exception ex){
                    if(ex == null){  // 消息发送成功
                        log.info(metadata.topic());

                    }else{  // 消息发送失败
                        log.error(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
            });

            // 同步发送
            // 不限制发送时间
            producer.send(new ProducerRecord<>("my-topic3", Integer.toString(i+3), Integer.toString(i))).get();

            // 限制发送时间
            producer.send(new ProducerRecord<>("my-topic4", Integer.toString(i+4), Integer.toString(i))).get(5, TimeUnit.SECONDS);
        }

        producer.close();
    }
}
