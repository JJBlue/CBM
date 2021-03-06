package cbm.modules.tablist;

public class TablistTimer {
	static Thread timer;
	static long sleepMilliSeconds = 5000;
	static boolean run;

	public synchronized static void start() {
		if (timer != null || sleepMilliSeconds == -1) return;

		run = true;

		timer = new Thread(() -> {
			while (run && sleepMilliSeconds != -1) {
				Tablist.nextTablist();
				Tablist.updateAllPlayers();

				try {
					Thread.sleep(sleepMilliSeconds);
				} catch (InterruptedException e) {}
			}

			run = false;
			timer = null;
		});
		timer.start();
	}

	public synchronized static void stop() {
		if (timer == null) return;

		run = false;
		if (timer != null)
			timer.interrupt();
	}

	public static void setSleep(long sleep) {
		if (sleep < 1000 && sleep != -1) return;
		sleepMilliSeconds = sleep;
	}
}
