package singleTon;

//单例：构造器私有，全局只提供一个对象，且只由自己提供
//饿汉式单例
public class Hungry {
    private byte[] data1 = new byte[1024*1024];//很占内存的资源
    private Hungry() {
    }

    //Hungry内部有很占内存的资源，如果一上来就初始化，会存在很大问题
    private final static Hungry hungry = new Hungry();
    public static Hungry getInstance(){
        return hungry;
    }
}
