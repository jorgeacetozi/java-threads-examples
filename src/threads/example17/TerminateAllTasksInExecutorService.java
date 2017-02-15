package threads.example17;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Terminates all tasks assigned to ExecutorService by using ExecutorService.shutdownNow() method
 * This method terminates immediately the running tasks and retrieves the list of tasks that hasn't 
 * started yet
 * 
 * As in this example the thread pool has size 5 and 8 tasks where assigned, then it must return 3 
 * tasks that were queued
 */

public class TerminateAllTasksInExecutorService {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Thread main started");

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.submit(new SumFirstNumbers(400000));
		executorService.submit(new SumFirstNumbers(300000));
		executorService.submit(new SumFirstNumbers(200000));
		executorService.submit(new SumFirstNumbers(100000));
		executorService.submit(new SumFirstNumbers(100000));
		executorService.submit(new SumFirstNumbers(100000));
		executorService.submit(new SumFirstNumbers(100000));
		executorService.submit(new SumFirstNumbers(100000));
		
		Thread.sleep(1000l);

		List<Runnable> queudTasks = executorService.shutdownNow();
		
		System.out.println("Tasks that hasn't started: " + queudTasks.size());
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
			try {
				System.out.println("[" + Thread.currentThread().getName() + "] Adding " + i);
				sum += i;
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				System.out.println("Interrupting the thread while it was sleeping...");
				break;
			}
		}
		return sum;
	}
}
