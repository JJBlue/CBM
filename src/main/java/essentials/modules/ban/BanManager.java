package essentials.modules.ban;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.StringUtilities;
import essentials.utilities.TimeUtilities;

public class BanManager {
	private BanManager() {}
	
	public static boolean isPlayerBanned(UUID uuid) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime banUntil = config.getLocalDateTime("banUntil");
		return now.isBefore(banUntil);
	}
	
	public static void banPlayer(UUID uuid, String reason) {
		// 0000.01.01 00:00 -> ever ban
		banPlayer(uuid, reason, LocalDateTime.of(0, 1, 1, 0, 0));
	}
	
	public static void banPlayer(UUID uuid, String reason, String time) {
		banPlayer(uuid, reason, TimeUtilities.parseAddTime(time));
	}
	
	public static void banPlayer(UUID uuid, String reason, LocalDateTime localDateTime) {
		PlayerConfig config = PlayerManager.getPlayerConfig(uuid);
		config.set("banUntil", localDateTime);
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
		if(LocalDateTime.now().isAfter(time)) return null;
		return time;
	}
	
	public static String getMessage(UUID uuid) {
		String message = "$1\n$2"; //TODO message
		
		String reason = getReason(uuid);
		String banUntil = getMessageBanUntil(uuid);
		
		return StringUtilities.setArgs(message, reason != null ? reason : "", banUntil != null ? banUntil : "");
	}
	
	public static String getReason(UUID uuid) {
		return null; //TODO
	}
	
	public static String getMessageBanUntil(UUID uuid) {
		String banUntil = TimeUtilities.timeToString(LocalDateTime.now(), getBanUntil(uuid));
		if(banUntil == null) return null;
		
		String message = "$1"; //TODO message
		return StringUtilities.setArgs(message, banUntil);
	}
}
