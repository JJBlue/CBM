package essentials.warpmanager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import components.datenbank.Datenbank;
import essentials.database.Databases;
import essentials.player.PlayerSQLHelper;
import essentials.utilities.ConfigUtilities;

public class Warp {
	final String name;
	Location location;
	ItemStack itemStack;
	
	boolean autoLore;
	boolean hasPermission;
	boolean showWithoutPermission;
	int cost; //TODO Not work currently
	int pos;
	
	boolean saved = false;
	
	public Warp(String name) {
		this.name = name;
	}
	
	public void save() {
		if(saved) return;
		saved = true;
		
		Datenbank database = Databases.getWorldDatabase();
		PreparedStatement preparedStatement = database.prepareStatement("UPDATE warps SET location = ?, itemStack = ?, tPermission = ?, showWithoutPermission = ?, autoLore = ?, pos = ? WHERE name = ?");
		try {
			preparedStatement.setString(1, PlayerSQLHelper.LocationToString(location));
			preparedStatement.setString(2, ConfigUtilities.toString(itemStack));
			preparedStatement.setBoolean(3, hasPermission);
			preparedStatement.setBoolean(4, showWithoutPermission);
			preparedStatement.setBoolean(5, autoLore);
			preparedStatement.setInt(6, pos);
			preparedStatement.setString(7, name);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ItemStack getResultItemStack() {
		if(!autoLore)
			return itemStack;
		else {
			ItemStack is = itemStack.clone();
			ItemMeta itemMeta = is.getItemMeta();
			
			List<String> lore = itemMeta.getLore() == null ? new LinkedList<>() : itemMeta.getLore();
			lore.add(location.getWorld().getName());
			lore.add(location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ());
			
			itemMeta.setLore(lore);
			is.setItemMeta(itemMeta);
			return is;
		}
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
	
	public boolean isHasPermission() {
		return hasPermission;
	}
	
	public void setHasPermission(boolean hasPermission) {
		this.hasPermission = hasPermission;
		saved = false;
	}
	
	public boolean isShowWithoutPermission() {
		return showWithoutPermission;
	}
	
	public void setShowWithoutPermission(boolean showWithoutPermission) {
		this.showWithoutPermission = showWithoutPermission;
		saved = false;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
		saved = false;
	}
	
	public String getName() {
		return name;
	}
}
