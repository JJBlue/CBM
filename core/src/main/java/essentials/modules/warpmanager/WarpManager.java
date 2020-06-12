package essentials.modules.warpmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import components.database.Datenbank;
import components.sql.SQLParser;
import essentials.config.database.SQLHelper;
import essentials.database.Databases;
import essentials.utilities.ItemStackJSONUtilities;
import essentials.utilities.conditions.Condition;
import essentials.utilities.permissions.PermissionHelper;

public class WarpManager {

	private static Map<String, Warp> warps = Collections.synchronizedMap(new HashMap<>());

	public static void load() {
		Datenbank database = Databases.getWorldDatabase();

		for (String s : SQLParser.getResources("sql/create.sql", WarpManager.class)) {
			try {
				database.execute(s);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		for (String s : SQLParser.getResources("sql/updateVersionTable.sql", WarpManager.class)) {
			try {
				database.executeQuery(s);
			} catch (SQLException e) {}
		}
	}

	public synchronized static void unload() {
		save();
		warps.clear();
	}

	public static void save() {
		synchronized (warps) {
			for (Warp warp : warps.values())
				warp.save();
		}
	}

	public synchronized static void addWarp(Warp warp) {
		if (warp == null || warp.name == null || warp.location == null) return;
		warps.put(warp.name, warp);

		try {
			Datenbank database = Databases.getWorldDatabase();
			PreparedStatement preparedStatement = database.prepareStatement("INSERT OR IGNORE INTO warps (name, location) VALUES (?, ?)");
			preparedStatement.setString(1, warp.name);
			preparedStatement.setString(2, SQLHelper.LocationToString(warp.location));
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		warp.save();
	}

	public synchronized static void deleteWarp(String warp) {
		Datenbank database = Databases.getWorldDatabase();

		try {
			PreparedStatement preparedStatement = database.prepareStatement("DELETE FROM warps WHERE name = ?");
			preparedStatement.setString(1, warp);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		warps.remove(warp);
	}

	public synchronized static void loadWarp(String warpName) {
		if (warps.containsKey(warpName)) return;

		Datenbank database = Databases.getWorldDatabase();

		try {
			PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM warps WHERE name = ?");
			preparedStatement.setString(1, warpName);
			ResultSet result = preparedStatement.executeQuery();

			if (result.next()) {
				Warp warp = new Warp(warpName);
				warp.autoLore = result.getBoolean("autoLore");
				warp.hasPermission = result.getBoolean("tPermission");
				warp.itemStack = (ItemStack) ItemStackJSONUtilities.toObject(result.getString("itemStack"));
				warp.condition = new Condition(result.getString("condition"), result.getString("executes"));
				warp.location = SQLHelper.StringToLocation(result.getString("location"));
				warp.showWithoutPermission = result.getBoolean("showWithoutPermission");
				warp.pos = result.getInt("pos");
				warp.saved = true;

				warps.put(warpName, warp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Warp> getWarps(int start, int length) {
		List<Warp> list = new LinkedList<>();

		Datenbank database = Databases.getWorldDatabase();

		try {
			PreparedStatement preparedStatement = database.prepareStatement("SELECT name FROM warps ORDER BY pos ASC LIMIT " + length + " OFFSET " + start);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				Warp warp = getWarp(result.getString("name"));
				list.add(warp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public static Warp getWarp(String warp) {
		loadWarp(warp);
		return warps.get(warp);
	}

	public static void teleport(Player player, String warp) {
		if (player == null) return;
		loadWarp(warp);
		teleport(player, warps.get(warp));
	}

	public static void teleport(Entity entity, Warp warp) {
		if (entity == null || warp == null) return;
		if (warp.hasPermission && !entity.hasPermission(PermissionHelper.getPermission("warp." + warp.name))) return;
		if(entity instanceof Player && !warp.checkAndExecute((Player) entity)) return;
		entity.teleport(warp.location);
	}

	public static void openInventory(Player player) {
		if (player == null) return;
		WarpInventory.openNewInventory(player);
	}
}
