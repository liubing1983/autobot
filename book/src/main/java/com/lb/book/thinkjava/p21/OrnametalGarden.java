package com.lb.book.thinkjava.p21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 计数
 */
class Count{
    private  int count = 0;
    private Random rand = new Random(47);
    public synchronized int increment(){
        int tmp = count;
        if(rand.nextBoolean()) Thread.yield();
        return (count = ++tmp);
    }
    public synchronized int value(){return count;}
}

/**
 *  转门
 */
class Entrance implements  Runnable{
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    // 当前转门经过的人数
    private int number = 0;
    // 当前转门编号
    private final int id;
    // 当前转门开关状态
    public static volatile  boolean canceled = false;
    public static void cancel(){canceled = true;}

    public Entrance(int id){
        this.id = id;
        entrances.add(this);
    }
    @Override
    public void  run(){
        while(!canceled){
            synchronized (this){
                ++number;
            }
            System.out.println(this+ "  Total: " + count.increment());

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("stopping "+ this);
    }
    public synchronized int getValue(){return number ;}

    public String toString(){
        return "Entrance: " + id + " : " + getValue();
    }

    public static  int getTotalCount(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum = 0;
        for(Entrance e : entrances){
            sum += e.getValue();
        }
        return sum;
    }
}

/**
 * Created by liub on 2017/3/7.
 */
public class OrnametalGarden  {
  public static void main(String[] args) throws  Exception{
      ExecutorService exec = Executors.newFixedThreadPool(5);
      for(int i = 0; i<5; i++) {
          System.out.println(i);
          exec.execute(new Entrance(i));
      }
          TimeUnit.SECONDS.sleep(4);
          Entrance.cancel();
          exec.shutdown();

          if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS)){
              System.out.println("Some");
          }

          System.out.println("Total : " + Entrance.getTotalCount());
          System.out.println("Sum : " + Entrance.sumEntrances());
  }
}
