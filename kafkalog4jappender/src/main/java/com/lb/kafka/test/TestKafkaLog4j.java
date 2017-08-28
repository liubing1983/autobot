package com.lb.kafka.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by samsung on 2017/4/12.
 */
public class TestKafkaLog4j {
    private static Logger logger = LoggerFactory.getLogger(TestKafkaLog4j.class);

    public static void main(String[] args){
        while(true) {
            logger.debug("other ---  debug");
            logger.info("other ---  info");
            logger.error("other ---  error");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
