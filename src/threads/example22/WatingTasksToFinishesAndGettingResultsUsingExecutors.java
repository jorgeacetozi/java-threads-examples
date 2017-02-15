package threads.example22;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * When dealing with Executors you do not have the thread reference, so invoking join() isn't the way.
 * To lock the caller thread, one strategy is to use the CountDownLatch, in which each task will
 * decrement the countdown when it finishes its job and the caller thread (main, in this case)
 * will be unlocked when the counter reaches zero. The main thread will be blocked in the method
 * countDownLatch.await()
 */

public class WatingTasksToFinishesAndGettingResultsUsingExecutors {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		System.out.println("Thread main started");
		
		CountDownLatch countDownLatch = new CountDownLatch(4);
		
		MyTask task1 = new MyTask(500l, countDownLatch);
		MyTask task2 = new MyTask(1000l, countDownLatch);
		MyTask task3 = new MyTask(2000l, countDownLatch);
		MyTask task4 = new MyTask(50l, countDownLatch);

		ExecutorService executorService = Executors.newFixedThreadPool(4);
		
		List<Future<Integer>> resultList = executorService.invokeAll(Arrays.asList(task1, task2, task3, task4));
		
		System.out.println("Locking main thread...");
		countDownLatch.await();
		System.out.println("Unlocking main thread...");
		
		// Now the main thread can proceed as the 4 threads finished their jobs (the countdown reached 0)
		// They are now available in the thread pool
		for (int i=0; i<4; i++) {
			System.out.println("Task" + i + " result: " + resultList.get(i).get());
		}
		
		executorService.shutdown();
	
		System.out.println("Thread main finished");
	}
}

class MyTask implements Callable<Integer> {
	private long sleep;
	private CountDownLatch countDownLatch;
	
	public MyTask(long sleep, CountDownLatch countDownLatch) {
		this.sleep = sleep;
		this.countDownLatch = countDownLatch;
	}
	
	public Integer call() {
		int sum=0;
		for (int i = 1; i <= 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Adding " + i);
			sum += i;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (countDownLatch != null) {
			System.out.println(Thread.currentThread().getName() + " counting down!");
			countDownLatch.countDown();
		}
		return sum;
	}
}
