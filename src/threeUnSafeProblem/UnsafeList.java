package threeUnSafeProblem;

import java.util.ArrayList;
import java.util.List;

public class UnsafeList {
    public static void main(String[] args) throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i <100000 ; i++) {
            new Thread(()->{
                synchronized (list){
                    list.add(Thread.currentThread().getName());
                }
            }).start();
        }

        Thread.sleep(1000);
        //线程冲突，size会小于预定值100000
        System.out.println(list.size());
    }
}
