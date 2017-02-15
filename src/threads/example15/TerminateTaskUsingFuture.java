package threads.example15;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Terminates a TASK by using the Future.cancel(true) call.
 * Note that the task will not terminate immediately (that's why we put main to sleep for a while)
 * Comment the cancel() invocation to see the task finishing its job.
 * 
 * Internally, it uses the same mechanism in one of the previous examples using Thread.interrupt() method.
 * 
 * After the task is cancelled, the thread is again available in the thread pool.
 */

public class TerminateTaskUsingFuture {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newCachedThreadPool();

		SumFirstNumbers sumTask = new SumFirstNumbers(500000);
		Future<Integer> result = executorService.submit(sumTask);

		Thread.sleep(500l);
		result.cancel(true);
		
		executorService.shutdown();

		System.out.println("Thread main finished");
	}
}

class SumFirstNumbers implements Callable<Integer> {
	private int n;
	
	public SumFirstNumbers(int n) {
		this.n = n;
	}
	
	public Integer call() {
		int sum = 0;
		for (int i = 1; i <= n; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Adding " + i);
			sum += i;
			
			if (Thread.interrupted()) {
				System.out.println("Cancelling the task...");
				return -1;
			}
		}
		return sum;
	}
}
