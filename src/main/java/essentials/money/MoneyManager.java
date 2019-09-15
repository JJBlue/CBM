package essentials.money;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;

public class MoneyManager {
	
	public synchronized static void addMoney(UUID uuid, double money) {
		setMoney(uuid, getMoney(uuid) + money);
	}

	public synchronized static void removeMoney(UUID uuid, double money) {
		setMoney(uuid, getMoney(uuid) - money);
	}
	
	public synchronized static boolean removeMoney(UUID uuid, double money, boolean check) {
		if(!check) {
			removeMoney(uuid, money);
			return true;
		}
		
		if(hasMoney(uuid, money)) {
			setMoney(uuid, getMoney(uuid) - money);
			return true;
		}
		
		return false;
	}

	public synchronized static void setMoney(UUID uuid, double money) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		config.set(PlayerConfigKey.balance.toString(), money);
		
		Player player = Bukkit.getPlayer(uuid);
		if(player == null || !player.isOnline())
			config.save();
	}

	public synchronized static double getMoney(UUID uuid) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		return config.getLong(PlayerConfigKey.balance.toString());
	}

	public synchronized static boolean hasMoney(UUID player, double money) {
		return getMoney(player) >= money;
	}
}
