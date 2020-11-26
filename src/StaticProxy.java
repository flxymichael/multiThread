//静态代理模式总结：
//真实对象和代理对象都要实现同一个接口
//代理对象要代理真实角色

//好处：
//代理对象可以做很多真实对象做不了的事
//真实对象专注于做自己的事就好

interface Marry {
    void HappyMarry();
}

//真实结婚角色
class You implements Marry {
    String name;

    @Override
    public void HappyMarry() {
        System.out.println(name + "结婚了，开心");
    }

    public You(String name) {
        this.name = name;
    }
}

//代理角色，帮人结婚
class WeddingCompany implements Marry {
    private Marry target;//要代理的人即要结婚的人

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    private void before() {
        System.out.println("结婚之前,布置婚礼");
    }

    @Override
    public void HappyMarry() {
        //用这种骚操作在主要动作前后插入自己想干的事
        before();
        this.target.HappyMarry();//真实结婚对色
        after();
    }

    private void after() {
        System.out.println("结婚之后，收钱");
    }
}

public class StaticProxy {
    public static void main(String[] args) {
        new WeddingCompany(new You("爷")).HappyMarry();
        //线程也是这么搞得。比如：
        new Thread(()->System.out.println("哪个傻逼结婚")).start();
    }

}
