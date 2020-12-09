package threadStatus;

public class ThreadSleep {
    public static void main(String[] args) {
        int i = 10;
        ThreadSleep threadSleep = new ThreadSleep();
        threadSleep.timeDown(i);
    }
    private void timeDown(int time){
        while (time>0){
            try{
                Thread.sleep(1000);
                System.out.println("过去了这么多秒："+--time);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}