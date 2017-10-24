package com.lb.book.thread.p07;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 测试在递归中使用中断
 * 我们使用Java异常来控制线程的中断。当你运行这个例子，程序开始浏览文件夹来检查他们是否含有那个文件。
 * 例如，如果你输入\b\c\d，那么程序会循环调用3次processDirectory()方法。
 * 当它检测到被中断时，它会抛出InterruptedException异常并继续执行方法，无论已经多少次循环调用。
 * Created by samsung on 2017/9/5.
 */
public class FileSearch implements Runnable {

    private String initPath;
    private String fileName;

    /**
     * 构造函数
     *
     * @param initPath
     * @param fileName
     */
    public FileSearch(String initPath, String fileName) {
        this.initPath = initPath;
        this.fileName = fileName;
    }


    @Override
    public void run() {
        File file = new File(initPath);
        //  它会检测fileName属性是不是路径，
        //  如果它是，就调用processDirectory()方法。
        //  这个方法会抛出一个InterruptedException异常，所以我们应该要捕获它。
        if (file.isDirectory()) {
            try {
                directoryProcess(file);
            } catch (InterruptedException e) {
                System.out.printf("%s: The search has been interrupted", Thread.currentThread().getName());
            }
        }
    }

    /**
     * 实现 directoryProcess()方法。这个方法会获取文件夹的文件和子文件夹并处理他们。
     * 对于每个路径，这个方法会传递路径作为参数来循环调用。对于每个文件，它会调用fileProcess()方法。
     * 处理完全部的文件和文件夹后，它会检查线程有没有被中断，
     * 在这个例子，会抛出一个InterruptedException异常。
     *
     * @param file
     * @throws InterruptedException
     */
    private void directoryProcess(File file) throws InterruptedException {
        File list[] = file.listFiles();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isDirectory()) {
                    directoryProcess(list[i]);
                } else {
                    fileProcess(list[i]);
                }
            }
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private void fileProcess(File file) throws InterruptedException {
        if (file.getName().equals(fileName)) {
            System.out.printf("%s : %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
        }
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    public static void main(String[] args) {

        // 创建并初始一个FileSearch类的对象和一个执行它的任务的线程。然后，开始执行线程。
        FileSearch searcher = new FileSearch("D:\\", "autoexec.bat");
        Thread thread = new Thread(searcher);
        thread.start();

        // 等10秒然后中断线程。
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
