package components.datenbank.async;

public class DatabaseRunnable implements DatabaseQuery {
	
	public final Runnable runnable;
	
	public DatabaseRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void run() {
		runnable.run();
	}
}
