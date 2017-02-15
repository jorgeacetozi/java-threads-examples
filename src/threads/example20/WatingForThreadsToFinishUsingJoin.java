package threads.example20;

/*
 * Like the wait() / notify() strategy used in one of the previous examples, join() allows
 * the caller thread to wait until the target thread finishes.
 * 
 * If the target thread is already done, join() returns immediately to the caller thread 
 * (that's what happens with thread4 in this example).
 */

public class WatingForThreadsToFinishUsingJoin {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Thread main started");
		
		Thread thread1 = new Thread(new MyTask(100l));	
		Thread thread2 = new Thread(new MyTask(200l));
		Thread thread3 = new Thread(new MyTask(300l));
		Thread thread4 = new Thread(new MyTask(50l));	
		thread1.setName("thread-1");
		thread2.setName("thread-2");
		thread3.setName("thread-3");
		thread4.setName("thread-4");
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread1.getName());
		thread1.join();
		System.out.println(thread1.getName() + " finished!");
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread2.getName());
		thread2.join();
		System.out.println(thread2.getName() + " finished!");
		
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread3.getName());
		thread3.join();
		System.out.println(thread3.getName() + " finished!");
		
		// As thread-4 already finished (smaller sleep time), the join call only immediately
		// returns the control to the caller thread
		System.out.println("[" + Thread.currentThread().getName() + "] waiting for " + thread4.getName());
		thread4.join();
		System.out.println(thread4.getName() + " finished!");
		
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	private long sleep;
	
	public MyTask(long sleep) {
		this.sleep = sleep;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
