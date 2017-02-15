package threads.example11;

/*
 * Terminates a TASK (not the Thread itself) by using a flag inside the task. 
 * Note that the task will not terminate immediately. Comment the cancel() 
 * invocation to see the task finishing its job.
 * 
 * As in the Thread API a thread basically executes only one task (it's not
 * reusable), once the task is finished the thread will finish as well.
 * Again: note that the here the TASK is being terminated, not the thread.
 */

public class TerminateTaskUsingFlagThreadAPI {

	public static void main(String[] args) {

		System.out.println("Thread main started");

		MyTask task1 = new MyTask();
		Thread thread1 = new Thread(task1);
		thread1.start();

		task1.cancel();

		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	private volatile boolean cancelTask;

	public MyTask() {
		this.cancelTask = false;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);
			try {
				Thread.sleep(200l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronized (this) {
				if (this.cancelTask) {
					System.out.println("Cancelling task running in thread "
							+ Thread.currentThread().getName());
					break;
				}
			}
		}
	}

	public void cancel() {
		synchronized (this) {
			this.cancelTask = true;
		}
	}
}
