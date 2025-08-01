package com.shoppingproduct;

public class MyThread extends Thread{
    private Counter co;

    public MyThread (Counter count)
    {
        this.co=count;
    }


}
