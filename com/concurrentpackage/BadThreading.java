package com.concurrentpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BadThreading {

	public static void main(String[] args) {

		File file=new File("E:/fileTests");
		long size=DirSizeNotWork.sizeOf(file);
		System.out.println(size);


	}

}

/**
 * lets say we have used a fixedThreaPool of size 2, lets say we have three folders in a dir and files inside them.
 * the available two threads will be busy with dir1,dir2. Then when we call submit on executors for a file inside a  dir,
 * there will not be any available threads. Hence it will wait forever.
 *
 * @author user
 *
 */
class DirSizeNotWork {

	  private static class SizeOfFileCallable implements Callable<Long> {

	    private final File file;
	    private final ExecutorService executor;

	    public SizeOfFileCallable(final File file, final ExecutorService executor) {
	      this.file = Objects.requireNonNull(file);
	      this.executor = Objects.requireNonNull(executor);
	    }

	    @Override
	    public Long call() throws Exception {
	    	System.out.println("getting call");
	      long size = 0;

	      if (file.isFile()) {
	        size = file.length();
	        System.out.println("size="+size+" with file="+file.getAbsolutePath());
	      } else {
	        final List<Future<Long>> futures = new ArrayList<>();
	        for (final File child : file.listFiles()) {
              System.out.println("puting this file on submit="+child.getAbsolutePath());
	          futures.add(executor.submit(new SizeOfFileCallable(child, executor)));
	        }

	       for (final Future<Long> future : futures) {
	          size += future.get();
	        }
	      }

	      return size;
	    }
	  }

	  public static <T> long sizeOf(final File file) {
	    final int threads = Runtime.getRuntime().availableProcessors();
	    final ExecutorService executor = Executors.newFixedThreadPool(2);
	    try {
	      return executor.submit(new SizeOfFileCallable(file, executor)).get();
	    } catch (final Exception e) {
	      throw new RuntimeException("Failed to calculate the dir size", e);
	    } finally {
	      executor.shutdown();
	    }
	  }

//
	//  private DirSize() {}

	}
