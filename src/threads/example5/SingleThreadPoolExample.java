package threads.example5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Instantiates a thread pool with a single thread
 * All tasks are executed sequentially by the same thread
 */

public class SingleThreadPoolExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newSingleThreadExecutor();
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
