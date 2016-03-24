package com.concurrentpackage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *java concurrent pakcage Lock mechanism to lock a critical section of execution, or an object.
 *Functionally its like synchronized block. But there are few difference between them.
 *
 *1. Timeout is not possible on synchronized block. In Lock, we can set timeout on tryLock()
 *2. We can not span a synchronized block over multiple methods. But a Lock can have its lock() and unlock() span over different methods.
 *
 *Lock is an interface, and ReEntrantLock is an implementation class
 *One should be careful to release a lock with unLock() after finishinh job, Otherwise other waiting threads will wait foreever.
 *
 * @author user
 *
 */
public class LockExample {

	public static void main(String[] args) {

		PrinterQueue printerQueue=new PrinterQueue();

		for(int i=0;i<10;i++){
			String filename="file"+i;
          Thread t=new Thread(new PrintDocs(filename, printerQueue));
          t.start();
	    }
	}

}



class PrintDocs implements Runnable{
	private String fileName="";
	PrinterQueue printerQueue;
	public PrintDocs(String fileName,PrinterQueue printerQueue){
		this.fileName=fileName;
		this.printerQueue=printerQueue;
	}

	@Override
	public void run() {
        System.out.println("this doc will be placed in priner queue="+fileName);
        printerQueue.print(fileName);

	}

}

class PrinterQueue{

    Lock lock=new ReentrantLock();

	public void print(String file){
        lock.lock();
        System.out.println("going to print file="+file);
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			 System.out.println("finished printing of file="+file);
			 lock.unlock();
		}

	}

}
