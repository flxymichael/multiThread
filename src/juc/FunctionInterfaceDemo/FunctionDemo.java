package juc.FunctionInterfaceDemo;

import java.util.function.Function;
/**
 * 函数式接口，简化编程模型
 */

/**
 * Function,函数型接口
 * 两个参数，第一个为传入值，第二个为返回值
 * 只要是函数式接口，都可以用lambda简化
 */
public class FunctionDemo {
    public static void main(String[] args) {
        //这样就可以搞出来一个工具方法，
        //有两个参数，第一个为传入值，第二个为返回值
        Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(String o) {
                return o+"you";
            }
        };
        //简化版，范性一般不要省略
        Function<String, String> function1 = (str)->{return str+"you";};

        //极简版
        Function function2 = str->{return str+"you";};

        System.out.println(function.apply("fk"));
        System.out.println(function1.apply("fk"));
        System.out.println(function2.apply("fk"));

    }
}
