package juc.FunctionInterfaceDemo;

import java.util.function.Predicate;

/**
 * predicate
 * 断定型接口，返回值只能是bool
 */
public class PredicateDemo {
    public static void main(String[] args) {
        Predicate predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.length()>=10;
            }
        };
        Predicate<String> predicate2 = Str-> Str.length()>=10;

        System.out.println(predicate.test("asd"));
        System.out.println(predicate2.test("asdssaasdsadadada"));
    }
}
