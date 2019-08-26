package essentials.commands.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.commands.NameTag.nt;
import essentials.commands.armorstand.ArmorstandCommands;
import essentials.commands.commandspy.CommandSpy;
import essentials.commands.skull.Skullitem;
import essentials.commands.teleport.teleportCommand;
import essentials.commands.trade.TradeCommands;
import essentials.config.MainConfig;
import essentials.listeners.CommandsEvents;
import essentials.listeners.FlyThrowBlocks.FTB;
import essentials.listeners.MapPaint.MapPaint;
import essentials.listeners.chair.chair;
import essentials.main.Deop;
import essentials.main.Join;
import essentials.main.Main;
import essentials.permissions.PermissionHelper;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import essentials.player.SudoPlayer;
import essentials.pluginmanager.DisableEnable;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.ItemUtilies;
import essentials.utilities.PlayerUtilities;
import essentials.utilities.StringUtilities;

public class MainCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1) return true;
		Player p = null;
		
		if(sender instanceof Player)
			p = (Player)sender;	
				
		if(args.length == 0)
			return false;
		
		args[0] = args[0].toLowerCase();
		if(!PermissionHelper.hasPermission(sender, args[0])) return true;
		
		switch (args[0]) {
			case "afk":
				
				Player p1 = null;
				
				if(args.length == 1) p1 = p;
				else p1 = Bukkit.getPlayer(args[0]);
				
				if(p1 == null) return true;
				
				if(CommandsEvents.afk.contains(p1)) {
					CommandsEvents.afk.remove(p1);
					Bukkit.broadcastMessage(p1.getName() + " ist nicht mehr afk");
				} else {
					CommandsEvents.afk.add(p1);
					Bukkit.broadcastMessage(p1.getName() + " ist jetzt afk");
				}
				
				break;
				
			case "armorstand":
				
				return ArmorstandCommands.armorstandCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
		
			case "blockname":
				
				if(sender instanceof Player)
					p.sendMessage(p.getLocation().getBlock().getType() + "");
			
				break;
				
			case "book":
				
				return bookCommand.bookcommand.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "burn":
				
				if(args.length < 2) return true;
				p1 = (Player) Bukkit.getPlayer(args[1]);
				if(p1 == null) return true;
				p1.setFireTicks(Integer.parseInt(args[2]));
				
				break;
				
			case "broadcast":
				
				String msg = null;
				for(int i = 1; i < args.length; i++){
					if(msg == null)
						msg = args[i];
					else
						msg = msg + " " + args[i];
				}
				
				if(msg != null)
					Bukkit.broadcastMessage(msg);
				
				break;
				
			case "commandspy":
				
				return CommandSpy.commandSpy.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "feed":
				
				p1 = null;
					
				if(args.length >= 2) {
					p1 = Bukkit.getPlayer(args[1]);
					if(p1 == null) return true;
					
					if(args.length == 2)
						p1.setFoodLevel(20);
					else
						try{
							p1.setFoodLevel(Integer.parseInt(args[2]));
						}catch(NumberFormatException nfe){}
					
					sender.sendMessage(args[1] +  " wurdest gefuettert");
				} else {
					if(p == null) return true;
					
					p.setFoodLevel(20);
					sender.sendMessage("Du wurdest gefuettert");
				}
				
				break;
				
			case "fly":
				
				p1 = null;
				
				if(args.length >= 1) p1 = Bukkit.getPlayer(args[1]);
				else p1 = p;
				
				if(p1 == null) return true;
				p1.setAllowFlight(true);
				
				break;
				
			case "head":
				if(p == null) return true;
				
				ItemStack is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				ItemStack tmp = p.getInventory().getHelmet();
				p.getInventory().setHelmet(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "jump":
				
				if(p == null) return true;
				
				Block b = p.getTargetBlock(null, Integer.MAX_VALUE);
				if(b == null) return true;
				Location l = b.getLocation();
				
				boolean top = false;
				while(top == false && l.getY() <= 256) {
					l.setY(l.getY() + 1);
					
					if(l == null || l.getBlock().getType().equals(Material.AIR))
						top = true;
				}
				
				p.teleport(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));
				
				break;
				
			case "join":
				
				return Join.onCommand(sender, cmd, cmdLabel, args);
				
			case "chestplate":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				tmp = p.getInventory().getChestplate();
				p.getInventory().setChestplate(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "legging":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				tmp = p.getInventory().getLeggings();
				p.getInventory().setLeggings(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "lightning":
				
				if(args.length == 1) {
					if(p == null) return true;
					
					b = p.getTargetBlock((Set<Material>) null, Integer.MAX_VALUE);
					if(b == null) return true;
					
					p.getWorld().spawnEntity(b.getLocation(), EntityType.LIGHTNING);
				} else if(args.length > 1) {
					p1 = Bukkit.getPlayer(args[0]);
					if(p1 == null) return true;
					
					p1.getWorld().spawnEntity(p1.getLocation(), EntityType.LIGHTNING);
				}
				
				break;
				
			case "boot":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				tmp = p.getInventory().getBoots();
				p.getInventory().setBoots(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "heal":
				
				if(args.length >= 2) {
					p1 = Bukkit.getPlayer(args[1]);
					if(p1 == null) return true;
					
					if(args.length == 2)
						p1.setHealth(p1.getHealthScale());
					else
						try{
							p1.setHealth(Integer.parseInt(args[2]));
						}catch(NumberFormatException nfe){}
					
					sender.sendMessage(args[1] +  " wurde geheilt");
				} else {
					if(p == null) return true;
					
					p.setHealth(p.getHealthScale());
					sender.sendMessage("Du wurdest geheilt");
				}
				
				break;
				
			case "hide":
				
				p1 = null;
				if(args.length <= 1) p1 = p;
				else Bukkit.getPlayer(args[1]);
				
				if(p1 == null) return true;
				
				for(Player p2 : Bukkit.getOnlinePlayers()){
					if(CommandsEvents.hide.contains(p))
						p2.showPlayer(Main.getPlugin(), p);
					else
						p2.hidePlayer(Main.getPlugin(), p);
				}
				
				if(CommandsEvents.hide.contains(p)){
					CommandsEvents.hide.remove(p);
					sender.sendMessage(p1.getName() + " sind jetzt unsichtbar");
				} else {
					CommandsEvents.hide.add(p);
					sender.sendMessage(p1.getName() + " sind jetzt sichtbar");
				}
				
				break;
				
			case "itemdb":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				p.sendMessage("Item: §4" + is.getType());
				
				break;
				
			case "inventory":
				
				inventorySee.inventorySee.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
				
			case "more":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				if(args.length == 1)
					is.setAmount(64);
				else if(args.length == 2)
					try{
						is.setAmount(Integer.parseInt(args[0]));
					}catch(NumberFormatException nfe){
						sender.sendMessage("§4" + args[0] + " ist keine Zahl");
					}
				
				break;
				
			case "mute":
				
				if(args.length >= 2){
					p1 = PlayerUtilities.getOfflinePlayer(args[0]).getPlayer();
					if(p1 == null) return true;
					
					PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p1);
					
					if(!playerConfig.getBoolean(PlayerConfigKey.tMute)){
						playerConfig.set(PlayerConfigKey.tMute, true);
						sender.sendMessage(p1.getName() + " wurde gemutet");
						p1.sendMessage("Du wurdest gemutet");
					} else {
						playerConfig.set(PlayerConfigKey.tMute, false);
						sender.sendMessage(p1.getName() + " wurde entmutet");
						p1.sendMessage("Du wurdest entmutet");
					}
				} else {
					sender.sendMessage("Es sind folgende Spieler gemutet:");
					
					//TODO
				}
				
				break;
				
			case "nametag":
				
				if(args.length < 2) break;
				
				if(args[1].equalsIgnoreCase("true"))
					nt.setNameTag(true);
				else if(args[1].equalsIgnoreCase("false"))
					nt.setNameTag(false);
				
				break;
				
			case "near":
				
				if(p == null) return true;
				
				for(Entity e : p.getNearbyEntities(500, 500, 500)){
					if(e instanceof Player)
						sender.sendMessage(e.getName() + ": " + e.getLocation().distance(p.getLocation()));
				}
				
				break;
				
			case "op":
				
				Deop.deopCommand.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
				break;
				
			case "paint":
				
				if(args.length < 2) break;
				MapPaint.addPainting(p, args[1]);
				
				break;
				
			case "pluginmanager":
				
				return DisableEnable.disableEnable.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "reload":
				
				MainConfig.reload();
				Main.Loading();
				sender.sendMessage("§aReload complete!");
				
				break;
				
			case "silent":
				
				Bukkit.dispatchCommand(new SudoPlayer(sender).setSilentOutputMessage(true), StringUtilities.arrayToString(Arrays.copyOfRange(args, 1, args.length)));
				
				break;
				
			case "sit":
				
				if(p == null) return false;
				
				if(chair.toggle(p))
					sender.sendMessage("Chair: ON");
				else
					sender.sendMessage("Chair: OFF");
				
				break;
				
			case "sign":
				
				return SignCommands.signCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "speed":
				
				if(args.length == 2) {
					if(p == null) return true;
					
					if(p.isFlying())
						p.setFlySpeed(flo(Double.parseDouble(args[1]), sender));
					else
						p.setWalkSpeed(flo(Double.parseDouble(args[1]), sender));
					
					sender.sendMessage("Normal Speed == 2");
				} else if(args.length >= 3) {
					p1 = Bukkit.getPlayer(args[2]);
					if(p1 == null) return true;
					
					if(args[1].equalsIgnoreCase("walk"))
						p1.setWalkSpeed(flo(Double.parseDouble(args[0]), sender));
					else if(args[1].equalsIgnoreCase("fly"))
						p1.setFlySpeed(flo(Double.parseDouble(args[0]), sender));
					
					sender.sendMessage("Normal Speed == 2");
				}
				
				break;
				
			case "skull":
				
				Skullitem.skullitem.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
				
			case "sudo":
				
				if(args[1].equalsIgnoreCase("§4Server")) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtilities.arrayToString(Arrays.copyOfRange(args, 2, args.length)));
				} else {
					Player sudoPlayer = Bukkit.getPlayer(args[1]);
					if(sudoPlayer == null) return true;
					
					if(args.length < 3) return true;
					PluginCommand command = Bukkit.getServer().getPluginCommand(args[2]);
					if(command != null)
						command.execute(new SudoPlayer(sender, sudoPlayer), args[2], Arrays.copyOfRange(args, 3, args.length));
				}
				
				break;
				
			case "teleport":
				
				teleportCommand.teleport.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
				break;
				
			case "trade":
				
				return TradeCommands.tradeCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "uuid":
				if(args.length < 2) break;
				
				OfflinePlayer player = PlayerUtilities.getOfflinePlayer(args[1]);
				UUID uuid = player.getUniqueId();
				sender.sendMessage(uuid.toString());
				
				break;
				
			case "wallghost":
				
				if(args.length <= 1) {
					if(FTB.toogle(p))
						sender.sendMessage("WallGhost: Player <" + p.getName() + "> hinzugefuegt");
					else
						sender.sendMessage("WallGhost: Player <" + p.getName() + "> entfernt");
				} else {
					Player p2 = Bukkit.getPlayer(args[1]);
					if(p2 == null) break;
					
					if(FTB.toogle(p2))
						sender.sendMessage("WallGhost: Player <" + args[1] + "> hinzugefuegt");
					else
						sender.sendMessage("WallGhost: Player <" + args[1] + "> entfernt");
				}
				
				break;
				
			case "random":
				if(args.length < 3) break;
				
				int amount = Integer.parseInt(args[1]);
				LinkedList<Integer> pl = new LinkedList<>();
				String[] players;
				if(!args[2].contains(",")) {
					players = new String[1];
					players[0] = args[2];
				}else {
					players = args[2].split(",");
				}
				
				if(players.length >= amount && amount >= 0) {
					Random rnd = new Random();
					int i = 0;
					while(i < amount) {
						int z = rnd.nextInt(players.length);
						if(!pl.contains(z)){
							pl.add(z);
							Player player1 = Bukkit.getPlayer(players[z]);
							if(player1 != null)
								player1.sendMessage("You were selected!");
							i++;
						}
					}
					
					for(int y = 0; y < players.length; y++){
						if(!pl.contains(y)) {
							Player player1 = Bukkit.getPlayer(players[y]);
							if(player1 != null)
								player1.sendMessage("§4You were not selected!");
						}
					}
				}
				
				break;
				
			case "repair":
				
				if(args.length <= 1) {
					if(p == null) return true;
					is = p.getInventory().getItemInMainHand();
				} else {
					p1 = Bukkit.getPlayer(args[1]);
					is = p1.getInventory().getItemInMainHand();
				}
				
				if(is == null || is.getType().equals(Material.AIR)) return true;
				ItemUtilies.setDurability(is, 0);
				
				break;
				
			default:
				break;
		}
		
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if(args.length < 1) return null;
		
		List<String> returnArguments = new LinkedList<>();
		
		if(args.length == 1) {
			returnArguments.add("afk");
			returnArguments.add("armorstand");
			returnArguments.add("blockname");
			returnArguments.add("boot");
			returnArguments.add("book");
			returnArguments.add("burn");
			returnArguments.add("broadcast");
			returnArguments.add("commandspy");
			returnArguments.add("chestplate");
			returnArguments.add("fly");
			returnArguments.add("feed");
			returnArguments.add("head");
			returnArguments.add("heal");
			returnArguments.add("hide");
			returnArguments.add("itemdb");
			returnArguments.add("inventory");
			returnArguments.add("legging");
			returnArguments.add("join");
			returnArguments.add("jump");
			returnArguments.add("more");
			returnArguments.add("mute");
			returnArguments.add("nametag");
			returnArguments.add("near");
			returnArguments.add("paint");
			returnArguments.add("pluginmanager");
			returnArguments.add("random");
			returnArguments.add("repair");
			returnArguments.add("reload");
			returnArguments.add("silent");
			returnArguments.add("sit");
			returnArguments.add("sign");
			returnArguments.add("skull");
			returnArguments.add("sudo");
			returnArguments.add("speed");
			returnArguments.add("teleport");
			returnArguments.add("trade");
			returnArguments.add("uuid");
			returnArguments.add("wallGhost");
			
			Iterator<String> it = returnArguments.iterator();
			while(it.hasNext()) {
				if(!PermissionHelper.hasPermission(sender, it.next()))
					it.remove();
			}
			
		} else { // I know that I tested here the permission never -> But then he know the first arguement, I think he knows the rest...
			switch (args[0]) {
				case "burn":
				case "more":
				case "feed":
				case "heal":
					if(args.length == 2) {
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if(args.length == 3) returnArguments.add("[<amount>]");
					
					break;
					
				case "armorstand":
					
					return ArmorstandCommands.armorstandCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "book":
					
					return bookCommand.bookcommand.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "commandspy":
					
					return CommandSpy.commandSpy.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "silent":
					
					returnArguments = BukkitUtilities.getAvailableCommands(sender);
					break;
					
				case "sign":
					
					return SignCommands.signCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "speed":
					
					for(Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());
					
					if(args.length == 2) {
						returnArguments.add("walk");
						returnArguments.add("fly");
					}
					
					break;
					
				case "skull":
					
					return Skullitem.skullitem.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "sudo":
					
					switch (args.length) {
						case 2:
							
							for(Player player : Bukkit.getOnlinePlayers())
								returnArguments.add(player.getName());
							
							returnArguments.add("§4Server");
							
							break;
							
						case 3:
							
							returnArguments = BukkitUtilities.getAvailableCommands(sender);
							break;

						default:
							
							PluginCommand command = Bukkit.getPluginCommand(args[2]);
							if(command == null) break;
							TabCompleter tabCompleter = command.getTabCompleter();
							if(tabCompleter == null) break;
							return tabCompleter.onTabComplete(sender, command, args[2], Arrays.copyOfRange(args, 3, args.length));
					}
					
					break;
					
				case "teleport":
					
					return teleportCommand.teleport.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "inventory":
					
					return inventorySee.inventorySee.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
			
				case "join":
					returnArguments.add("help");
					break;
					
				case "nametag":
					returnArguments.add("true");
					returnArguments.add("false");
					
					break;
					
				case "paint":
					if(args.length == 2) {
						File dir = new File(MainConfig.getDataFolder() + "picture");
						
						for(File file : dir.listFiles()) {
							if(file.isDirectory()) continue;
							
							returnArguments.add(file.getName());
						}
					}
					else if(args.length == 3) returnArguments.add("<X>");
					else if(args.length == 4) returnArguments.add("<Y>");
					
					break;
					
				case "pluginmanager":
					
					return DisableEnable.disableEnable.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "trade":
					
					return TradeCommands.tradeCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "random":
					if(args.length == 2) returnArguments.add("<Amount>");
					else if(args.length == 3) returnArguments.add("<Player,Player...>");
					
					break;
				
				case "hide":
				case "lightning":
				case "clear":
				case "fly":
				case "uuid":
				case "wallGhost":
					for(Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.startsWith(args[args.length - 1]));
		
		returnArguments.sort((s1, s2) -> {
			return s1.compareTo(s2);
		});
		
		return returnArguments;
	}
	
	private static float flo(double i, CommandSender sender){
		float f = (float) 0.1;
		
		if(i >= -10 && i <= 10)
			f = (float) (i / 10);
		else
			sender.sendMessage("Es darf nur eine Zahl zwischen -10 bist 10 sein");
		
		return f;
	}
}
