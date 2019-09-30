package components.datenbank.async;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import components.datenbank.Datenbank;

public class AsyncDatabase {
	private static List<DatabaseQuery> list = Collections.synchronizedList(new LinkedList<>());
	
	private static Thread thread;
	
	public static void add(Datenbank database, String query) {
		list.add(new DatabaseExecuteString(database, query));
		resume();
	}
	
	public static void execute(PreparedStatement preparedStatement) {
		list.add(new DatabaseExecutePrepareStatement(preparedStatement));
		resume();
	}
	
	public static void add(Runnable runnable) {
		list.add(new DatabaseRunnable(runnable));
	}
	
	public synchronized static void start() {
		if(thread != null) {
			resume();
			return;
		}
		
		thread = new Thread() {
			@Override
			public void run() {
				while(true) {
					
					DatabaseQuery query = list.remove(0);
					if(query != null)
						query.run();
					
					if(list.isEmpty()) {
						synchronized (this) {
							try {
								wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					
					if(thread != this)
						break;
				}
			}
		};
		
		thread.start();
	}
	
	public synchronized static void stop() {
		thread.interrupt();
		thread = null;
	}
	
	public synchronized static void resume() {
		if(thread == null)
			start();
		
		synchronized (thread) {
			thread.notify();
		}
	}
}
