package essentials.commands.commandonobject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import components.datenbank.Datenbank;
import components.sql.SQLParser;
import essentials.database.Databases;

public class CommandOnBlock {
	private CommandOnBlock() {}
	
	public static Map<Location, CoBBlock> buffer = Collections.synchronizedMap(new HashMap<>());
	public static Set<Location> bufferEmptyLocations = Collections.synchronizedSet(new HashSet<>());
	
	public static void load() {
		Datenbank database = Databases.getWorldDatabase();
		
		for(String s : SQLParser.getResources("sql/create.sql", CommandOnBlock.class))
			database.execute(s);
	}
	
	public static void unload() {
		save();
		buffer.clear();
		bufferEmptyLocations.clear();
	}
	
	public static void save() {
		synchronized (buffer) {
			for(CoBBlock commandOnBlockInformation : buffer.values())
				commandOnBlockInformation.save();
		}
	}
	
	public static void executeBlock(Player p, CoBAction action, Location location){
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		if(commandOnBlock == null) return;
		commandOnBlock.execute(p, action);
	}
	
	public synchronized static CoBBlock getOrCreateCommandOnBlock(Location location) {
		CoBBlock coBBlock = getCommandOnBlock(location);
		
		if(coBBlock != null)
			return coBBlock;
		
		coBBlock = new CoBBlock(location);
		coBBlock.commands = Collections.synchronizedList(new LinkedList<>());
		buffer.put(location, coBBlock);
		
		return coBBlock;
	}
	
	public synchronized static CoBBlock getCommandOnBlock(Location location) {
		if(buffer.containsKey(location))
			return buffer.get(location);
		
		if(bufferEmptyLocations.contains(location))
			return null;
		
		if(bufferEmptyLocations.size() > 1000)
			bufferEmptyLocations.clear();
		
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/getCommands.sql", CommandOnBlock.class));
		
		List<CoBCommandInfo> commands = null;
		CoBBlock block = null;
		
		try {
			preparedStatement.setString(1, location.getWorld().getName());
			preparedStatement.setInt(2, location.getBlockX());
			preparedStatement.setInt(3, location.getBlockY());
			preparedStatement.setInt(4, location.getBlockZ());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				if(block == null) {
					commands = Collections.synchronizedList(new LinkedList<>());
					block = new CoBBlock(location);
				}
				
				if(!block.isIDSet())
					block.setID(resultSet.getInt("IDCommandOnBlock"));
				
				CoBCommandInfo coBCommandInfo = new CoBCommandInfo();
				coBCommandInfo.saved = true;
				
				coBCommandInfo.command = resultSet.getString("Command");
				coBCommandInfo.action = CoBAction.valueOf(resultSet.getString("CoBAction"));
				commands.add(coBCommandInfo);
			}
			
			if(block != null) {
				block.commands = commands;
				buffer.put(location, block);
			} else
				bufferEmptyLocations.add(location);
			
			return block;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		bufferEmptyLocations.add(location);
		return null;
	}
	
	public static List<CoBCommandInfo> getCommandInfos(Location location) {
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		if(commandOnBlock == null) return null;
		return commandOnBlock.getCommandInfos();
	}
	
	public synchronized static void clear(Location location) {
		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/deleteBlock.sql", CommandOnBlock.class));
		try {
			preparedStatement.setString(1, location.getWorld().getName());
			preparedStatement.setInt(2, location.getBlockX());
			preparedStatement.setInt(3, location.getBlockY());
			preparedStatement.setInt(4, location.getBlockZ());
			preparedStatement.execute();
			buffer.remove(location);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCommand(Location location, String command) {
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		commandOnBlock.addCommand(command);
	}
	
	public static void removeCommand(Location location, String command) {
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		commandOnBlock.removeCommand(command);
	}
}
