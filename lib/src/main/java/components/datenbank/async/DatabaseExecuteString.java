package components.datenbank.async;

import components.datenbank.Datenbank;

public class DatabaseExecuteString implements DatabaseQuery {
	public final Datenbank database;
	public final String execute;
	
	public DatabaseExecuteString(Datenbank database, String execute) {
		this.database = database;
		this.execute = execute;
	}

	@Override
	public void run() {
		database.execute(execute);
	}
	
}
