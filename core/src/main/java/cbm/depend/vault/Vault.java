package cbm.depend.vault;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import cbm.main.Main;

public class Vault {
	private static Economy economy;
	private static Chat chat;
	private static Permission permission;
	
	public static boolean setupEconomy() {
		if(economy != null) return true;
		
		Server server = Main.getPlugin().getServer();
		
		if(server.getPluginManager().getPlugin("Vault") == null)
			return false;
		
		RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
		if(rsp == null)
			return false;
		
		economy = rsp.getProvider();
		return economy != null;
	}
	
	public static boolean setupChat() {
		if(chat != null) return true;
		
		RegisteredServiceProvider<Chat> rsp = getServicesManager().getRegistration(Chat.class);
		if(rsp == null)
			return false;
		chat = rsp.getProvider();
		return chat != null;
	}
	
	public static String getPrefix(Player player) {
		if(chat == null)
			setupChat();
		
		return chat.getPlayerPrefix(player);
	}
	
	public static String getSuffix(Player player) {
		if(chat == null)
			setupChat();
		
		return chat.getPlayerSuffix(player);
	}
	
	public static boolean setupPermissions() {
		if(permission != null) return true;
		
		RegisteredServiceProvider<Permission> rsp = getServicesManager().getRegistration(Permission.class);
		permission = rsp.getProvider();
		return permission != null;
	}
	
	public static Server getServer() {
		return Main.getPlugin().getServer();
	}
	
	public static ServicesManager getServicesManager() {
		return getServer().getServicesManager();
	}

	public static Economy getEconomy() {
		return economy;
	}

	public static Chat getChat() {
		return chat;
	}

	public static Permission getPermission() {
		return permission;
	}
}
