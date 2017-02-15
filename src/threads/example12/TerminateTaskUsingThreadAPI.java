package threads.example12;

/*
 * Terminates a TASK (not the Thread itself) by using the Thread.interrupt() method on the thread.
 * Note that the task will not terminate immediately as terminating the task depends on
 * the execution reaches the line "if (Thread.interrupted())"
 * 
 * As in the Thread API a thread basically executes only one task (it's not
 * reusable), once the task is finished the thread will finish as well.
 * Again: note that the here the TASK is being terminated, not the thread.
 */

public class TerminateTaskUsingThreadAPI {

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
		for (int i = 0; i < 100000; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);

			if (Thread.interrupted()) {
				System.out.println("This thread was interruped by someone calling thisThread.interrupt()");
				System.out.println("Cancelling task running in thread " + Thread.currentThread().getName());
				System.out.println("Just illustrating that after Thread.interrupted() call, JVM reset the interrupted value to: " + Thread.interrupted());
				break;
			}
		}
	}
}
