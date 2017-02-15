package threads.example16;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Terminates a blocking TASK by using the Future.cancel(true) method.
 * 
 * If you'd like only to terminate the task after the sleep finishes, instead of 'break' in the
 * catch block, you can set a flag and check for this flag after. Something like:
 * 
 * catch() {
 *   interruptedTask=true;
 * }
 * 
 * if (Thread.interrupted() || interruptedTask) 
 *   break;
 */

public class TerminateBlockedTaskUsingThreadAPI {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Thread main started");

		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> result = executorService.submit(new MyTask());

		Thread.sleep(500l);
		result.cancel(true);
		
		executorService.shutdown();
		
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	public void run() {
		for (int i = 0; i < 10000; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);

			try {
				Thread.sleep(1l);
			} catch (InterruptedException e) {
				System.out.println("Interrupting the thread while it was sleeping...");
				break;
			}
		}
	}
}
