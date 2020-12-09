package singleTon;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LazyMan {
    private LazyMan() {

        //检测反射，这就相当于三重检测了
        //如果在getInstance方法调用（也就是lazyMan生成之前）调用反射，则该方法失效
        synchronized (LazyMan.class){
            if (lazyman!=null){
                throw new RuntimeException("不要试图使用反射破坏异常");
            }
        }
        //构造方法的执行次数就相当于创建对象的数量
        System.out.println(Thread.currentThread().getName()+"ok");
    }

    //volatile禁止指令重排序
    private volatile static  LazyMan lazyman;

    public static LazyMan getInstance(){
        //双重检测锁模式的懒汉式单例 简称dcl
        if (lazyman==null){
            synchronized (LazyMan.class){
                //单线程下单例ok
                if (lazyman==null){
                    lazyman = new LazyMan(); //不是原子性操作
                    /**
                     * 1、分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把对象指向空间
                     *
                     * 指令可能重排，不按照123的顺序排
                     *
                     * A线程按照132的顺序执行
                     * B线程进来后发现内存空间不为空，则直接去return lazyMan，此时A线程第二部还没有走完，返回的结果就有问题。
                     * 对lazyMan加上volatile关键字可以禁止指令重排
                     */
                }
            }
        }

        return lazyman;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //反射，破坏单例模式
        LazyMan instance01 = LazyMan.getInstance();
        System.out.println(instance01.hashCode());


        //获取构造器
        Constructor<LazyMan> declaredConstructor = LazyMan.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);// 将构造器可见性改为public
        LazyMan instance02 = declaredConstructor.newInstance();

        //hashcode不一样就说明对象不一样，则单例模式被破坏
        System.out.println(instance02.hashCode());
    }
}

