package juc.readWriteLockDemo;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 * 比Lock更细粒度
 * 一组关联的锁，读锁是共享的，写锁是独占
 * 读读-共存
 * 写写-互斥
 * 读写-互斥
 *
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheWithLock cache = new MyCacheWithLock();
        for (int i = 0; i < 10; i++) {
            final String num = i + "";
            new Thread(() -> {
                cache.put(num, num);
            }, String.valueOf(i)).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            final String num = i + "";
            new Thread(() -> {
                cache.get(num);
            }, String.valueOf(i)).start();
        }


    }
}

//自定义缓存
class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    //未加锁，多线程情况下存在并发问题
    public void put(String key, Object val) {
        System.out.println(Thread.currentThread().getName().toString() + "开始写入" + val);
        map.put(key, val);
        System.out.println(Thread.currentThread().getName().toString() + "写入完成" + val);

    }

    public Object get(String key) {
        System.out.println(Thread.currentThread().getName().toString() + "开始读取");
        Object val = map.get(key);
        System.out.println(Thread.currentThread().getName().toString() + "读取完成" + val);
        return val;
    }

}



class MyCacheWithLock {
    private volatile Map<String, Object> map = new HashMap<>();
    private  ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //未加锁，多线程情况下存在并发问题
    public void put(String key, Object val) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName().toString() + "开始写入" + val);
            map.put(key, val);
            System.out.println(Thread.currentThread().getName().toString() + "写入完成" + val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public Object get(String key) {
        readWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName().toString() + "开始读取");
            Object val = map.get(key);
            System.out.println(Thread.currentThread().getName().toString() + "读取完成" + val);
            return val;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }

}