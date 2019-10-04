package essentials.modules.commandonobject;

import components.datenbank.Datenbank;
import components.datenbank.async.AsyncDatabase;
import components.sql.SQLParser;
import essentials.database.Databases;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandOnBlock {
	private CommandOnBlock() {}

	private static final Map<Chunk, Map<Location, CoBBlock>> chunkBuffer = Collections.synchronizedMap(new HashMap<>());

	public static void load() {
		Datenbank database = Databases.getWorldDatabase();

		for (String s : SQLParser.getResources("sql/create.sql", CommandOnBlock.class))
			database.execute(s);
	}

	public static void unload() {
		save();
		chunkBuffer.clear();
	}

	public static void save() {
		synchronized (chunkBuffer) {
			for (Map<Location, CoBBlock> buffer : chunkBuffer.values()) {
				for (CoBBlock commandOnBlockInformation : buffer.values())
					commandOnBlockInformation.save();
			}
		}
	}

	public static boolean executeBlock(Player p, CoBAction action, Location location) {
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		if (commandOnBlock == null) return false;
		commandOnBlock.execute(p, action);
		return true;
	}

	public synchronized static CoBBlock getOrCreateCommandOnBlock(Location location) {
		CoBBlock coBBlock = getCommandOnBlock(location);

		if (coBBlock != null)
			return coBBlock;

		coBBlock = new CoBBlock(location);

		Map<Location, CoBBlock> map = chunkBuffer.get(location.getChunk());
		map.put(location, coBBlock);

		return coBBlock;
	}

	public synchronized static CoBBlock getCommandOnBlock(Location location) {
		Chunk chunk = location.getChunk();

		if (!chunkBuffer.containsKey(chunk))
			loadChunk(chunk);

		Map<Location, CoBBlock> map = chunkBuffer.get(chunk);
		return map.get(location);
	}

	public synchronized static void loadChunk(Chunk chunk) {
		if (chunkBuffer.containsKey(chunk)) return;

		PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/getBlocksInChunk.sql", CommandOnBlock.class));

		Location location = chunk.getBlock(0, 64, 0).getLocation();

		try {
			preparedStatement.setString(1, chunk.getWorld().getName());
			preparedStatement.setInt(2, location.getBlockX());
			preparedStatement.setInt(3, location.getBlockX() + 15);
			preparedStatement.setInt(4, location.getBlockY());
			preparedStatement.setInt(5, location.getBlockY() + 15);
			preparedStatement.setInt(6, location.getBlockZ());
			preparedStatement.setInt(7, location.getBlockZ() + 15);

			ResultSet resultSet = preparedStatement.executeQuery();

			Map<Location, CoBBlock> blocks = Collections.synchronizedMap(new HashMap<>());
			Location current = new Location(chunk.getWorld(), 0, 0, 0);

			while (resultSet.next()) {
				current.setX(resultSet.getInt("x"));
				current.setY(resultSet.getInt("y"));
				current.setZ(resultSet.getInt("z"));

				CoBBlock coBBlock;

				if (blocks.containsKey(current))
					coBBlock = blocks.get(current);
				else {
					Location cloneLocation = current.clone();
					coBBlock = new CoBBlock(cloneLocation);
					coBBlock.setID(resultSet.getInt("IDCommandOnBlock"));
					blocks.put(cloneLocation, coBBlock);
				}

				CoBCommandInfo commandInfo = new CoBCommandInfo();
				commandInfo.saved = true;
				commandInfo.action = CoBAction.valueOf(resultSet.getString("CoBAction"));
				commandInfo.command = resultSet.getString("Command");
				coBBlock.commands.add(commandInfo);
			}

			chunkBuffer.put(chunk, blocks);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized static void unloadChunk(Chunk chunk) {
		if (!chunkBuffer.containsKey(chunk)) return;

		Map<Location, CoBBlock> map = chunkBuffer.get(chunk);
		if (map != null) {
			synchronized (map) {
				for (CoBBlock commandOnBlockInformation : map.values())
					commandOnBlockInformation.save();
			}
		}

		chunkBuffer.remove(chunk);
	}

	public static List<CoBCommandInfo> getCommandInfos(Location location) {
		CoBBlock commandOnBlock = getCommandOnBlock(location);
		if (commandOnBlock == null) return null;
		return commandOnBlock.getCommandInfos();
	}

	public synchronized static void clear(final Location location) {
		if(!chunkBuffer.containsKey(location.getChunk())) return;
		
		Map<Location, ?> map = chunkBuffer.get(location.getChunk());
		
		if(map.remove(location) != null) {
			AsyncDatabase.add(() -> {
				PreparedStatement preparedStatement = Databases.getWorldDatabase().prepareStatement(SQLParser.getResource("sql/deleteBlock.sql", CommandOnBlock.class));
				try {
					preparedStatement.setString(1, location.getWorld().getName());
					preparedStatement.setInt(2, location.getBlockX());
					preparedStatement.setInt(3, location.getBlockY());
					preparedStatement.setInt(4, location.getBlockZ());
					preparedStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	}

	public static void addCommand(Location location, CoBAction action, String command) {
		if (command == null) return;

		CoBBlock commandOnBlock = getOrCreateCommandOnBlock(location);
		commandOnBlock.addCommand(action, command);
	}

	public static void removeCommand(Location location, CoBAction action, String command) {
		if (command == null) return;

		CoBBlock commandOnBlock = getCommandOnBlock(location);
		if (commandOnBlock == null) return;
		commandOnBlock.removeCommand(action, command);
	}
}
