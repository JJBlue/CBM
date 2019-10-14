package essentials.modules.ban;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.StringUtilities;
import essentials.utilities.TimeUtilities;

public class BanManager {
	private BanManager() {}
	
	public static void load() {
		ConfigurationSection configuration = getConfigurationSection();
		
		configuration.addDefault("message", "You were §4banned$1$2");
		configuration.addDefault("reason", "\n§fReason: §6$1");
		configuration.addDefault("until", "\n§fTime until unbanned: §6$1");
	}
	
	public static boolean isPlayerBanned(UUID uuid) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		LocalDateTime banUntil = config.getLocalDateTime("banUntil");
		if(banUntil == null) return false;
		if(banUntil.isEqual(LocalDateTime.of(1, 1, 1, 0, 0))) return true;
		return LocalDateTime.now().isBefore(banUntil);
	}
	
	public static void banPlayer(UUID uuid, String reason) {
		// 0000.01.01 00:00 -> ever ban
		banPlayer(uuid, reason, LocalDateTime.of(1, 1, 1, 0, 0));
	}
	
	public static void banPlayer(UUID uuid, String reason, String time) {
		System.out.println(TimeUtilities.parseAddTime(time));
		
		banPlayer(uuid, reason, TimeUtilities.parseAddTime(time));
	}
	
	public static void banPlayer(UUID uuid, String reason, LocalDateTime localDateTime) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		config.set("banUntil", localDateTime);
		config.set("banReason", reason);
		config.saveAsync();
		
		Player player = Bukkit.getPlayer(uuid);
		if(player != null)
			player.kickPlayer(getMessage(uuid));
	}
	
	public static void unbanPlayer(UUID uuid) {
		banPlayer(uuid, null, (LocalDateTime) null);
	}
	
	public static LocalDateTime getBanUntil(UUID uuid) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		LocalDateTime time = config.getLocalDateTime("banUntil");
		if(time == null || LocalDateTime.now().isAfter(time)) return null;
		return time;
	}
	
	public static String getMessage(UUID uuid) {
		String message = getConfigurationSection().getString("message");
		if(message == null) return null;
		
		String reason = getReason(uuid);
		String banUntil = getMessageBanUntil(uuid);
		
		return StringUtilities.setArgs(message, reason != null ? reason : "", banUntil != null ? banUntil : "");
	}
	
	public static String getReason(UUID uuid) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		return getReason(config);
	}
	
	public static String getReason(PlayerConfig config) {
		String reason = getConfigurationSection().getString("reason");
		if(reason == null) return null;
		String banReason = config.getString("banReason");
		if(banReason == null) return null;
		return StringUtilities.setArgs(reason, banReason);
	}
	
	public static String getMessageBanUntil(UUID uuid) {
		String banUntil = TimeUtilities.timeToString(LocalDateTime.now(), getBanUntil(uuid));
		if(banUntil == null) return null;
		
		String message = getConfigurationSection().getString("until");
		if(message == null) return null;
		return StringUtilities.setArgs(message, banUntil);
	}
	
	public static ConfigurationSection getConfigurationSection() {
		return PlayersYMLConfig.getConfigurationSectionOrCreate("ban");
	}
}
