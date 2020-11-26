/**
 * 学习lambda表达式
 */
//函数式接口：只有一个抽象方法的借口,通常用lambda表达式来创建该接口的对象
interface ILove{
    abstract void love(int a);
}
//1.实现类
class Love implements  ILove{
    @Override
    public void love(int a) {
        System.out.println("I love you-->"+a);
    }
}
public class Lambda {
    //2.静态内部类
    static class Love2 implements  ILove{
        @Override
        public void love(int a) {
            System.out.println("I love you-->"+a);
        }
    }

    public static void main(String[] args) {
         //测试实现类
         ILove like = new Love();
         like.love(1);

         //测试静态内部类
        like = new Love2();
        like.love(2);

        //3.局部内部类及其测试
        class Love3 implements  ILove{
            @Override
            public void love(int a) {
                System.out.println("I love you-->"+a);
            }
        }
        like = new Love3();
        like.love(3);

        //4.匿名内部类，没有类的名称，必须依赖父类或者接口
        //为了实现抽象类或者接口中的抽象方法，并“一次性调用”
        like = new ILove() {
            @Override
            public void love(int a) {
                System.out.println("I love you-->"+a);
            }
        };
        like.love(4);

        //用lambda简化，前提是:必须是函数式接口
        like = (int a)->{
            System.out.println("I love you-->"+a);
        };
        like.love(5);

        //简化参数类型
        like = (a)->{
            System.out.println("I love you-->"+a);
        };
        like.love(6);

        //简化小括号,(多个参数也可以去掉参数类型，要去掉就都去掉，（a，int b这样的不行）)
        like = a->{
            System.out.println("I love you-->"+a);
        };
        like.love(7);

        //简化大括号（只有一样代码时才可以，类似于if如果后面只有一行的话，可以简化大括号）
        like = a->System.out.println("I love you-->"+a);
        like.love(8);
    }
}
