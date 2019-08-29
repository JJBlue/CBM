package essentials.permissions;

import org.bukkit.command.CommandSender;

public class PermissionHelper {
	private PermissionHelper() {}
	
	public static String getPermissionCommand(String permission) {
		return getPermission("command." + permission);
	}
	
	public static String getPermission(String permission) {
		return "all." + permission;
	}
	
	public static boolean hasPermission(CommandSender sender, String permission) {
		return sender.hasPermission(getPermission(permission));
	}
}
