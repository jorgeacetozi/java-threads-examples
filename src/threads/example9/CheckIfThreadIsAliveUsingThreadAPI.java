package threads.example9;

/*
 * Checks whether a thread is alive or not. A thread is said to be alive 
 * if it's started or waiting. An available thread in a thread pool
 * is on the waiting state, therefore it's alive
 * 
 *  The question is: when using the Executors, are we really interested 
 *  if threads are alive or if tasks are running? Think about it.
 */

public class CheckIfThreadIsAliveUsingThreadAPI {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("Thread main started");

		Thread thread1 = new Thread(new MyTask());
		Thread thread2 = new Thread(new MyTask());

		System.out.println("Thread1 is alive? " + thread1.isAlive());
		System.out.println("Thread2 is alive? " + thread2.isAlive());

		thread1.start();
		thread2.start();

		while (thread1.isAlive() || thread2.isAlive()) {
			System.out.println("Thread1 is alive? " + thread1.isAlive());
			System.out.println("Thread2 is alive? " + thread2.isAlive());
			Thread.sleep(500l);
		}

		System.out.println("Thread1 is alive? " + thread1.isAlive());
		System.out.println("Thread2 is alive? " + thread2.isAlive());
		
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("[" + Thread.currentThread().getName() + "] Message " + i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
