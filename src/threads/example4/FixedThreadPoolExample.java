package threads.example4;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Instantiates a fixed thread pool with 5 threads. When executing 5 tasks 
 * simultaneously, each task will be assigned to one of the five available threads
 * 
 * Change the thread pool size to 1 and execute the example again. Note that
 * now each task will be executed one by one on a sequential fashion
 * In this scenario, while first task is being executed the other 4 are queued
 */

public class FixedThreadPoolExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.execute(new MyTask());
		executorService.execute(new MyTask());
		executorService.execute(new MyTask());
		executorService.execute(new MyTask());
		executorService.execute(new MyTask());
		
		executorService.shutdown();
		
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] " + "Message " + i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
