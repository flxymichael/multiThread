package threadSafe;

public class UnsafeBuyTicket {

    public static void main(String[] args) {
        Customer customer = new Customer();

        //推荐实现Runnable借口而不是继承Thread类的原因就在于此，方便多个线程操作同一个对象,此处的Customer叫做售票大厅也许会更好
        Thread thread0 = new Thread(customer, "爷");
        Thread thread1 = new Thread(customer, "黄牛");
        Thread thread2 = new Thread(customer, "淘宝店");

        //由于线程不安全，此处会出现多人买到同一张票的情况。
        thread0.start();
        thread1.start();
        thread2.start();
    }


}

class Customer implements Runnable {
    @Override
    public void run() {
        while (flag) {
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int nums = 10;
    public boolean flag = true;

    public void buy() throws InterruptedException {
        if (nums < 0) {
            flag = false;
            return;
        }
        Thread.sleep(100);//模拟延时
        System.out.println(Thread.currentThread().getName() + "拿到了" + nums--);
    }
}
