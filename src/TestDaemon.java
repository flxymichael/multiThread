//线程优先级分为1-10，越高，被执行的概率越大，看cpu心情
//用Thread.getPriority()和 Thread.setPriority()来查看修改线程状态
//线程分为用户线程（比如main线程） 和守护线程（比如gc）
//虚拟机必须保证用户线程执行完毕，但不用确保daemon执行完毕


public class TestDaemon {
    public static void main(String[] args) {
        God god = new God();
        Thread threadGod = new Thread(god);
        threadGod.setDaemon(true);//默认false为用户线程
        threadGod.start();

        Man man = new Man();
        Thread threadMan = new Thread(man);
        threadMan.start();
    }
}

class God implements Runnable{
    @Override
    public void run() {
        //God因为是daemon，所以并不会无限循环，待用户线程结束后，就会结束
        while (true){
            System.out.println("爷守护着你");
        }
    }
}

class Man implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("======爷开心的活了"+i+"天======");
        }
        System.out.println("Goodbye,World");
    }
}


