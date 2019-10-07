package essentials.modules.player;

public class test {
	public static void main(String[] args) {
		start();
	}
	
	private static Thread thread;
	private static boolean running;
	private static long sleep;
	
	public synchronized static void start() {
		if(thread != null) return;
		
		running = true;
		
		sleep = 500_000; //in nano, default value
		
		thread = new Thread(() -> {
			long time = 0;
			
			while(running) {
				if(System.nanoTime() - time < sleep) continue;
				time = System.nanoTime();
			}
			thread = null;
		});
		
		thread.start();
	}
}
