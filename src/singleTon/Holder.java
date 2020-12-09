package singleTon;
//静态内部类
//外部类加载时并不需要立即加载内部类，内部类不被加载则不去初始化INSTANCE，故而不占内存
//第一次调用getInstance()方法会导致虚拟机加载InnerClass类
//能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。


//虚拟机会保证一个类的<clinit>()方法在多线程环境中被正确地加锁、同步，如果多个线程同时去初始化一个类，
//那么只会有一个线程去执行这个类的<clinit>()方法，其他线程都需要阻塞等待，直到活动线程执行<clinit>()方法完毕。


//因为存在反射，所以，除了枚举外，所有的单例模式都不安全。
public class Holder {
    private Holder() {
        System.out.println(Thread.currentThread().getName() + "6");
    }

    private static class InnerClass {
        private static final Holder holder = new Holder();
    }

    private static Holder getInstance() {
        //getInstance()方法并没有多次去new对象
        //故不管多少个线程去调用getInstance()方法，取的都是同一个INSTANCE对象，而不用去重新创建
        //当getInstance()方法被调用时，InnerClass才在Holder的运行时常量池里，
        //把符号引用替换为直接引用，这时静态对象INSTANCE也真正被创建
        return InnerClass.holder;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                Holder.getInstance();
            }).start();
        }
    }
}
