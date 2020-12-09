package singleTon;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum EnumSingle {
    INSTANCE;
    public EnumSingle getInstance(){
        return INSTANCE;
    }
}
class Test {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        EnumSingle instance01 = EnumSingle.INSTANCE;
        EnumSingle instance02 = EnumSingle.values()[0].getInstance();
        System.out.println(instance01.hashCode());
        System.out.println(instance02.hashCode());

        //通过一些骚操作发现enum里面的构造器其实是由参数的
        //Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(null);

        //反射不能破坏枚举
        Constructor<EnumSingle> declaredConstructor = EnumSingle.class.getDeclaredConstructor(String.class,int.class);

        declaredConstructor.setAccessible(true);
        EnumSingle instance03 = declaredConstructor.newInstance(null);
        System.out.println(instance03.hashCode());
    }
}
