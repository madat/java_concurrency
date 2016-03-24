package com.deadlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadDeadLock {



	public static void main(String[] args) throws InterruptedException, ExecutionException {

		RenderTask task1=new RenderTask("task1");
		RenderTask task2=new RenderTask("task2");
		// TODO Auto-generated method stub
		ExecutorService exec=Executors.newSingleThreadExecutor();
		Future res1 = exec.submit(task1);
		Future res2=exec.submit(task2);
		System.out.println((String)res1.get());
		System.out.println((String)res2.get());


	}

}


class RenderTask implements Callable{
	private String taskName;

	public RenderTask(String taskName){
		this.taskName=taskName;
	}

	public String call(){
		return taskName;
	}
}
