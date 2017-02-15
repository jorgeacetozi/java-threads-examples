package threads.example18;

import java.lang.Thread.UncaughtExceptionHandler;

/*
 * Instantiates an UncaughtExceptionHandler to handle exceptions that may rise in
 * all the threads of the application (including those in a Thread Pool)
 * 
 * It's also possible to set an UncaughtExceptionHandler directly to an specific thread object.
 * 
 * You may also use a combination of both strategies as specific handlers overrides the global one
 */

public class HandlingExceptionsInThreadAPI {
	
	public static void main(String[] args) {
		
		System.out.println("Thread main started");
		
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Handling the Exception occured in thread " + t.getName());
			}
		});
		
		new MyTask().start();
		new MyTask().start();	
		
		System.out.println("Thread main finished");
	}
}

class MyTask extends Thread {
	public void run() {
		throw new RuntimeException();
	}
}
