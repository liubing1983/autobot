package com.lb.flink.mq;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqClientConfigBuilder;
import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqConnector;
import cn.ac.iie.di.datadock.rdata.exchange.client.connector.impl.RERmqReceiverBuilder;
import cn.ac.iie.di.datadock.rdata.exchange.client.data.*;
import cn.ac.iie.di.datadock.rdata.exchange.client.exception.REConnectionException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * @ClassName ReceiveDemo
 * @Description @TODO
 * @Author liubing
 * @Date 2021/7/22 14:16
 * @Version 1.0
 **/
public class ReceiveDemo {

    //this is called to handle records
    private static BiConsumer<MessageExt, REPac> handler = (msg, pac) -> {
        //  `msg` is the original message from Rocketmq
        //  you can get all original information from `msg`
        //  for example DelayTimeLevel
        int delayTimeLevel = msg.getDelayTimeLevel();
        //msg information
        StringBuilder sb = new StringBuilder();
        sb.append("=== === getpac === ===\n");
        sb.append("\ttopic:").append(msg.getTopic());
        sb.append("\ttags:").append(msg.getTags());
        sb.append("\tmsgId:").append(msg.getMsgId()).append("\n");
        sb.append("\tpac:").append(pac).append("\n\n");
        System.out.println(sb.toString());

        // the type of each column
        HashMap<String/*key*/, REFieldType/*value type*/> types =
                pac.getDefaultTypes();

        System.out.println(types.keySet());

        //user-set map, may be empty
        Map<String, String> userDesc = pac.getUserDesc();


        //get records
        List<RERecord> recs = pac.getRecs();
        for (RERecord rec : recs) {
            //handle the record by iterator
            rec.forEach((RERecord.Field f) -> {
                String key = f.getKey();
                REValue value = f.getValue();
                REFieldType type = value.getType();
                switch (type) {
                    ///
                    ///
                    ///Basic Value
                    ///
                    ///
                    case Boolean: {
                        //get value
                        Boolean v = ((REBasicValue.REBoolean) value).getValue();
                        //do something
                        break;
                    }
                    case Int: {
                        Integer v = ((REBasicValue.REInt) value).getValue();
                        break;
                    }
                    case Long: {
                        Long v = ((REBasicValue.RELong) value).getValue();
                        break;
                    }
                    case Float: {
                        Float v = ((REBasicValue.REFloat) value).getValue();
                        break;
                    }
                    case Double: {
                        Double v = ((REBasicValue.REDouble) value).getValue();
                        break;
                    }
                    case String: {
                        String v = ((REBasicValue.REString) value).getValue();
                        break;
                    }
                    case Binary: {
                        byte[] v = ((REBasicValue.REBinary) value).getValue();
                        break;
                    }
                    case Struct: {
                        //get map value
                        Map<String, ?> mapValue = ((REBasicValue.REStruct) value).getValue();
                        //get type info of this map, which describes the type
                        // of each value in mapValue.
                        Map<String, REFieldType> detailType = pac.getDetail(key);
                        //do some with the type information
                        mapValue.forEach((k, v) -> {
                            switch (detailType.get(k)) {
                                case Int:
                                case Long:
                                default:
                            }
                        });
                        break;
                    }
                    ///
                    ///
                    /// Arrays
                    ///
                    ///
                    case Ints: {
                        List<REBasicValue.REInt> list = ((REArray<REBasicValue.REInt>) value).getValue();
                        for (REBasicValue.REInt reInt : list) {
                            Integer i = reInt.getValue();
                            //do something.
                        }
                        break;
                    }
                    case Structs: {
                        List<REBasicValue.REStruct> list = ((REArray<REBasicValue.REStruct>) value).getValue();
                        //  get type info of each map, which describes the type
                        //  of each value in maps.
                        Map<String, REFieldType> detailType = pac.getDetail(key);
                        for (REBasicValue.REStruct reStr : list) {
                            Map<String, ?> map = reStr.getValue();
                            //do something.
                        }
                        break;
                    }
                    case Array: {
                        switch (((REArray) value).getElementType()) {
                            case Int:
                            case String:
                            default:
                                //do some thing
                        }
                    }
                    default:
                }
            });

            //or you can get column_value by column_key:
               //  REBasicValue.REString value = (REBasicValue.REString) rec.get("struct_t");
            System.out.println(rec.get("struct_t").getValue().toString()+"------------");
            System.out.println(rec.get("long").getValue().toString()+"------------");
            System.out.println(rec.get("strListTest").getValue().toString()+"------------");
            //and do something
        }

    };

    //this is called when error happens
    private static BiConsumer<MessageExt, Exception> failureHandler = (msg,
                                                                       ex) -> {
        StringBuilder sb = new StringBuilder();
        sb.append("*** *** errorpac *** ***\n");
        sb.append("\ttopic:").append(msg.getTopic());
        sb.append("\ttags:").append(msg.getTags());
        sb.append("\tmsgId:").append(msg.getMsgId()).append("\n");
        System.out.println(sb.toString());
        ex.printStackTrace();
    };

    public static void main(String[] args) throws REConnectionException {
        String namesrv = "127.0.0.1:9876";
        String topic = "lb";
        String group = "lb";
        String user = "user";
        String passwd = "passwd";

        RERmqReceiverBuilder builder = new RERmqConnector().receiverBuilder(namesrv);
        builder.setGroup(group)
                .setInstanceName("asef")
                .setUser(user, passwd)
                .setConsumeThreadNum(1)
                //depends on rmq cluster. If not sure, use V3_X_X
                .setVersion(RERmqClientConfigBuilder.RmqVersion.V3_X_X)
                .setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //register handlers
        RERmqReceiverBuilder abc =  builder.registerMessageListener(topic, handler, failureHandler);

        // abc.getOriHandlers().keySet();

        System.out.print(abc.getOriHandlers().keySet()+"------------------");

        // WARN:
        // 1. 【DO NOT】 consume multiple topics with one group if not necessary;
        // 2. If it is necessary to consume multiple topics with one group,
        // make sure EVERY consumer in this group consumes EXACTLY SAME topics;
        // 3. EVERY topic can be registered with ONLY ONE subExpression, bet
        // multiple subExpression can be combined with "|";
       // builder.registerMessageListener("lb", "double_t|bool_t", handler, failureHandler);
        // Different topic can be registered with different handlers.
        BiConsumer<MessageExt, REPac> h2 = null;
        BiConsumer<MessageExt, Exception> fh2 = null;
       //  builder.registerMessageListener("topic2", h2, fh2);

        builder.build().start();

        builder.build().suspend();
    }
}
