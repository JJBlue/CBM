package cbm.utilities.permissions;

import org.bukkit.command.CommandSender;

public class PermissionHelper {
	private PermissionHelper() {}

	public static String getPermissionCommand(String permission) {
		return getPermission("command." + permission);
	}

	public static String getPermission(String permission) {
		return "cbm." + permission;
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		if(sender == null) return false;
		return sender.hasPermission(getPermission(permission));
	}

	public static boolean hasCommandPermission(CommandSender sender, String permission) {
		if(sender == null) return false;
		return sender.hasPermission(getPermissionCommand(permission));
	}

	public static String getPluginDefaultCommand() {
		return "cbm";
	}
}
