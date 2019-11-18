package essentials.config.database;

import java.sql.ResultSet;
import java.util.List;

public interface AbstractDatabaseConfig<I extends Object> {
	public void unload();
	public void unload(I uuid);
	public void unloadAll();
	public boolean hasColumn(String column, ResultSet resultSet);

	public List<String> getColumns();
}
