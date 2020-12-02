package juc;
//基本的卖票例子

/**
 * 多线程开发，公司中的开发，降低耦合性
 * 1、线程就是一个单独的资源类，没有任何附属的操作
 * 资源：属性，方法
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {

        //并发，多线程操作同一个资源类，把资源类丢入线程
        Ticket ticket =new Ticket();

        new Thread(()->{
            for (int i = 0; i <60 ; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }},"a").start();
        new Thread(()->{
            for (int i = 0; i <60 ; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }},"b").start();
        new Thread(()->{
            for (int i = 0; i <60 ; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }},"c").start();



    }
}
//资源类 oop
class Ticket{
    //属性，方法
    private int nums = 50;

    //卖票的方式
    public synchronized void sale(){
        if (nums>0){
            System.out.println(Thread.currentThread().getName()+"卖出了"+nums--+"票，剩余："+nums);
        }
    }
}
