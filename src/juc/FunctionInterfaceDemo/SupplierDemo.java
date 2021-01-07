package juc.FunctionInterfaceDemo;

import java.util.function.Supplier;

/**
 * 供给型接口，只有返回值，没有参数
 */
public class SupplierDemo {
    public static void main(String[] args) {
        Supplier supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 66666;
            }
        };
        Supplier<Integer> supplier1= ()->66666777;
        System.out.println(supplier.get());
        System.out.println(supplier1.get());
    }
}
