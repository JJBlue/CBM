package essentials.modules.claim;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import essentials.economy.EconomyManager;

public class ClaimCommands implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length <= 0) return true;
		
		switch (args[0].toLowerCase()) {
			case "claimchunk": { //claimchunk <Player>
				
				Player player = null;
				
				if(args.length == 2) {
					player = Bukkit.getPlayer(args[1]);
				} else if(sender instanceof Player) {
					player = (Player) sender;
				}
				
				if(player == null) break;
				
				//TODO freeBlocks
				double money = ClaimConfig.getConfiguration().getDouble("claim.costPerBlock") * 255 * 16 * 16;
				
				if(EconomyManager.removeMoney(player.getUniqueId(), money, true)) {
					Chunk chunk = player.getLocation().getChunk();
					ClaimRegion.claimChunk(player, chunk);
				}
				
				break;
			}
			case "claim": {
				//TODO
				break;
			}
			case "unclaimchunk": {
				
				if(!(sender instanceof Player)) break;
				Player player = (Player) sender;
				
				//TODO
				Chunk chunk = player.getLocation().getChunk();
				ClaimRegion.unclaimChunk(chunk);
				
				break;
			}
			case "unclaim": {
				//TODO
				break;
			}
			case "listclaims": {
				//TODO
				break;
			}
			case "isclaimed": {
				//TODO
				break;
			}
		}
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("claimchunk");
			returnArguments.add("unclaimchunk");

		} else {
			for (Player player : Bukkit.getOnlinePlayers())
				returnArguments.add(player.getName());
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

}
