package juc.threadSafeList;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
//学习方法：1、先会用，2、货比三家，寻找其他解决方案，3、分析源码
/**
 * CopyOnWrite 写时复制
 * 多个线程调用的时候，list在读取时固定。
 * 在写入的时候，会复制一份原数组出来，修改完成后，将原数组替换为修改后的复制品。（lock锁）
 * 避免覆盖，造成数据问题
 * 由于arraylist不是线程安全的，因此会报并发修改异常
 * ConcurrentModificationException
 * 解决方案
 * 1.List<String> list = new Vector<>()； add方法用了syn，效率较低
 * 2.List<String> list = Collections.synchronizedList(new ArrayList<>());
 * 3.List<String> list = new CopyOnWriteArrayList<>(); 用了lock锁比vector效率高
 */
public class COAArrayList {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
