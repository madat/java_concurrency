package com.concurrentpackage;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerBlockingQueue {

	public static void main(String[] args) {
       BlockingQueue<Integer> bq=new ArrayBlockingQueue<Integer>(2); //define a blockingqueue that can hold only two elements max
       Producer prod=new Producer(bq);
       Consumer cons=new Consumer(bq);

       new Thread(prod).start();
       new Thread(cons).start();

	}

}


class Producer implements Runnable{

	BlockingQueue<Integer> bq;

	public Producer(BlockingQueue<Integer> bq){
          this.bq=bq;
	}

	@Override
	public void run() {
		try{
		Random rand = new Random();
		int num=0;
		num=rand.nextInt(50);
		bq.add(num);
		System.out.println("added "+num+ " in the queue");

		Thread.sleep(3000);

		num=rand.nextInt(50);
		bq.add(num);

		System.out.println("added "+num+ " in the queue");

		Thread.sleep(3000);

		num=rand.nextInt(50);
		bq.add(num);
		System.out.println("added "+num+ " in the queue");

		}catch(InterruptedException ex){

		}
	}

}

class Consumer implements Runnable{

	BlockingQueue<Integer> bq;

	public Consumer(BlockingQueue<Integer> bq){
          this.bq=bq;
	}

	@Override
	public void run() {
		try {
			System.out.println("conmsumed no from queue="+bq.take());
			System.out.println("conmsumed no from queue="+bq.take());
			System.out.println("conmsumed no from queue="+bq.take());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

