package juc.FunctionInterfaceDemo;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 消费型接口，只负责消费，没有返回值
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        Consumer consumer = new Consumer<String>(){
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        Consumer<String> consumer1 = s->System.out.println(s);
        consumer.accept("666");
        consumer1.accept("6666");

    }
}
