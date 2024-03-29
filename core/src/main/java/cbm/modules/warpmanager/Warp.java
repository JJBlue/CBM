package cbm.modules.warpmanager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cbm.config.database.SQLHelper;
import cbm.database.Databases;
import cbm.utilities.ItemStackJSONUtilities;
import cbm.utilities.conditions.Condition;
import cbm.utilities.permissions.PermissionHelper;
import components.database.Database;
import components.json.JSONObject;

public class Warp {
	final String name;
	Location location;
	ItemStack itemStack;

	boolean autoLore;
	boolean hasPermission;
	boolean showWithoutPermission;
	int pos;
	
	Condition condition;

	boolean saved = false;

	public Warp(String name) {
		this.name = name;
	}

	public void save() {
		if (saved) return;
		saved = true;

		Database database = Databases.getWorldDatabase();
		
		try {
			PreparedStatement preparedStatement = database.prepareStatement("UPDATE warps SET location = ?, itemStack = ?, tPermission = ?, showWithoutPermission = ?, autoLore = ?, pos = ?, condition = ?, executes = ? WHERE name = ?");
		
			preparedStatement.setString(1, SQLHelper.LocationToString(location));
			if (itemStack != null && !itemStack.getType().equals(Material.AIR))
				preparedStatement.setString(2, ItemStackJSONUtilities.toString(itemStack));
			else
				preparedStatement.setString(2, null);
			
			preparedStatement.setBoolean(3, hasPermission);
			preparedStatement.setBoolean(4, showWithoutPermission);
			preparedStatement.setBoolean(5, autoLore);
			preparedStatement.setInt(6, pos);
			preparedStatement.setString(7, condition != null ? condition.getConditionToString() : null);
			preparedStatement.setString(8, condition != null ? condition.getExecuteToString() : null);
			preparedStatement.setString(9, name);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkAndExecute(Player player) {
		return condition == null ? true : condition.checkAndExecute(player);
	}

	public ItemStack getResultItemStack() {
		if (itemStack == null)
			return null;

		if (!autoLore)
			return itemStack;
		
		ItemStack is = itemStack.clone();
		ItemMeta itemMeta = is.getItemMeta();

		List<String> lore = itemMeta.getLore() == null ? new LinkedList<>() : itemMeta.getLore();
		lore.add(location.getWorld().getName());
		lore.add(location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
		lore.add("");

		if (hasPermission)
			lore.add("Permission: " + PermissionHelper.getPermission("warp." + name));

		if(condition != null && condition.hasCondition())
			lore.addAll(condition.getConditiontoList());
		
		itemMeta.setLore(lore);
		is.setItemMeta(itemMeta);
		return is;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
		saved = false;
	}

	public boolean isAutoLore() {
		return autoLore;
	}

	public void setAutoLore(boolean autoLore) {
		this.autoLore = autoLore;
		saved = false;
	}

	public boolean hasPermission() {
		return hasPermission;
	}

	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
		saved = false;
	}

	public String getPermission() {
		return PermissionHelper.getPermission("warp." + name);
	}

	public boolean isShowWithoutPermission() {
		return showWithoutPermission;
	}

	public void setShowWithoutPermission(boolean showWithoutPermission) {
		this.showWithoutPermission = showWithoutPermission;
		saved = false;
	}
	
	public void setCost(int cost) {
		if(condition == null)
			condition = new Condition(new JSONObject(), new JSONObject());
		
		condition.getCondition().add("money", cost);
		saved = false;
	}

	public String getName() {
		return name;
	}
}
