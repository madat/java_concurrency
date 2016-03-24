package com.concurrentpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FookJoinPoolExample {

	public static void main(String[] args) {

		File file=new File("E:/fileTests");
		long size=DirSize.sizeOf(file);
		System.out.println(size);


	}

}

class DirSize{

	private static class SizeOfFileTask extends RecursiveTask<Long>{
		File file;
		public  SizeOfFileTask(File file) {
			this.file=file;
		}

		@Override
		protected Long compute() {

			if(file.isFile()) return file.length();
			List<SizeOfFileTask> fileTasks=new ArrayList();
			File[] subFiles = file.listFiles();
			if(subFiles!=null){
               for(File file:subFiles){
                    SizeOfFileTask task=new SizeOfFileTask(file);
                    task.fork();
                    fileTasks.add(task);
               }
			}

			long size=0;
			for(SizeOfFileTask task:fileTasks){
				size +=task.join();
			}

			return size;
		}

	}

	 public static long sizeOf(File file) {
		    ForkJoinPool pool = new ForkJoinPool();

		    try {
		      return pool.invoke(new SizeOfFileTask(file));
		    } finally {
		      pool.shutdown();
		    }
		  }
}
