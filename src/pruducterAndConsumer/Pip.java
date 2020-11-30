package pruducterAndConsumer;


//线程间通信一般有管程法和信号灯法，此处用管程法演示生产者-消费者模型
public class Pip {
    public static void main(String[] args) {
        ProductContainer productContainer = new ProductContainer();

        Producer producer01 = new Producer(productContainer);
        Customer customer01 = new Customer(productContainer);

        new Thread(producer01,"producer01").start();
        new Thread(customer01,"customer01").start();
    }
}
class Producer implements Runnable{
    private ProductContainer productContainer;

    public Producer(ProductContainer productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
                productContainer.push(new Product(i));
                System.out.println("生产了"+i+"号产品");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Customer implements Runnable{
    private ProductContainer productContainer;

    public Customer(ProductContainer productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public void run() {
        for (int i = 0; i <100 ; i++) {
            try {
                Thread.sleep(100);
                Product product = productContainer.consume();
                System.out.println("消费了"+product.id+"号产品");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Product{
    int id;

    public Product(int id) {
        this.id = id;
    }
}

class ProductContainer{
    private int MAX_NUM =10;
    private int count = 0;
    Product[] products = new Product[MAX_NUM];
    public synchronized void push(Product product) throws InterruptedException {
        //如果容器满了，通知消费者消费
        if (count==MAX_NUM){
           this.wait();
        }
        //没满就继续生产
        products[count]=product;
        count++;
        this.notifyAll();
    }

    public synchronized Product consume() throws InterruptedException {
        //容器为空则通知生产者生产
        if (count==0){
            this.wait();
        }
        //否则继续消费
        count--;
        Product product = products[count];
        this.notifyAll();
        return product;
    }
}

