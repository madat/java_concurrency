package com.concurrentpackage;

import java.util.concurrent.ThreadFactory;


/**
 * We can implement Threadfactory interface to create a threadfactory.
 * Having a threadFactory simplifies the process to name a thread, make it a daemon, giving thread group name etc.
 * @author user
 *
 */
public class ThreadFactoryExample {

	public static void main(String[] args) {

		MyThreadFactory threadFactory=new MyThreadFactory("threadFactory");

		ThreadTask task=new ThreadTask();

		for(int i=0;i<10;i++){
			Thread newThread = threadFactory.newThread(task);

			newThread.start();
		}
	}

}


/**
 * ThreadFactory has only one method. Except a runnable and return a Thread.
 * @author user
 *
 */
class MyThreadFactory implements ThreadFactory{
	   String custName;

	public MyThreadFactory(String custName){
		this.custName=custName;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t=new Thread(r,"thread custome name");
		return t;
	}

}

class ThreadTask implements Runnable
{
   @Override
   public void run()
   {
      try
      {
         System.out.println("in task");
         Thread.sleep(5000);
      } catch (InterruptedException e)
      {
         e.printStackTrace();
      }
   }
}
