//通过标识为来使线程停止
//jdk不建议使用thread.stop（）方法
public class ThreadStop implements Runnable {
    boolean flag = true;
    @Override
    public void run() {
            int i =0;
            while (flag){
                System.out.println("线程运行了"+(++i));
            }
    }
    private void stop(){
        this.flag=false;
    }

    public static void main(String[] args) {
        ThreadStop threadStop = new ThreadStop();
        new Thread(threadStop).start();
        for (int i = 0; i <1000 ; i++) {
            System.out.println("main跑到"+i);
            if (i==100){
                System.out.println("线程该停止了");
                threadStop.stop();
            }
        }
    }
}
