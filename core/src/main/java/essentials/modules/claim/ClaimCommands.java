package essentials.modules.claim;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import essentials.economy.EconomyManager;
import essentials.language.LanguageConfig;

public class ClaimCommands implements TabExecutor {
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
				
				double notFreeBlocks = 255 * 16 * 16;
				notFreeBlocks -= ClaimManager.getFreeBlocks(player);
				double money = ClaimConfig.getConfiguration().getDouble("claim.costPerBlock") * notFreeBlocks;
				
				if(!EconomyManager.removeMoney(player.getUniqueId(), money, true)) {
					LanguageConfig.sendMessage(player, "money.notenough", money + "");
					break;
				}
				
				Chunk chunk = player.getLocation().getChunk();
				boolean claimed = ClaimRegion.claimChunk(player, chunk);
				
				if(!claimed) {
					EconomyManager.addMoney(player.getUniqueId(), money);
					LanguageConfig.sendMessage(player, "claim.errorclaiming");
					break;
				}
				
				LanguageConfig.sendMessage(player, "claim.success");
				
				break;
			}
			case "claim": {
				//TODO
				break;
			}
			case "unclaimchunk": {
				
				if(!(sender instanceof Player)) break;
				
				Player player = null;
				
				if(args.length == 2) {
					player = Bukkit.getPlayer(args[1]);
				} else if(sender instanceof Player) {
					player = (Player) sender;
				}
				
				if(player == null)
					break;
				
				Chunk chunk = player.getLocation().getChunk();
				boolean success = ClaimRegion.unclaimChunk(player, chunk);
				
				if(!success) {
					LanguageConfig.sendMessage(player, "claim.errorunclaiming");
					break;
				}
				
				ClaimManager.addFreeBlocks(player, 255 * 16 * 16);
				LanguageConfig.sendMessage(player, "claim.unclaimed");
				
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
			case "prizes": {
				double money = ClaimConfig.getConfiguration().getDouble("claim.costPerBlock");
				LanguageConfig.sendMessage(sender, "claim.prizes", money + "", (money * 255) + "", (money * 255 * 16 * 16) + "");
				
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
