package pruducterAndConsumer;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//线程间通信一般有管程法和信号灯法，此处用管程法演示生产者-消费者模型
public class Pip {
    public static void main(String[] args) {
        ProductContainer productContainer = new ProductContainer();

        Producer producer01 = new Producer(productContainer);
        Customer customer01 = new Customer(productContainer);

        new Thread(producer01, "producer01").start();
        new Thread(customer01, "customer01").start();
    }
}

class Producer implements Runnable {
    private ProductContainer productContainer;

    public Producer(ProductContainer productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            productContainer.push(new Product(i));
            System.out.println(Thread.currentThread().getName() + "生产了" + i + "号产品");
        }
    }
}

class Customer implements Runnable {
    private ProductContainer productContainer;

    public Customer(ProductContainer productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Product product = productContainer.consume();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "消费了---->" + product.id + "号产品");
        }
    }
}

class Product {
    int id;

    public Product(int id) {
        this.id = id;
    }
}

class ProductContainer {
    private int MAX_NUM = 10;
    private int count = 0;
    Product[] products = new Product[MAX_NUM];

    public synchronized void push(Product product) {
        //如果容器满了，通知消费者消费

        //多个线程阻塞于此，当被notifyAll时，第一个被唤醒的线程拿到count==MAX_NUM-1没问题，第二个就惨了.
//        if (count==MAX_NUM){
//           this.wait();
//        }

        //改用while解决了数组越界的问题，但是现在多个生产者会生产同一个商品，为什么？
        while (count >= MAX_NUM) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //没满就继续生产
        products[count] = product;
        count++;
        this.notifyAll();
    }

    public synchronized Product consume() {
        //容器为空则通知生产者生产
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //否则继续消费
        count--;
        Product product = products[count];
        this.notifyAll();
        return product;
    }
}

