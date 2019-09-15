package essentials.money;

import org.bukkit.OfflinePlayer;

import essentials.plugins.Vault;
import net.milkbowl.vault.economy.EconomyResponse;

public class MoneyVault {
	
	private MoneyVault() {}
	
	public static void load() {
		Vault.setupEconomey();
	}
	
	public static boolean addMoney(OfflinePlayer offlinePlayer, double amount) {
		EconomyResponse r = Vault.getEconomy().depositPlayer(offlinePlayer, amount);
		return r.transactionSuccess();
	}
	
	public synchronized static boolean removeMoney(OfflinePlayer offlinePlayer, double amount) {
		EconomyResponse r = Vault.getEconomy().withdrawPlayer(offlinePlayer, amount);
		return r.transactionSuccess();
	}
	
	public synchronized static boolean removeMoney(OfflinePlayer offlinePlayer, double money, boolean check) {
		if(!check)
			return removeMoney(offlinePlayer, money);
		
		if(hasMoney(offlinePlayer, money))
			return removeMoney(offlinePlayer, money);
		
		return false;
	}
	
	public synchronized static boolean setMoney(OfflinePlayer offlinePlayer, double money) {
		double currentValue = getMoney(offlinePlayer);
		
		if(currentValue == money) return true;
		
		if(currentValue > money)
			return removeMoney(offlinePlayer, currentValue - money);
		return addMoney(offlinePlayer, money - currentValue);
	}
	
	public static double getMoney(OfflinePlayer offlinePlayer) {
		return Vault.getEconomy().getBalance(offlinePlayer);
	}
	
	public synchronized static boolean hasMoney(OfflinePlayer player, double money) {
		return Vault.getEconomy().has(player, money);
	}
}
