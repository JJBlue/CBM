package essentials.modules.chunk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChunkCommands implements CommandExecutor, TabCompleter {

	//TODO
	@Override
	public boolean onCommand(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {


		return false;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender arg0, @NotNull Command arg1, @NotNull String arg2, @NotNull String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
