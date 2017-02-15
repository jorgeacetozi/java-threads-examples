package threads.example23;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/*
 * Executes a TimerTask (which implements Runnable) on a Ttimer that will run the task on a 
 * SINGLE thread after 5 seconds
 * 
 * It's also possible to use Timer.scheduleAtFixedRate to run the task repeatedly. In this case
 * You may want to set the Timer constructor to true in order to to make it run as a Deamon
 * 
 * Also, blocks the main thread until the countdown reaches 0, that is, when the task is done
 * See the previous example to learn the CountDownLatch
 */

public class SchedulingTasksWithTimers {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Thread main started");
		CountDownLatch countDownLatch = new CountDownLatch(1);
		
		Timer timer = new Timer();
		System.out.println("Scheduling task to run after 5 seconds... " + new Date());
		timer.schedule(new MyTask(countDownLatch), 5000);
		
		countDownLatch.await();
		
		timer.cancel();
		System.out.println("Thread main finished");
	}
}

class MyTask extends TimerTask {
	
	private CountDownLatch countDownLatch;
	
	public MyTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		System.out.println("Executing the task at: " + new Date());
		countDownLatch.countDown();
	}
}
