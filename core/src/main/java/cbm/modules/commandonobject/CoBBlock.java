package cbm.modules.commandonobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cbm.database.Databases;
import cbm.utilities.commands.CommandAusfuehren;
import components.database.async.AsyncDatabase;
import components.sql.SQLParser;

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
		commands.removeIf(ci -> {
			if(ci.command.equals(command)) {
				removeCommand(ci);
				return true;
			}
			return false;
		});
	}

	public void removeCommand(CoBAction action, String command) {
		commands.removeIf(ci -> {
			if(ci.command.equals(command) && ci.action.equals(action)) {
				removeCommand(ci);
				return true;
			}
			return false;
		});
	}

	public synchronized void removeCommand(final CoBCommandInfo ci) {
		if (!isIDSet || !ci.saved) return;
		
		AsyncDatabase.add(() -> {
			try {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/removeCommand.sql", CoBBlock.class));
				
				preparedStatement.setInt(1, ID);
				preparedStatement.setString(2, ci.action.name());
				preparedStatement.setString(3, ci.command);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	public synchronized boolean save() {
		if (location == null) return false;

		if (!isIDSet) {
			try {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().getConnection().prepareStatement(SQLParser.getResource("sql/addBlock.sql", CoBBlock.class), Statement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setString(1, location.getWorld().getName());
				preparedStatement.setInt(2, location.getBlockX());
				preparedStatement.setInt(3, location.getBlockY());
				preparedStatement.setInt(4, location.getBlockZ());
				int rows = preparedStatement.executeUpdate();
				if(rows <= 0) return false;
				
				ResultSet result = preparedStatement.getGeneratedKeys();
				while(result.next()) {
					setID(result.getInt(1));
				}
				
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		commands.forEach(ci -> {
			if (ci.saved) return;
			
			try {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/addCommand.sql", CoBBlock.class));
				
				int index = 1;
				preparedStatement.setInt(index++, ID);
				preparedStatement.setString(index++, ci.action.name());
				preparedStatement.setString(index++, ci.command);
				int rows = preparedStatement.executeUpdate();
				if(rows <= 0) return;
				
				ci.saved = true;
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		});

		return true;
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
