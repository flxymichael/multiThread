package juc;

//new      this    具体的一个对象
//static   class   唯一模版
//这两个是不同的两把锁，不需要彼此等待。
public class StaticMethod {
    public static void main(String[] args) {
        //两个对象，两个方法的调用者，两把锁，但两个对象的class模版只有一个
        //static 锁的是class
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        //虽然这两个方法的调用者不一样，但是由于存在static关键字，导致class被锁，因此还是会顺序执行
        //如果方法上没有static，则者两个线程的执行顺序不固定，因为方法的调用者不一样
        new Thread(()->{
            phone1.sendMessage();
        },"A").start();
        new Thread(()->{
            phone2.call();
        },"B").start();

    }
}

class Phone{

    //syn锁的是方法的调用者
    //static方法在类加载时就会初始化，因此锁的是class对象，唯一！
    public static synchronized void sendMessage(){
        System.out.println("发短信");
    }
    public static synchronized void call(){
        System.out.println("打电话");
    }
    public void hello(){

    }
}
