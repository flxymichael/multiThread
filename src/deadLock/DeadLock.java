package deadLock;

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
                //sleep可以放大问题的发生性
                Thread.sleep(1000);
                synchronized (lipstick){
                    System.out.println(this.name+"拿到了口红");
                }
                System.out.println(this.name+"化完妆了");
            }
        }else{
            synchronized (lipstick){
                System.out.println(this.name+"拿到了口红");
                synchronized (mirror){
                    System.out.println(this.name+"拿到了镜子");
                }
                Thread.sleep(1000);
                System.out.println(this.name+"化完妆了");
            }
        }
    }
}