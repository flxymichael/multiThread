package juc.pruducterAndConsumer;

/**
 * 生产者消费者问题
 */
//判断等待、业务、通知
public class ProducerAndConsumer {

    public static void main(String[] args) {
        Product01 product = new Product01();
        //这种写法没有错，但是企业一般不这么搞，企业的搞法参照SaleTicketDemo02WithLock
//        new Thread(new Producer(product),"生产者1").start();
//        new Thread(new Producer(product),"生产者2").start();
//        new Thread(new Producer(product),"生产者3").start();
//        new Thread(new Consumer(product),"消费者").start();

        //这就是个生产者了呗
        new Thread(() -> { for (int i = 0; i < 50000; i++) product.increase(); },"生产者A").start();
        new Thread(() -> { for (int i = 0; i < 50000; i++) product.increase(); },"生产者B").start();
        new Thread(() -> { for (int i = 0; i < 50000; i++) product.increase(); },"生产者C").start();

        //消费者
        new Thread(() -> { for (int i = 0; i < 50000; i++) product.decrease(); },"消费者A").start();



    }
}
//
//class Producer implements Runnable {
//    Product product;
//
//    public Producer(Product product) {
//        this.product = product;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 50000; i++) {
//            product.increase();
//        }
//    }
//}
//
//class Consumer implements Runnable {
//    Product product ;
//
//    public Consumer(Product product) {
//        this.product = product;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 50000; i++) {
//            product.decrease();
//        }
//    }
//}

class Product01 {
    int nums;
    public synchronized void increase() {

        //判断等待
        while (nums != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //业务
        nums++;
        System.out.println(Thread.currentThread().getName() + "-->" + nums);
        //通知
        this.notifyAll();
    }

    public synchronized void decrease() {
        while (nums == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        nums--;
        System.out.println(Thread.currentThread().getName() + "-->" + nums);
        this.notifyAll();
    }
}


