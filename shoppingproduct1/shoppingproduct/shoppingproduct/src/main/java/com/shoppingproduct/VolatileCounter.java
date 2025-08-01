package com.shoppingproduct;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileCounter {

    private AtomicInteger count=new AtomicInteger(0) ;

    public int getCount() {
        return count.get();
    }

    public
    void Increment() {
        count.incrementAndGet();

    }

    public static void main(String[] args) throws InterruptedException {
        VolatileCounter vc=new VolatileCounter();
        Thread t1=new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                vc.Increment();
            }
        });

        Thread t2=new Thread(()->{
            for(int i=0;i<1000;i++)
            {
                vc.Increment();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
   System.out.print(vc.count);
    }
}
