package com.lb.book.thread.p07;

/**
 * 测试线程的中断
 * Created by samsung on 2017/9/5.
 */
public class PrimeGenerator extends Thread {


    @Override
    public void run() {
        long number=1L;
        while (true) {
            if (isPrime(number)) {
                System.out.println("Number "+number+" is Prime");
            }
            // 判读是否已经中断
            if (isInterrupted()) {
                System.out.println("The Prime Generator has been Interrupted");
                return;
            }
            number++;
        }
    }

    /**
     * 判断入参是否为质数
     * @param number
     * @return
     */
    private boolean isPrime(long number) {
        if (number <=2) {
            return true;
        }

        for (long i=2; i<number; i++){
            if ((number % i)==0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        // 执行线程
        Thread task=new PrimeGenerator();
        task.start();

        //  等待5秒，然后中断 PrimeGenerator 方法。
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 设置中断
        task.interrupt();
    }

}
