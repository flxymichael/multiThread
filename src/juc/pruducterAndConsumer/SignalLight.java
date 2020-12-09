package juc.pruducterAndConsumer;
//生产者消费者问题解决方法2：信号灯，标志位
//管道法中的管道有没有满，不就是一个标志吗？
public class SignalLight {

    public static void main(String[] args) {
        TV tv = new TV();
        Player player = new Player(tv);
        Watcher watcher = new Watcher(tv);

        new Thread(player).start();
        new Thread(watcher).start();
    }

}

//生产者-->电视台
class Player implements Runnable{
    TV tv;

    public Player(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if (i%3==0){
                this.tv.play("广告时间到");
            }else {
                this.tv.play("铁齿铜牙纪晓岚播放中");
            }
        }
    }
}
//消费者-->观众
class Watcher implements  Runnable{
    TV tv;

    public Watcher(TV tv) {
        this.tv = tv;
    }

    @Override
    public void run() {
        //让消费者等待一下生产者
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i <20 ; i++) {
            this.tv.watch();
        }

    }
}

//产品-->节目
class TV{
    //演员录制节目，观众等待
    //观众观看节目，演员等待

    String season;//表演的节目
    boolean flag = false;//节目有没有准备好

    //表演，录制节目
    public  synchronized  void play(String season){
        if (flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("电视台播放了了："+season);
        this.season=season;
        //通知观看
        this.notifyAll();
        this.flag=!this.flag;
    }
    //观看
    public synchronized void watch(){
        if (!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("观看了:"+season);
        this.notifyAll();
        this.flag=!this.flag;
    }
}