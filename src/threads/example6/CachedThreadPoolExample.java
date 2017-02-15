package threads.example6;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Instantiates a thread pool that allocates as many threads as tasks are given
 * 
 * Create more tasks and execute it again. Note that there are no queued tasks as the
 * pool grows when there are no available threads to run the tasks
 */

public class CachedThreadPoolExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newCachedThreadPool();
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
