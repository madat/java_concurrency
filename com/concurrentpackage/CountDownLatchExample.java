package com.concurrentpackage;

import java.util.concurrent.CountDownLatch;


/**
 * what is countdown latch ? : Countodown latch simplifies wait/notify implementations.
 * It is a thread synchronizing mechanism where we can configure a thread to wait for one or more other threads.
 *
 * How does countdown latch work?
 * Countodown latch works on latch or gate principle. We define no of countdown , the main tread should wiat for.
 * The main thread waits for the countdown to become zero by calling CountDownLatch.wait(). The main thread will wait  till countdown becomes zero
 * or it is interrupted
 * Other thread should call CoundownLatch.countdown() one they have finished /started their jobs.
 *
 * Disadvantage of countdownlatch : it is not reusable. Once the countdown reaches 0,we can not use it again for another countdown.
 * @author user
 *
 */
public class CountDownLatchExample {


	/**
	 * my main thread will wait for a countdown of 3 to 0.
	 * @param args
	 */
	public static void main(String[] args) {
      final CountDownLatch latch=new CountDownLatch(3);

      Thread t1=new Thread(new Service(latch) );
      Thread t2=new Thread(new Service(latch) );
      Thread t3=new Thread(new Service(latch) );

      t1.start();t2.start();t3.start();

      try{
    	  latch.await();
    	  System.out.println("wait is over, main thread will do its work now");
      }catch(InterruptedException e){
    	  System.out.println(e);
      }

	}

}


class Service implements Runnable{
    private CountDownLatch latch;

	public Service(CountDownLatch latch){
       this.latch=latch;
	}

	public void run(){
       try{
    	   Thread.sleep(10000);
       }catch(InterruptedException ex){
            System.out.println(ex);
       }
       latch.countDown();
	}
}
