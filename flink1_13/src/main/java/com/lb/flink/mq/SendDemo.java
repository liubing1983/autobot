package com.lb.flink.mq;


import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqClientConfigBuilder;
import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqConnector;
import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqSender;
import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqSenderBuilder;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.REArray;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.REBasicValue;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.REFieldType;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.REPac;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.RERecord;
import cn.ac.iie.di.datadock.rdata.exchange.client.exception.REConnectionException;
import cn.ac.iie.di.datadock.rdata.exchange.client.exception.REFormatterException;
import cn.ac.iie.di.datadock.rdata.exchange.client.formatter.impl.REAvroFormatter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ClassName SendDemo
 * @Description @TODO
 * @Author liubing
 * @Date 2021/7/22 14:16
 * @Version 1.0
 **/
public class SendDemo {

    public static void main(String[] args) throws REConnectionException,  REFormatterException {

        //start sender
        String namesrv = "localhost:9876";
        String topic = "lb";
        String sendGroup = "lb";
        String user = "user";
        String passwd = "passwd";
        RERmqSenderBuilder builder =  new RERmqConnector().senderBuilder(namesrv);
        RERmqSender sender = builder
                .setDefaultTopic(topic)
                .setGroup(sendGroup)
                .setInstanceName("asef_sent_0001") //optional
                // .setUser(user, passwd)
                .setVersion(RERmqClientConfigBuilder.RmqVersion.V3_X_X)
                // depends on rmq cluster. If not sure, use V3_X_X
                .build();
        sender.start();

        //send data
        for (int idx = 0; idx < 5; ++idx) {
            long start = System.currentTimeMillis();
            int n = 1000;
            for (int pacNum = 0; pacNum < 10000 / n; ++pacNum) {
                //the param must be TRUE, only strict mode is supported by
                // current RMQ cluster
                REPac pac = new REPac(true);
                for (int i = 0; i < n; ++i) {
                    //you can see all basic types in REBasicValue.java
                    RERecord rec = new RERecord();
                    rec.set("int_t", new REBasicValue.REInt(3 + i));//int
                    rec.set("bool_t", new REBasicValue.REBoolean(false));//bool
                    rec.set("double_t", new REBasicValue.REDouble(3.3));//double
                    rec.set("string_t", new REBasicValue.REString("hahaha_" + i));//string

                    //struct
                    HashMap<String, Object> struct = new HashMap<>();
                    struct.put("t_int", 100 + i);
                    struct.put("t_s", "asd----------" + i);
                    rec.set("struct_t", new REBasicValue.REStruct(struct));

                    //one way to build an array
                    ArrayList<REBasicValue.RELong> longList = new ArrayList<>();
                    longList.add(new REBasicValue.RELong(10000L + i));
                    longList.add(new REBasicValue.RELong(20000L + i));
                    longList.add(new REBasicValue.RELong(30000L + i));
                    rec.set("long", REArray.of(longList));

                    //another way to build an array
                    REArray<REBasicValue.REString> strArray = new REArray<>(REFieldType.String);
                    strArray.add(new REBasicValue.REString("str1"));
                    strArray.add(new REBasicValue.REString("str2"));
                    rec.set("strListTest", strArray);

                    pac.addRecord(rec);
                }

                boolean sendSucceeded = false;
                sendSucceeded = sender.send(pac);

                //use this to send to another topic
                sendSucceeded = sender.send("lb1", pac);

                //you can also send packages with delay level like this
                sendSucceeded = sender.send(pac, 1);

                //or you can manually encode and send data
                //note that encode costs cpu
                byte[] bytes = REAvroFormatter.encodePac(pac);
                // sendSucceeded = sender.send("topic", "tag", bytes, 1);

                //other send functions can be found in `RERmqSender.java`

                System.out.println("send idx: " + idx + " ,time: " + System.currentTimeMillis());
            }

            System.out.println("send time : " + (System.currentTimeMillis() - start));
        }
        System.out.println("send succeeded, stopping sender");
        //stop and no more data should be sent
        sender.stopGracefully();
        System.out.println("sender stopped.");
    }
}
