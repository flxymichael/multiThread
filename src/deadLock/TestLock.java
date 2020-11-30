package deadLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) {
        //多个资源操作同一个对象
        Ticket ticket = new Ticket();
        new Thread(ticket).start();
        new Thread(ticket).start();
        new Thread(ticket).start();

    }
}

class Ticket implements Runnable {
    private int nums = 10;
    private final Lock lock = new ReentrantLock();

    @Override
    public void run() {
            try {
                lock.lock();
                while (nums>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(nums--);
                }

            } finally {
                lock.unlock();
            }

    }
}
