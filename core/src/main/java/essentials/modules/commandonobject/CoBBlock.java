package essentials.modules.commandonobject;

import components.datenbank.async.AsyncDatabase;
import components.sql.SQLParser;
import essentials.database.Databases;
import essentials.utilities.commands.CommandAusfuehren;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class CoBBlock {
	private int ID;
	private boolean isIDSet = false;
	private final Location location;
	public final List<CoBCommandInfo> commands;

	public CoBBlock(Location location) {
		this.location = location;
		commands = Collections.synchronizedList(new ArrayList<>());
	}

	public Location getLocation() {
		return location;
	}

	public synchronized List<CoBCommandInfo> getCommandInfos() {
		return commands;
	}
	
	public synchronized List<String> getCommands(CoBAction action) {
		List<String> list = new LinkedList<>();
		
		commands.forEach(coi -> {
			if(coi.action.equals(action))
				list.add(coi.command);
		});
		
		return list;
	}

	public void addCommand(String command) {
		commands.add(new CoBCommandInfo(CoBAction.EVERYTIME, command));
	}

	public void addCommand(CoBAction action, String command) {
		commands.add(new CoBCommandInfo(action, command));
	}

	public void removeCommand(String command) {
		synchronized (commands) {
			Iterator<CoBCommandInfo> iterator = commands.iterator();
			while (iterator.hasNext()) {
				CoBCommandInfo ci = iterator.next();

				if (ci.command.equals(command)) {
					iterator.remove();
					removeCommand(ci);
					break;
				}
			}
		}
	}

	public void removeCommand(CoBAction action, String command) {
		synchronized (commands) {
			Iterator<CoBCommandInfo> iterator = commands.iterator();
			while (iterator.hasNext()) {
				CoBCommandInfo ci = iterator.next();

				if (ci.command.equals(command) && ci.action.equals(action)) {
					iterator.remove();
					removeCommand(ci);
					break;
				}
			}
		}
	}

	public void removeCommand(final CoBCommandInfo ci) {
		if (isIDSet) {
			AsyncDatabase.add(() -> {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/removeCommand.sql", CoBBlock.class));
				try {
					preparedStatement.setInt(1, ID);
					preparedStatement.setString(2, ci.action.name());
					preparedStatement.setString(3, ci.command);
					preparedStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} else {
			AsyncDatabase.add(() -> {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/removeCommandLocation.sql", CoBBlock.class));
				try {
					preparedStatement.setString(1, location.getWorld().getName());
					preparedStatement.setInt(2, location.getBlockX());
					preparedStatement.setInt(3, location.getBlockY());
					preparedStatement.setInt(4, location.getBlockZ());
					preparedStatement.setString(5, ci.action.name());
					preparedStatement.setString(6, ci.command);
					preparedStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	public synchronized int save() {
		int result = 0;

		if (location == null) return result;

		if (!isIDSet) {
			PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/addBlock.sql", CoBBlock.class));
			try {
				preparedStatement.setString(1, location.getWorld().getName());
				preparedStatement.setInt(2, location.getBlockX());
				preparedStatement.setInt(3, location.getBlockY());
				preparedStatement.setInt(4, location.getBlockZ());
				preparedStatement.execute();
				result = 1; //Saved but must be new loaded to get the ID
			} catch (SQLException e) {
				e.printStackTrace();
				result = -1;
				return result;
			}
		}

		commands.forEach(ci -> {
			if (ci.saved) return;

			int index = 1;
			PreparedStatement preparedStatement;

			if (!isIDSet) {
				preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/addCommandToBlock.sql", CoBBlock.class));
				try {
					preparedStatement.setString(index++, location.getWorld().getName());
					preparedStatement.setInt(index++, location.getBlockX());
					preparedStatement.setInt(index++, location.getBlockY());
					preparedStatement.setInt(index++, location.getBlockZ());
				} catch (SQLException e) {
					e.printStackTrace();
					return;
				}

			} else {
				preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/addCommand.sql", CoBBlock.class));
				try {
					preparedStatement.setInt(index++, ID);
				} catch (SQLException e) {
					e.printStackTrace();
					return;
				}
			}

			try {
				preparedStatement.setString(index++, ci.action.name());
				preparedStatement.setString(index++, ci.command);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});

		return result;
	}
	
	public void saveAsync() {
		AsyncDatabase.add(() -> save());
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		if (isIDSet) return;
		isIDSet = true;
		this.ID = ID;
	}

	public boolean isIDSet() {
		return isIDSet;
	}

	public void execute(Player p, CoBAction action) {
		if (commands.isEmpty()) return;

		commands.forEach(ci -> {
			if (action.isIn(ci.action))
				CommandAusfuehren.Command(p, ci.command);
		});
	}
}
