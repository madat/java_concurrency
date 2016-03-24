package com.concurrentpackage;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorExample {

	public static void main(String[] args) {


		ThreadFactory threadFactory=Executors.defaultThreadFactory();

		/**
		 * cachedThreadPool creates new thread on need basis. It reuses previously created Thread on need basis
		 * newCachedThreadPool by default returns a ThreadPoolExecutor of
		 * ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
         *                              new SynchronousQueue<Runnable>());
         *
         *
		 */

		ThreadPoolExecutor threadPoolExec=(ThreadPoolExecutor)Executors.newCachedThreadPool();
		threadPoolExec.setCorePoolSize(2); // change core pool size to 2. When we get a new task and available threads are less than 2, another thread will be created
		threadPoolExec.setMaximumPoolSize(4); // maximum number of threads allowed in the pool
		threadPoolExec.setThreadFactory(threadFactory);
		threadPoolExec.setRejectedExecutionHandler(new RejectedExecutionHandlerImpl());

		//pass this threadpoolExec to out monitor to monitor status of the pool
		ThreadpoolMonitor monitor=new ThreadpoolMonitor(threadPoolExec);
		new Thread(monitor).start();

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//submit works to threads
		for(int i=0;i<10;i++){
			threadPoolExec.execute(new Thread(new Worker("Thread no="+i)));
		}
		try {
			Thread.sleep(50000);
			threadPoolExec.shutdown();
			Thread.sleep(500);
			monitor.shutDown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

}

class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }

}

/**
 * a monitor class o monitor different status of the ThreadPoolExecutor
 * @author user
 *
 */
class ThreadpoolMonitor implements Runnable{

	private ThreadPoolExecutor threadPoolExec;
	private long delay=5000;
    private boolean run=true;

	public ThreadpoolMonitor(ThreadPoolExecutor threadPoolExec){
		this.threadPoolExec=threadPoolExec;
	}

	public void shutDown(){
		run=false;
	}

	public void run(){
		while(run){
			System.out.println("current no of threads in the pool="+threadPoolExec.getPoolSize()); //get current no of thread in the pool
			System.out.println("approx no of threads executing tasks="+threadPoolExec.getActiveCount()); //get current no of thread in the pool
			System.out.println("approx numb of tasks completed="+threadPoolExec.getCompletedTaskCount());



		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	}
}

class Worker implements Runnable {

    private String command;

    public Worker(String s){
        this.command=s;
    }

    @Override
    public void run() {
        System.out.println(command+" Started");
         processCommand();
        System.out.println(command+" finished its job");
    }

    private void processCommand() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return this.command;
    }
}
