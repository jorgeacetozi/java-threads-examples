package threads.example24;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Schedules a Runnable task using a ScheduledExecutorService.schedule()
 * 
 * It's also possible to use scheduleAtFixedRate or scheduleAtFixedDelay to run the task repeatedly
 * using the thread pool
 * 
 * Also, blocks the main thread until the countdown reaches 0, that is, the task is done
 * See the previous examples to learn the CountDownLatch
 */

public class SchedulingTasksWithScheduledThreadPool {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Thread main started");
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
		
		System.out.println("Scheduling task to run after 5 seconds... " + new Date());
		scheduledExecutorService.schedule(new MyTask(countDownLatch), 5, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new MyTask(countDownLatch), 5, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new MyTask(countDownLatch), 5, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new MyTask(countDownLatch), 5, TimeUnit.SECONDS);
		scheduledExecutorService.schedule(new MyTask(countDownLatch), 5, TimeUnit.SECONDS);

		countDownLatch.await();
		
		scheduledExecutorService.shutdown();
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	
	private CountDownLatch countDownLatch;
	
	public MyTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	public void run() {
		System.out.println("Executing the task at: " + new Date());
		countDownLatch.countDown();
	}
}
