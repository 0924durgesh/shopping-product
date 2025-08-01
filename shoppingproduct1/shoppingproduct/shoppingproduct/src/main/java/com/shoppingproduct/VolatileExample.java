package com.shoppingproduct;

class SharedResourced
{
    private volatile boolean flag=false;

    public  void setFlagTrue()
    {
        System.out.print("writer thread made flag true");
        flag=true;

    }
    public void printFlagTrue()
    { while (!flag)
    {
        //do nothing
    }
        System.out.print("flag is true");
     }

}
public class VolatileExample {

    public static void main(String[] args) {
        SharedResourced ob=new SharedResourced();

        Thread writeThread=new Thread(()->{

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ob.setFlagTrue();});

        Thread printthread=new Thread(()-> ob.printFlagTrue());
          writeThread.start();
          printthread.start();

    }
}
