package components.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncExecute {
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	
	public static void setAmount(int amount) {
		ExecutorService oldExecutor = executor;
		executor = Executors.newFixedThreadPool(amount);
		oldExecutor.shutdown();
	}
	
	public static void put(Runnable runnable) {
		executor.execute(runnable);
	}
}