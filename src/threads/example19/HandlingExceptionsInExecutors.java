package threads.example19;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/*
 * As using Executors you cannot manipulate the thread object directly, it's needed to create an
 * instance of ThreadFactory to intercept the creation of the Thread and set the
 * UncaughtExceptionHandler directly to the Thread instance.
 */

public class HandlingExceptionsInExecutors {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("Thread main started");
		
		ExecutorService executorService = Executors.newFixedThreadPool(5, new MyExceptionHandlerThreadFactory());
		
		// if executorService.submit() is used instead of execute(), the Exception will rise when invoking future.get()
		// try {
		//   future.get();
	    // } catch (ExecutionException ex) {
	    //     ex.getCause().printStackTrace();
	    // }
		executorService.execute(new MyTask()); 
		
		executorService.shutdown();
		
		System.out.println("Thread main finished");
	}
}

class MyTask implements Runnable {
	
	public void run() {
		throw new RuntimeException();
	}
}

class MyExceptionHandler implements UncaughtExceptionHandler {
	
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Exception occoured in thread " + t.getName());
	}
}

class MyExceptionHandlerThreadFactory implements ThreadFactory {

	public Thread newThread(Runnable r) {
		Thread myThread = new Thread(r);
		myThread.setUncaughtExceptionHandler(new MyExceptionHandler());
		return myThread;
	}
}
