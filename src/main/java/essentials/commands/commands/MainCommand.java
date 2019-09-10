package essentials.commands.commands;

import java.time.LocalDateTime;
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
import org.bukkit.World;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import essentials.commands.NameTag.nt;
import essentials.commands.armorstand.ArmorstandCommands;
import essentials.commands.commandonobject.CoBCommands;
import essentials.commands.commandspy.CommandSpy;
import essentials.commands.skull.Skullitem;
import essentials.commands.teleport.teleportCommand;
import essentials.commands.trade.TradeCommands;
import essentials.config.MainConfig;
import essentials.language.LanguageConfig;
import essentials.listeners.MainListener;
import essentials.listeners.FlyThrowBlocks.FTB;
import essentials.listeners.MapPaint.MPCommand;
import essentials.listeners.chair.chair;
import essentials.main.Deop;
import essentials.main.Join;
import essentials.main.Main;
import essentials.permissions.PermissionHelper;
import essentials.player.PlayerConfig;
import essentials.player.PlayerConfigKey;
import essentials.player.PlayerManager;
import essentials.player.sudoplayer.SudoPlayerInterface;
import essentials.player.sudoplayer.SudoPlayerManager;
import essentials.pluginmanager.DisableEnable;
import essentials.status.SystemStatus;
import essentials.timer.TimerCommand;
import essentials.updater.SpigotPluginUpdater;
import essentials.updater.UpdaterCommand;
import essentials.utilities.BukkitUtilities;
import essentials.utilities.ItemUtilies;
import essentials.utilities.PlayerUtilities;
import essentials.utilities.StringUtilities;
import essentials.utilities.TimeUtilities;
import essentials.warpmanager.warpCommands;

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
		if(!sender.hasPermission(PermissionHelper.getPermissionCommand(args[0]))) return true;
		
		switch (args[0]) {
			case "afk":
				{
					Player p1 = null;
					
					if(args.length == 1) p1 = p;
					else p1 = Bukkit.getPlayer(args[0]);
					
					if(p1 == null) return true;
					
					PlayerConfig config = PlayerManager.getPlayerConfig(p1);
					
					boolean nV = !(config.containsLoadedKey("afk") && config.getBoolean("afk"));
					config.setTmp("afk", nV);
					
					if(!nV)
						Bukkit.broadcastMessage(LanguageConfig.getString("afk.noLongerAfk", p1.getName()));
					else
						Bukkit.broadcastMessage(LanguageConfig.getString("afk.isNowAfk", p1.getName()));
					
					break;
				}
			case "armorstand":
				
				return ArmorstandCommands.armorstandCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
		
			case "blockname":
				
				if(sender instanceof Player)
					p.sendMessage(p.getLocation().getBlock().getType() + "");
			
				break;
				
			case "book":
				
				return bookCommand.bookcommand.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "boot":
				
				if(p == null) return true;
				
				ItemStack is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				ItemStack tmp = p.getInventory().getBoots();
				p.getInventory().setBoots(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "burn":
				{
					if(args.length < 2) return true;
					Player p1 = (Player) Bukkit.getPlayer(args[1]);
					if(p1 == null) return true;
					p1.setFireTicks(Integer.parseInt(args[2]));
					
					break;
				}
			case "broadcast":
				{
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
				}
			case "chestplate":
				{
					if(p == null) return true;
					
					is = p.getInventory().getItemInMainHand();
					if(is == null || is.getType().equals(Material.AIR)) return true;
					
					tmp = p.getInventory().getChestplate();
					p.getInventory().setChestplate(is);
					p.getInventory().setItemInMainHand(tmp);
					
					break;
				}
			case "cob":
				
				return CoBCommands.commandOnBlock.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "commandspy":
				
				return CommandSpy.commandSpy.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "feed":
				{
					Player p1 = null;
						
					if(args.length >= 2) {
						p1 = Bukkit.getPlayer(args[1]);
						if(p1 == null) return true;
						
						if(args.length == 2)
							p1.setFoodLevel(20);
						else
							try{
								p1.setFoodLevel(Integer.parseInt(args[2]));
							}catch(NumberFormatException nfe){}
						
						LanguageConfig.sendMessage(sender, "feed.feed-Player", args[1]);
					} else {
						if(p == null) return true;
						
						p.setFoodLevel(20);
						LanguageConfig.sendMessage(sender, "feed.feed");
					}
					
					break;
				}
			case "fly":
				{
					Player p1 = null;
					
					if(args.length >= 1) p1 = Bukkit.getPlayer(args[1]);
					else p1 = p;
					
					if(p1 == null) return true;
					p1.setAllowFlight(true);
					
					break;
				}
			case "god":
				{
					Player p1 = null;
					
					if(args.length >= 1) p1 = Bukkit.getPlayer(args[1]);
					else p1 = p;
					
					if(p1 == null) break;
					
					PlayerConfig config = PlayerManager.getPlayerConfig(p1);
					boolean nV = !config.getBoolean(PlayerConfigKey.tGod);
					config.set(PlayerConfigKey.tGod, nV);
					
					if(args.length >= 1) {
						if(nV) {
							LanguageConfig.sendMessage(sender, "god.enabled-Player", p1.getName());
							LanguageConfig.sendMessage(p1, "god.enabled");
						} else {
							LanguageConfig.sendMessage(sender, "god.disabled-Player", p1.getName());
							LanguageConfig.sendMessage(p1, "god.disabled");
						}
					} else {
						if(nV)
							LanguageConfig.sendMessage(sender, "god.enabled");
						else
							LanguageConfig.sendMessage(sender, "god.disabled");
					}
				}
			case "head":
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				tmp = p.getInventory().getHelmet();
				p.getInventory().setHelmet(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "heal":
				
				if(args.length >= 2) {
					Player p1 = Bukkit.getPlayer(args[1]);
					if(p1 == null) return true;
					
					if(args.length == 2)
						p1.setHealth(p1.getHealthScale());
					else
						try{
							p1.setHealth(Integer.parseInt(args[2]));
						}catch(NumberFormatException nfe){}
					
					LanguageConfig.sendMessage(sender, "heal.heal-Player", args[1]);
				} else {
					if(p == null) return true;
					
					p.setHealth(p.getHealthScale());
					LanguageConfig.sendMessage(sender, "heal.heal");
				}
				
				break;
				
			case "hide":
				{
					Player p1 = null;
					if(args.length <= 1) p1 = p;
					else Bukkit.getPlayer(args[1]);
					
					if(p1 == null) return true;
					
					for(Player p2 : Bukkit.getOnlinePlayers()){
						if(MainListener.hide.contains(p))
							p2.showPlayer(Main.getPlugin(), p);
						else
							p2.hidePlayer(Main.getPlugin(), p);
					}
					
					if(MainListener.hide.contains(p)){
						MainListener.hide.remove(p);
						LanguageConfig.sendMessage(sender, "hide.invisible-Player", p1.getName());
					} else {
						MainListener.hide.add(p);
						LanguageConfig.sendMessage(sender, "hide.visible-Player", p1.getName());
					}
					
					break;
				}
			case "itemdb":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				p.sendMessage("Item: §4" + is.getType());
				
				break;
				
			case "info":
				
				if(args.length < 2) break;
				Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
				if(plugin == null) break;
				
				PluginDescriptionFile description = plugin.getDescription();
				
				LanguageConfig.sendMessage(
					sender, "info.info-Plugin",
					description.getFullName(),
					description.getName(),
					description.getPrefix(),
					description.getVersion(),
					description.getAPIVersion() == null ? "-" : description.getAPIVersion(),
					description.getAuthors() == null ? "-" : StringUtilities.listToListingString(description.getAuthors()),
					description.getWebsite() == null ? "-" : description.getWebsite(),
					description.getDescription() == null ? "-" : description.getDescription(),
					description.getDepend() == null ? "-" : StringUtilities.listToListingString(description.getDepend()),
					description.getSoftDepend() == null ? "-" : StringUtilities.listToListingString(description.getSoftDepend())
				);
				
				break;
				
			case "inventory":
				
				inventorySee.inventorySee.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
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
				
			case "legging":
				
				if(p == null) return true;
				
				is = p.getInventory().getItemInMainHand();
				if(is == null || is.getType().equals(Material.AIR)) return true;
				
				tmp = p.getInventory().getLeggings();
				p.getInventory().setLeggings(is);
				p.getInventory().setItemInMainHand(tmp);
				
				break;
				
			case "lightning":
				{
					if(args.length == 1) {
						if(p == null) return true;
						
						b = p.getTargetBlock((Set<Material>) null, Integer.MAX_VALUE);
						if(b == null) return true;
						
						p.getWorld().spawnEntity(b.getLocation(), EntityType.LIGHTNING);
					} else if(args.length > 1) {
						Player p1 = Bukkit.getPlayer(args[0]);
						if(p1 == null) return true;
						
						p1.getWorld().spawnEntity(p1.getLocation(), EntityType.LIGHTNING);
					}
					
					break;
				}
			case "language":
				
				if(args.length < 2) break;
				MainConfig.setLanguage(args[1]);
				LanguageConfig.load();
				
				LanguageConfig.sendMessage(sender, "language.change", args[1]);
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
						LanguageConfig.sendMessage(sender, "error.NumberFormatException", args[0]);
					}
				
				break;
				
			case "mute":
				{
					if(args.length >= 2){
						Player p1 = PlayerUtilities.getOfflinePlayer(args[0]).getPlayer();
						if(p1 == null) return true;
						
						PlayerConfig playerConfig = PlayerManager.getPlayerConfig(p1);
						
						if(!playerConfig.getBoolean(PlayerConfigKey.tMute)){
							playerConfig.set(PlayerConfigKey.tMute, true);
							LanguageConfig.sendMessage(sender, "enabled-Command", p1.getName());
							LanguageConfig.sendMessage(p1, "mute.enabled-Player");
						} else {
							playerConfig.set(PlayerConfigKey.tMute, false);
							LanguageConfig.sendMessage(sender, "disabled-Command", p1.getName());
							LanguageConfig.sendMessage(p1, "mute.disabled-Player");
						}
					}
					
					break;
				}
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
				
				return MPCommand.mpcommand.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "pluginmanager":
				
				return DisableEnable.disableEnable.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
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
				
			case "reload":
				
				MainConfig.reload();
				Main.reload();
				sender.sendMessage("§aReload complete!");
				
				break;
				
			case "repair":
				{
					if(args.length <= 1) {
						if(p == null) return true;
						is = p.getInventory().getItemInMainHand();
					} else {
						Player p1 = Bukkit.getPlayer(args[1]);
						is = p1.getInventory().getItemInMainHand();
					}
					
					if(is == null || is.getType().equals(Material.AIR)) return true;
					ItemUtilies.setDurability(is, 0);
					
					break;
				}
				
			case "seen":
				{
					try {
						OfflinePlayer offlinePlayer = PlayerUtilities.getOfflinePlayer(args[1]);
						if(offlinePlayer == null) break;
						
						PlayerConfig config = PlayerManager.getPlayerConfig(offlinePlayer.getUniqueId(), false);
						if(config == null) break;
						
						if(offlinePlayer.isOnline()) {
							LocalDateTime loginTime = config.getLocalDateTime(PlayerConfigKey.loginTime);
							if(loginTime == null) break;
							
							LanguageConfig.sendMessage(sender, "seen.loginSince", offlinePlayer.getName(), TimeUtilities.timeToString(loginTime, LocalDateTime.now()));
						} else {
							LocalDateTime logoutTime = config.getLocalDateTime(PlayerConfigKey.logoutTime);
							if(logoutTime == null) break;
							
							LanguageConfig.sendMessage(sender, "seen.logoutSince", offlinePlayer.getName(), TimeUtilities.timeToString(logoutTime, LocalDateTime.now()));
						}
						
					} catch (IllegalArgumentException e) {
						LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
					}
					
					break;
				}
				
			case "silent":
				
				CommandSender commandSender = SudoPlayerManager.getSudoPlayer(sender);
				((SudoPlayerInterface) commandSender).setSilentOutputMessage(true);
				Bukkit.dispatchCommand(commandSender, StringUtilities.arrayToString(Arrays.copyOfRange(args, 1, args.length)));
				
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
				{
					if(args.length == 2) {
						if(p == null) return true;
						
						if(p.isFlying())
							p.setFlySpeed(flo(Double.parseDouble(args[1]), sender));
						else
							p.setWalkSpeed(flo(Double.parseDouble(args[1]), sender));
						
						sender.sendMessage("Normal Speed == 2");
					} else if(args.length >= 4) {
						Player p1 = Bukkit.getPlayer(args[3]);
						if(p1 == null) return true;
						
						if(args[2].equalsIgnoreCase("walk"))
							p1.setWalkSpeed(flo(Double.parseDouble(args[1]), sender));
						else if(args[2].equalsIgnoreCase("fly"))
							p1.setFlySpeed(flo(Double.parseDouble(args[1]), sender));
						
						sender.sendMessage("Normal Speed == 2");
					}
					
					break;
				}
			case "skull":
				
				Skullitem.skullitem.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				break;
				
			case "sudo":
				
				if(args[1].equalsIgnoreCase("@c")) {
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtilities.arrayToString(Arrays.copyOfRange(args, 2, args.length)));
				} else {
					Player sudoPlayer = Bukkit.getPlayer(args[1]);
					if(sudoPlayer == null) return true;
					
					if(args.length < 3) return true;
					PluginCommand command = Bukkit.getServer().getPluginCommand(args[2]);
					if(command != null)
						command.execute(SudoPlayerManager.getSudoPlayer(sender, sudoPlayer), args[2], Arrays.copyOfRange(args, 3, args.length));
				}
				
				break;
				
			case "status":
				sender.sendMessage("§e--------------------------------------------------");
				
				sender.sendMessage("§e Platform: §6" + SystemStatus.getPlatform() + "§e(§6" + SystemStatus.getArchitecture() + "§e)" + " §eRunning threads: §6" + SystemStatus.getRunningThreads());
				sender.sendMessage("§e System CPU usage: §6" + SystemStatus.round(SystemStatus.getSystemCPUUsage(), 2) + " % §e(§6" + SystemStatus.getCores() + " cores§e)");
				sender.sendMessage("§e Process CPU usage: §6" + SystemStatus.round(SystemStatus.getCPUUsage(), 2));
				sender.sendMessage("§e Uptime: " + SystemStatus.getOnlineSince());
				
				try {
					double[] tps = SystemStatus.getRecentTPS();
					if(tps != null)
						sender.sendMessage("§e TPS: §6" + SystemStatus.round(((tps[0] + tps[1] + tps[2]) / 3), 2) + " §e(§6" + SystemStatus.round(tps[0], 2) + " " + SystemStatus.round(tps[1], 2) + " " + SystemStatus.round(tps[2], 2) + "§e)");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sender.sendMessage("§e Memory usage: §6" + SystemStatus.round(( SystemStatus.BytestoMB(SystemStatus.getUsedMemory()) / SystemStatus.BytestoMB(SystemStatus.getMaxMemory()) ) * 100, 2) + "% §e(§6" + (int) SystemStatus.BytestoMB(SystemStatus.getUsedMemory()) + " §e/§6 " + (int) SystemStatus.BytestoMB(SystemStatus.getMaxMemory()) + " MB§e)");
				sender.sendMessage("§e Java version: " + SystemStatus.getJavaVersion());
				sender.sendMessage("§e Disk usage: " + SystemStatus.round(( SystemStatus.BytestoGB(SystemStatus.getUsedDisk()) / SystemStatus.BytestoGB(SystemStatus.getMaxDisk())) * 100, 2) + "% §e(§6" + (int) SystemStatus.BytestoGB(SystemStatus.getUsedDisk()) + " §e/§6 " + (int) SystemStatus.BytestoGB(SystemStatus.getMaxDisk()) + " GB§e)");
				
				sender.sendMessage("§e--------------------------------------------------");
				
				for(World world : Bukkit.getWorlds())
					sender.sendMessage("§eWorld: §6" + world.getName() + "§e Loaded Chunks: §6" + world.getLoadedChunks().length + "§e ForceLoaded Chunks: §6" + world.getForceLoadedChunks().size() + "§e Entities: §6" + world.getEntities().size() + "§e Players: §6" + world.getPlayers().size());
				
				sender.sendMessage("§e--------------------------------------------------");
				
				break;
				
			case "teleport":
				
				teleportCommand.teleport.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
				break;
				
			case "timer":
				
				return TimerCommand.timerCommand.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "trade":
				
				return TradeCommands.tradeCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "updater":
				
				return UpdaterCommand.updaterCommands.onCommand(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
			case "uuid":
				if(args.length < 2) break;
				
				OfflinePlayer player = PlayerUtilities.getOfflinePlayer(args[1]);
				UUID uuid = player.getUniqueId();
				sender.sendMessage(uuid.toString());
				
				break;
				
			case "version":
				
				LanguageConfig.sendMessage(sender, "version.current-version", Main.getPlugin().getDescription().getVersion());
				SpigotPluginUpdater spu = new SpigotPluginUpdater(70992);
				if(spu.hasNewerVersion())
					LanguageConfig.sendMessage(sender, "version.new-version", spu.getOnlineVersion());
				
				break;
				
			case "warp":
			case "setwarp":
			case "delwarp":
			case "editwarp":
			case "savewarp":
				
				return warpCommands.commands.onCommand(sender, cmd, cmdLabel, args);
				
			case "wallghost":
				
				if(args.length <= 1) {
					if(FTB.toogle(p))
						LanguageConfig.sendMessage(sender, "wallghost.add-Player", p.getName());
					else
						LanguageConfig.sendMessage(sender, "wallghost.remove-Player", p.getName());
				} else {
					Player p2 = Bukkit.getPlayer(args[1]);
					if(p2 == null) break;
					
					if(FTB.toogle(p2))
						LanguageConfig.sendMessage(sender, "wallghost.add-Player", args[1]);
					else
						LanguageConfig.sendMessage(sender, "wallghost.add-Player", args[1]);
				}
				
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
			returnArguments.add("cob");
			returnArguments.add("commandspy");
			returnArguments.add("chestplate");
			returnArguments.add("delwarp");
			returnArguments.add("editwarp");
			returnArguments.add("fly");
			returnArguments.add("feed");
			returnArguments.add("god");
			returnArguments.add("head");
			returnArguments.add("heal");
			returnArguments.add("hide");
			returnArguments.add("itemdb");
			returnArguments.add("info");
			returnArguments.add("inventory");
			returnArguments.add("legging");
			returnArguments.add("join");
			returnArguments.add("jump");
			returnArguments.add("language");
			returnArguments.add("more");
			returnArguments.add("mute");
			returnArguments.add("nametag");
			returnArguments.add("near");
			returnArguments.add("paint");
			returnArguments.add("pluginmanager");
			returnArguments.add("random");
			returnArguments.add("repair");
			returnArguments.add("reload");
			returnArguments.add("savewarp");
			returnArguments.add("setwarp");
			returnArguments.add("silent");
			returnArguments.add("sit");
			returnArguments.add("sign");
			returnArguments.add("skull");
			returnArguments.add("status");
			returnArguments.add("sudo");
			returnArguments.add("speed");
			returnArguments.add("teleport");
			returnArguments.add("timer");
			returnArguments.add("trade");
			returnArguments.add("updater");
			returnArguments.add("uuid");
			returnArguments.add("version");
			returnArguments.add("wallGhost");
			returnArguments.add("warp");
			
			Iterator<String> it = returnArguments.iterator();
			while(it.hasNext()) {
				if(!sender.hasPermission(PermissionHelper.getPermissionCommand(it.next())))
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
					
				case "cob":
					
					return CoBCommands.commandOnBlock.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
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
							
							returnArguments.add("@c");
							
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
					
				case "info":
					
					for(Plugin plugin : Bukkit.getPluginManager().getPlugins())
						returnArguments.add(plugin.getName());
					break;
					
				case "inventory":
					
					return inventorySee.inventorySee.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
			
				case "join":
					returnArguments.add("help");
					break;
					
				case "language":
					
					returnArguments.add("<Language>");
					break;
					
				case "nametag":
					returnArguments.add("true");
					returnArguments.add("false");
					
					break;
					
				case "paint":
					
					return MPCommand.mpcommand.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "pluginmanager":
					
					return DisableEnable.disableEnable.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "timer":
					
					return TimerCommand.timerCommand.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "trade":
					
					return TradeCommands.tradeCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
					
				case "random":
					if(args.length == 2) returnArguments.add("<Amount>");
					else if(args.length == 3) returnArguments.add("<Player,Player...>");
					
					break;
					
				case "updater":
					
					return UpdaterCommand.updaterCommands.onTabComplete(sender, cmd, cmdLabel, Arrays.copyOfRange(args, 1, args.length));
				
				case "hide":
				case "lightning":
				case "clear":
				case "fly":
				case "uuid":
				case "wallGhost":
					for(Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());
					
					break;
					
				case "warp":
				case "delwarp":
				case "editwarp":
				case "setwarp":
					warpCommands.commands.onTabComplete(sender, cmd, cmdLabel, args);
					
					break;
			}
		}
		
		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));
		
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
