package com.lb.book.thread.p05.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by samsung on 2017/9/13.
 */
public class QueueManager {

    private static ArrayBlockingQueue<String>  queue = null;


    public ArrayBlockingQueue<String>  getQueue(){
        if(queue == null){
            queue = new ArrayBlockingQueue<String>(10);
        }
        return queue;
    }

    public static void main(String[] args){
        QueueManager qm = new QueueManager();
        while(true) {
            System.out.println(qm.getQueue().size());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



}
