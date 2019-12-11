package components.datenbank.async;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseExecutePrepareStatement implements DatabaseQuery {

	public final PreparedStatement preparedStatement;
	
	public DatabaseExecutePrepareStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	
	@Override
	public void run() {
		try {
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
