package threads.example3;

/*
 * Returning values using Thread or Runnable is not straightforward. 
 * One way is controlling the threads execution by using wait() and notify() methods
 */

public class ReturnValueUsingThreadAPI {

	public static void main(String[] args) throws InterruptedException {
		
		MyTask task1 = new MyTask(200l);
		Thread t1 = new Thread(task1);
		t1.start();
		
		MyTask task2 = new MyTask(200l);
		Thread t2 = new Thread(task2);
		t2.start();
		
		// if task1 is still executing, then it puts the caller thread [main] to wait
		// when it finishes, then notify() is called to wake up the caller thread
		// same for task 2
		System.out.println("Sum: " + task1.getSum());
		System.out.println("Sum: " + task2.getSum());
	}
}

class MyTask implements Runnable {
	private Long sleep;
	private volatile int sum;
	private boolean done = false;
	
	public MyTask(Long sleep) {
		this.sleep = sleep;
	}
	
	public void run() {
		for (int i = 1; i <= 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);
			this.sum += i;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		done = true;
		synchronized (this) {
			this.notifyAll();
		}
	}
	
	public int getSum() throws InterruptedException {
		synchronized (this) {
			if (!done) {
				System.out.println(Thread.currentThread().getName()+ " is waiting");
				this.wait();
			}
		}
		return this.sum;
	}
}
