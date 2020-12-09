package juc.threadSafeList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        //Map是这样用的吗         Map<Object, Object> map = new HashMap<>();
        //不是，工作中不用HashMap
        // 默认等价于什么？
        // new HashMap<>(16,0.75f)
        /**
         * hashmap并发异常解决方案
         * 1、Map<String, String> map =   Collections.synchronizedMap(new HashMap<String, String>(16,0.75f));
         * 2、Map<String, String> map = new ConcurrentHashMap<String, String>(16,0.75f);
         */
        //加载因子默认为0.75，初始化容量默认为16
        Map<String, String> map = new ConcurrentHashMap<String, String>(16,0.75f);
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
