package essentials.economy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.config.MainConfig;
import essentials.config.MainConfigEnum;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;

public class EconomyManager {
	
	public synchronized static boolean addMoney(UUID uuid, double money) {
		if(MainConfig.getConfiguration().getBoolean(MainConfigEnum.useVaultEconomy.value))
			return EconomyVault.addMoney(Bukkit.getOfflinePlayer(uuid), money);
		return setMoney(uuid, getMoney(uuid) + money);
	}

	public synchronized static boolean removeMoney(UUID uuid, double money) {
		if(MainConfig.getConfiguration().getBoolean(MainConfigEnum.useVaultEconomy.value))
			return EconomyVault.removeMoney(Bukkit.getOfflinePlayer(uuid), money);
		return setMoney(uuid, getMoney(uuid) - money);
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

	public synchronized static boolean setMoney(UUID uuid, double money) {
		if(MainConfig.getConfiguration().getBoolean(MainConfigEnum.useVaultEconomy.value))
			return EconomyVault.setMoney(Bukkit.getOfflinePlayer(uuid), money);
		
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		config.set(PlayerConfigKey.balance.toString(), money);
		
		Player player = Bukkit.getPlayer(uuid);
		if(player == null || !player.isOnline())
			config.save();
		
		return true;
	}

	public synchronized static double getMoney(UUID uuid) {
		if(MainConfig.getConfiguration().getBoolean(MainConfigEnum.useVaultEconomy.value))
			return EconomyVault.getMoney(Bukkit.getOfflinePlayer(uuid));
		
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		return config.getDouble(PlayerConfigKey.balance.toString());
	}

	public synchronized static boolean hasMoney(UUID player, double money) {
		return getMoney(player) >= money;
	}
}
