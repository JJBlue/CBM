package cbm.utilities.permissions;

import org.bukkit.command.CommandSender;

public class PermissionHelper {
	private PermissionHelper() {}

	public static String getPermissionCommand(String... permission) {
		return getPermissionCommand(0, permission.length, permission);
	}

	public static String getPermissionCommand(int offset, int to, String... args) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(getPluginDefaultCommandPermission());
		
		for(int i = offset; i < to && i < args.length; i++) {
			builder.append(".");
			builder.append(args[i].toLowerCase());
		}
		
		return builder.toString();
	}
	
	public static String getPermission(String permission) {
		return getPluginDefaultCommand() + "." + permission;
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		if(sender == null) return false;
		return sender.hasPermission(getPermission(permission));
	}

	public static boolean hasCommandPermission(CommandSender sender, String... permission) {
		if(sender == null) return false;
		return sender.hasPermission(getPermissionCommand(0, permission.length, permission));
	}
	
	public static boolean hasCommandPermission(CommandSender sender, int offset, int length, String... args) {
		if(sender == null) return false;
		return sender.hasPermission(getPermissionCommand(offset, length, args));
	}

	public static String getPluginDefaultCommandPermission() {
		return getPluginDefaultCommand() + ".command";
	}
	
	public static String getPluginDefaultCommand() {
		return "cbm";
	}
}
