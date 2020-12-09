package juc.supportClass;

import java.util.concurrent.CountDownLatch;

//减法计数器
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        //总数是6，必须要执行任务的时候再使用
        CountDownLatch countDownLatch = new CountDownLatch(6);//倒计时
        new Thread(()->{
            for (int i = 0; i < 6; i++) {
                System.out.println(i+"执行完毕");
                countDownLatch.countDown();//减1
            }
        }).start();
        countDownLatch.await();//等待计数器归零，然后再向下执行
        System.out.println("所有任务执行完毕");
    }
}
