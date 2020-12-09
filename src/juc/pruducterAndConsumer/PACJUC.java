package juc.pruducterAndConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PACJUC {
    public static void main(String[] args) {
        ProductJUC productJUC = new ProductJUC();
        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                productJUC.increase();
            }
        }, "生产者A").start();
        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                productJUC.increase();
            }
        }, "生产者B").start();
        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                productJUC.increase();
            }
        }, "生产者C").start();
        new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                productJUC.decrease();
            }
        }, "消费者E").start();


    }

}

//判断等待、业务、唤醒
class ProductJUC {
    private int nums;
    private final Lock lock = new ReentrantLock();
    //用condition.await和condition.signal替换了object.wait和object.notify
    //condition可以精准的控制线程的唤醒顺序
    private final Condition condition = lock.newCondition();

    public void increase() {
        lock.lock();
        try {
            while (nums != 0) {
                condition.await();
            }
            nums++;
            System.out.println(Thread.currentThread().getName() + nums);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrease() {
        lock.lock();
        try {
            while (nums == 0) {
                condition.await();
            }
            nums--;
            System.out.println(Thread.currentThread().getName() + nums);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

}
