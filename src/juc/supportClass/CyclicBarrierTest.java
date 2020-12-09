package juc.supportClass;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//加法计数器
public class CyclicBarrierTest {
    public static void main(String[] args) {
        //集齐七颗龙珠召唤神龙
        CyclicBarrier barrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙成功！");
        });

        for (int i = 1; i <=7; i++) {
            final int temp = i;//下面的lambda表达式本质是new了一个线程对象，里面拿不到i，要用i可以借助一个final 变量
            new Thread(()->{
                System.out.println("第"+temp+"颗龙珠拿到手了");
                try {
                    barrier.await();//等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
