package cbm.modules.commands.commands;

import org.bukkit.command.CommandExecutor;

import cbm.modules.commands.CommandManager;

public class gc {
	public static void register() {
		CommandExecutor executor = (sender, cmd, cmdLabel, args) -> {
			System.gc();
			return true;
		};
		CommandManager.register("gc", CommandManager.getTabExecutor(executor));
	}
}
