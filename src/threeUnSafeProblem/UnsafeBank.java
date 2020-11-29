package threeUnSafeProblem;


//synchronized分为同步方法和同步块，同步方法只能锁住this，同步块可以锁住任意对象
//在这个例子中，同步问题出现在account账户，而取钱动作发生在drawing.run()
//同步方法锁住drawing对象没用，必须同步块锁住drawing中的account
public class UnsafeBank {
    public static void main(String[] args) {
        Account account = new Account(100, "结婚基金");

        Drawing me = new Drawing(account, 50, "我");
        Drawing wife = new Drawing(account, 100, "对象");

        me.start();
        wife.start();

    }
}

class Account {
    int money;
    String name;

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

class Drawing extends Thread {
    private Account account;
    //取了多少钱
    int drawingMoney;
    //现在手里还有多少钱
    int nowMoney;

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }


    //在run方法直接加synchronized就不行了，因为synchronized锁的是this，此处的this是指drawing对象
    //而我们需要关注的是account对象
    //因此应该同步块
    @Override
    public  void run() {
        //把之前的代码放到这个同步块就好了
        synchronized (account){
            if (account.money < drawingMoney) {
                System.out.println(Thread.currentThread().getName() + "取得时候发现钱不够了，不取了");
                return;
            }

            //sleep常用于放大问题的发生性
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            account.money -= drawingMoney;
            nowMoney += drawingMoney;


            //因为这个类继承了Thread,所以自然也继承了Thread的getName方法
            //Thread.currentThread().getName()==this.getName()
            System.out.println(this.getName() + "取了这么多钱：" + nowMoney);
            System.out.println(account.name + "还有这么多钱：" + account.money);

        }


    }
}