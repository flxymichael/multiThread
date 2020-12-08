package juc.threadSafeList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Set的本质就是hashmap的key，不会重复，map.put(key,value)，所有的value都是一个常量president
 * 会报ConcurrentModificationException
 * 解决方法：
 * 1、 Set<String> set  = Collections.synchronizedSet(new HashSet<>());
 * 2、 Set<String> set  = new CopyOnWriteArraySet<>();
 */
public class COASet {

    public static void main(String[] args) {

        Set<String> set  = new CopyOnWriteArraySet<>();
        for (int i = 0; i <30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }
}
