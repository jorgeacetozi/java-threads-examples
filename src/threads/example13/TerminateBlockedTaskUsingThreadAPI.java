package threads.example13;

/*
 * Terminates a TASK (not the Thread itself) by using the Thread.interrupt() method on the thread
 * while its sleeping (blocked state).
 * 
 * If you'd like to only terminate the task after the sleep finishes, instead of 'break' in the
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

	public static void main(String[] args) {

		System.out.println("Thread main started");

		MyTask task1 = new MyTask();
		Thread thread1 = new Thread(task1);
		thread1.start();
		thread1.interrupt();

		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
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
