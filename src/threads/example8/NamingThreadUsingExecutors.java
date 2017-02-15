package threads.example8;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/*
 * The same as example7, except that here the threads are named 
 * in the thread pool implementing ThreadFactory interface
 * 
 * The same procedure can be done to instantiate Deamon Threads
 * that is, threads that executes while user threads exists
 */

public class NamingThreadUsingExecutors {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newFixedThreadPool(5, new NamedThreadFactory());
		List<Future<Integer>> returnedValues = executorService.invokeAll(Arrays.asList(
				new SumFirstNumbers(50), 
				new SumFirstNumbers(40),
				new SumFirstNumbers(30),
				new SumFirstNumbers(20),
				new SumFirstNumbers(10)));
		
		for (Future<Integer> value : returnedValues) {
			System.out.println(value.get());
		}
		
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
			System.out.println("[" + Thread.currentThread().getName() +  "] Adding " + i);
			sum += i;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return sum;
	}
}

class NamedThreadFactory implements ThreadFactory {
	private String threadName="xuxa-thread-";
	private static int count = 0;

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(threadName + count++);
		return t;
	}
}
