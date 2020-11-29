package threeUnSafeProblem;

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

    @Override
    public void run() {
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