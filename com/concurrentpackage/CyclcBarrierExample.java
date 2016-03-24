package com.concurrentpackage;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *Cyclic barrier is almost like countdownlatch, where a single/multiple threads wait for other threads to finish jobs.
 *
 *Difference is that cyclicBarrier is reusbale.
 *
 *We initialize CyclicBarrier with number of parties/threads. We can await() on each party to say other parties that it is waiting.
 *We can use cyclic barrier where we need multiple participant parties to wait for each other to finish some final work
 *ex :To implement multi player game which can not begin until all player has joined.

 * @author user
 *
 */
public class CyclcBarrierExample {

	public static void main(String[] args) {
		CyclicBarrier cb=new CyclicBarrier(3, new Runnable(){

			@Override
			public void run() {
				System.out.println("main task to be done after all parties croseed the barrier");

			}

		});


		Thread t1=new Thread(new Task(cb,10000),"thread1");
		Thread t2=new Thread(new Task(cb,2000),"thread2");
		Thread t3=new Thread(new Task(cb,6000),"thread3");
		t1.start();
		t2.start();
		t3.start();

	}

}


class Task implements Runnable{
	private CyclicBarrier cb;
    private long waitTime;
	 public Task(CyclicBarrier cb,long waitTime){
		 this.cb=cb;
		 this.waitTime=waitTime;
	 }
   public void run(){
	   try{
	   System.out.println(Thread.currentThread().getName()+" is waiting on barrier");

	   Thread.sleep(waitTime);  //even after waittime is over for a thread it will wait in cyclic barrier for other participants to cross the barrier.
	    cb.await();

	   System.out.println(Thread.currentThread().getName()+" has croseed the barrier");
	   }catch(InterruptedException|BrokenBarrierException e){

	   }


   }
}
