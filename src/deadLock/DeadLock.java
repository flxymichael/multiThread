package deadLock;

//死锁发生的四个条件：
//1.互斥：一个资源一次只能被一个进程（线程使用）
//2.请求与保持：一个进程因为请求资源而被阻塞的时候，不会释放已经持有的资源
//3.不剥夺：进程已经获得的资源，再未使用完成之前，不能强行剥夺。即2中的资源，既不会主动释放，也不能强行剥夺
//4.循环等待：多干个进程形成一种头尾相接的循环等待资源关系。

//只要破坏上述一个条件，就可避免死锁
//在该例子中，通过破坏循环等待条件，解除了死锁。即，不能让一个人拿着一个资源一直等待其他资源
public class DeadLock {
    public static void main(String[] args) {
        Makeup m1 = new Makeup("白雪公主",0);
        Makeup m2 = new Makeup("灰姑娘",1);
        m1.start();
        m2.start();
    }
}
class Mirror{

}
class Lipstick{

}
class Makeup extends Thread{

    //需要的资源只有一份，用static来保证只有一份
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    private String name;
    private int choice;

    public Makeup(String name, int choice) {
        this.name = name;
        this.choice = choice;
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void makeup() throws InterruptedException {
        if (choice==0){
            synchronized (mirror){
                System.out.println(this.name+"拿到了镜子");
                //sleep可以放大问题的发生性,不加sleep可能某一个线程跑很快直接把两个锁拿到就跑完了
                Thread.sleep(1000);
            }
            synchronized (lipstick){
                System.out.println(this.name+"拿到了口红");
            }
            System.out.println(this.name+"化完妆了");
        }else{
            synchronized (lipstick){
                System.out.println(this.name+"拿到了口红");
                Thread.sleep(1000);
            }
            synchronized (mirror){
                System.out.println(this.name+"拿到了镜子");
            }
            System.out.println(this.name+"化完妆了");
        }
    }
}