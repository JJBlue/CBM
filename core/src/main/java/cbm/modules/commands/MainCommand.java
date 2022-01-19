package cbm.modules.commands;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import cbm.config.MainConfig;
import cbm.economy.EconomyCommands;
import cbm.language.LanguageConfig;
import cbm.main.Main;
import cbm.modulemanager.ModuleCommand;
import cbm.modulemanager.ModuleManager;
import cbm.modules.JoinListener;
import cbm.modules.NameTag.nt;
import cbm.modules.chair.chair;
import cbm.modules.claim.ClaimCommands;
import cbm.modules.commands.commands.BookCommand;
import cbm.modules.commands.commands.InventorySee;
import cbm.modules.commands.commands.SignCommands;
import cbm.modules.commands.commands.clearground;
import cbm.modules.commands.commands.gc;
import cbm.modules.commands.commands.speed;
import cbm.modules.commands.tabcompleter.STabCompleter;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;
import cbm.modules.container.ContainerCommands;
import cbm.modules.disguise.DisguiseManager;
import cbm.modules.disguise.name.NameManager;
import cbm.modules.disguise.skin.Skin;
import cbm.modules.eventsfinder.EventFinder;
import cbm.modules.eventsfinder.EventFinderInventory;
import cbm.modules.holograms.HologramCommand;
import cbm.modules.move.AFK;
import cbm.modules.nbt.NBTCommands;
import cbm.modules.pluginmanager.DisableEnable;
import cbm.modules.skull.SkullInventory;
import cbm.modules.tablist.Tablist;
import cbm.modules.teleport.teleportCommand;
import cbm.modules.updater.SpigotPluginUpdater;
import cbm.modules.visible.HideState;
import cbm.modules.visible.VisibleManager;
import cbm.player.CountTime;
import cbm.player.PlayerConfig;
import cbm.player.PlayerConfigKey;
import cbm.player.PlayerManager;
import cbm.utilities.ItemUtilies;
import cbm.utilities.LocationUtilities;
import cbm.utilities.MathUtilities;
import cbm.utilities.StringUtilities;
import cbm.utilities.TimeUtilities;
import cbm.utilities.permissions.PermissionHelper;
import cbm.utilities.system.SystemStatus;
import cbm.utilitiesvr.bukkit.BukkitUtilities;
import cbm.utilitiesvr.chat.ChatMessageType;
import cbm.utilitiesvr.chat.ChatUtilities;
import cbm.utilitiesvr.chat.ClickAction;
import cbm.utilitiesvr.player.PlayerUtilities;
import components.classes.Files;

public class MainCommand implements TabExecutor {
	
	public static void load() {
		CommandManager.register("module", new RedirectTabExecutor(new ModuleCommand()));
		CommandManager.register("book", new RedirectTabExecutor(new BookCommand()));
		CommandManager.register("claim", new RedirectTabExecutor(new ClaimCommands()));
		CommandManager.register("container", new RedirectTabExecutor(new ContainerCommands()));
		CommandManager.register("economy", new RedirectTabExecutor(new EconomyCommands()));
		CommandManager.register("hologram", new RedirectTabExecutor(new HologramCommand()));
		CommandManager.register("inventory", new RedirectTabExecutor(new InventorySee()));
		CommandManager.register("join", new RedirectTabExecutor(new JoinListener(), 0));
		CommandManager.register("nbt", new RedirectTabExecutor(new NBTCommands()));
		CommandManager.register("pluginmanager", new RedirectTabExecutor(new DisableEnable()));
		CommandManager.register("sign", new RedirectTabExecutor(new SignCommands()));
		CommandManager.register("skull", new RedirectTabExecutor(new SkullInventory()));
		CommandManager.register("teleport", new RedirectTabExecutor(new teleportCommand()));
		
		afk();
		blockname();
		gc.register();
		speed.register();
		clearground.register();
	}
	
	public static void afk() {
		CommandExecutor executor = (sender, cmd, cmdLabel, args) -> {
			Player p1 = null;

			if (args.length == 1) {
				if(sender instanceof Player) {
					p1 = (Player) sender;
				}
			} else {
				p1 = Bukkit.getPlayer(args[1]);
			}

			if (p1 == null) return true;
			AFK.change(p1);
			return true;
		};
		CommandManager.register("afk", CommandManager.getTabExecutor(executor, STabCompleter.getPlayers()));
	}
	
	public static void blockname() {
		CommandExecutor executor = (sender, cmd, cmdLabel, args) -> {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.sendMessage(player.getLocation().getBlock().getType() + "");
			}
			return true;
		};
		CommandManager.register("blockname", CommandManager.getTabExecutor(executor));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return true;
		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;

		args[0] = args[0].toLowerCase();
		if(!CommandManager.checkPermissions(sender, args))
			return true;
		if(CommandManager.execute(sender, cmd, cmdLabel, args))
			return true;
		
		switch (args[0]) {
//			case "test": {
//				File file = new File(args[1]);
//				try {
//					int ID = BukkitMidiPlayerManager.play(file);
//					Bukkit.broadcastMessage("start " + ID);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				break;
//			}
//			case "test2": {
//				BukkitMidiPlayerManager.stop(Integer.parseInt(args[1]));
//				Bukkit.broadcastMessage("stop");
//				break;
//			}
//			case "a": {
//				ItemStack itemStack = p.getInventory().getItemInMainHand();
//				BlockStateMeta blockStateMeta = (BlockStateMeta) itemStack.getItemMeta();
//				ShulkerBox shulkerBox = (ShulkerBox) blockStateMeta.getBlockState();
//				
//				Inventory inventory = Bukkit.createInventory(
//					null,
//					shulkerBox.getInventory().getSize(),
//					shulkerBox.getCustomName() == null ? "ShulkerBox" : shulkerBox.getCustomName()
//				);
//				inventory.setContents(shulkerBox.getInventory().getContents());
//				p.openInventory(inventory);
//			}
			case "boot":

				if (p == null) return true;

				ItemStack is = p.getInventory().getItemInMainHand();
				if (is == null || is.getType().equals(Material.AIR)) return true;

				ItemStack tmp = p.getInventory().getBoots();
				p.getInventory().setBoots(is);
				p.getInventory().setItemInMainHand(tmp);

				break;

			case "burn": {
				if (args.length < 3) return true;
				Player p1 = Bukkit.getPlayer(args[1]);
				if (p1 == null) return true;
				p1.setFireTicks(Integer.parseInt(args[2]));

				break;
			}
			case "broadcast": {
				StringBuilder msg = null;
				for (int i = 1; i < args.length; i++) {
					if (msg == null)
						msg = new StringBuilder(args[i]);
					else
						msg.append(" ").append(args[i]);
				}

				if (msg != null)
					Bukkit.broadcastMessage(msg.toString());

				break;
			}
			case "chestplate": {
				if (p == null) return true;

				is = p.getInventory().getItemInMainHand();
				if (is == null || is.getType().equals(Material.AIR)) return true;

				tmp = p.getInventory().getChestplate();
				p.getInventory().setChestplate(is);
				p.getInventory().setItemInMainHand(tmp);

				break;
			}

			case "clearchat": {
				if (args.length >= 2 && args[1].equalsIgnoreCase("all")) {
					if (p == null || PermissionHelper.hasCommandPermission(p, "clearchat.all")) {
						for (Player ps : Bukkit.getOnlinePlayers()) {
							for (int i = 0; i < 70; i++) {
								ps.sendRawMessage("       ");
							}
						}
						break;
					}
					return true;
				}
				
				if (p != null) {
					for (int i = 0; i < 70; i++) {
						p.sendRawMessage("       ");
					}
				}
				
				break;
			}
			case "undisguise": {
				Player p2;
				
				if(args.length == 1) {
					p2 = p;
				} else {
					p2 = Bukkit.getPlayer(args[1]);
					if(p2 == null) break;
				}
				
				DisguiseManager.undisguise(p2);
			}
			case "disguise": {
				if(args.length < 2) break;
				
				String name = null;
				Player p2 = null;
				
				if(args.length == 2) {
					name = args[1];
					p2 = p;
				} else {
					p2 = Bukkit.getPlayer(args[2]);
					if(p2 == null) break;
					name = args[1];
				}
				
				DisguiseManager.disguise(p2, name);
				
				break;
			}
			case "eventfinder": {
				
				if(p == null) break;
				
				if(args.length > 1) {
					Plugin plugin = Bukkit.getPluginManager().getPlugin(args[1]);
					if(plugin == null) break;
					EventFinderInventory.inventory(p, EventFinder.findEvents(plugin));
				} else
					EventFinderInventory.inventory(p, EventFinder.findEvents());
				
			}
			case "feed": {
				Player p1;

				if (args.length >= 2) {
					p1 = Bukkit.getPlayer(args[1]);
					if (p1 == null) return true;

					p1.setSaturation(p1.getSaturation() > 20 ? p1.getSaturation() : 20);
					
					if (args.length == 2)
						p1.setFoodLevel(20);
					else
						try {
							p1.setFoodLevel(Integer.parseInt(args[2]));
						} catch (NumberFormatException nfe) {
							LanguageConfig.sendMessage(sender, "error.NumberFormatException");
						}

					LanguageConfig.sendMessage(sender, "feed.feed-Player", args[1]);
				} else {
					if (p == null) return true;

					p.setFoodLevel(20);
					p.setSaturation(p.getSaturation() > 20 ? p.getSaturation() : 20);
					LanguageConfig.sendMessage(sender, "feed.feed");
				}

				break;
			}
			case "fly": {
				Player p1;

				if (args.length >= 2) p1 = Bukkit.getPlayer(args[1]);
				else p1 = p;

				if (p1 == null) return true;
				p1.setAllowFlight(!p1.getAllowFlight());

				break;
			}
			case "for": {
				if(args.length < 3) break;
				
				try {
					int amount = Integer.parseInt(args[1]);
					for(int i = 0; i < amount; i++)
						Bukkit.dispatchCommand(sender, StringUtilities.arrayToStringRange(args, 2, args.length));
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
				}
				break;
			}
			case "giveall": {
				int amount = 1;
				ItemStack item;
				if (args.length == 1) {
					if (p == null) return true;
					item = p.getInventory().getItemInMainHand().clone();
					if (item == null) return true;
					amount = item.getAmount();
				} else if (args.length == 2) {
					try {
						amount = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						return true;
					}
					if (p == null) return true;
					item = p.getInventory().getItemInMainHand().clone();
					if (item == null) return true;
				} else {
					try {
						amount = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						return true;
					}

					Material mat = Material.matchMaterial(args[1]);
					if (mat == null) return true;
					item = new ItemStack(mat, 1);
				}

				if (amount <= 0 || amount > 64) return true;
				item.setAmount(amount);

				for (Player ps : Bukkit.getOnlinePlayers()) {
					if (ps.getInventory().addItem(item).size() != 0) { //adds items to (full) inventory
						ps.getWorld().dropItem(ps.getLocation(), item);
					} // no else as it gets added in if
				}
				break;
			}
			case "god": {
				Player p1;

				if (args.length >= 2) p1 = Bukkit.getPlayer(args[1]);
				else p1 = p;

				if (p1 == null) break;

				PlayerConfig config = PlayerManager.getConfig(p1);
				boolean nV = !config.getBoolean(PlayerConfigKey.tGod);
				config.set(PlayerConfigKey.tGod, nV);

				if (args.length >= 2) {
					if (nV) {
						LanguageConfig.sendMessage(sender, "god.enabled-Player", p1.getName());
						LanguageConfig.sendMessage(p1, "god.enabled");
					} else {
						LanguageConfig.sendMessage(sender, "god.disabled-Player", p1.getName());
						LanguageConfig.sendMessage(p1, "god.disabled");
					}
				} else {
					if (nV)
						LanguageConfig.sendMessage(sender, "god.enabled");
					else
						LanguageConfig.sendMessage(sender, "god.disabled");
				}
				break;
			}
			case "head":
				if (p == null) return true;

				is = p.getInventory().getItemInMainHand();
				if (is == null || is.getType().equals(Material.AIR)) return true;

				tmp = p.getInventory().getHelmet();
				p.getInventory().setHelmet(is);
				p.getInventory().setItemInMainHand(tmp);

				break;

			case "heal":

				if (args.length >= 2) {
					Player p1 = Bukkit.getPlayer(args[1]);
					if (p1 == null) return true;

					if (args.length == 2)
						p1.setHealth(p1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					else
						try {
							p1.setHealth(Integer.parseInt(args[2]));
						} catch (NumberFormatException nfe) {
							LanguageConfig.sendMessage(sender, "error.NumberFormatException");
						}

					LanguageConfig.sendMessage(sender, "heal.heal-Player", args[1]);
				} else {
					if (p == null) return true;

					p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
					LanguageConfig.sendMessage(sender, "heal.heal");
				}

				break;

			case "hide": {
				Player p1 = null;
				if (args.length <= 1) p1 = p;
				else p1 = Bukkit.getPlayer(args[1]);

				if (p1 == null) return true;
				
				if(args.length < 3) {
					VisibleManager.changeVisible(p1);
					VisibleManager.sendMessage(p1);
				} else {
					try {
						VisibleManager.setVisible(p1, HideState.valueOf(args[2].toUpperCase()));
						VisibleManager.sendMessage(p1);
					} catch (IllegalArgumentException e) {
						LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
					}
				}

				break;
			}
			
			case "itemdb":

				if (p == null) return true;

				is = p.getInventory().getItemInMainHand();
				p.sendMessage("Item: §4" + is.getType());

				break;

			case "info":
				Plugin plugin;

				if (args.length < 2) plugin = Main.getPlugin(); //fallback to own plugin if nothing is specified
				else plugin = Bukkit.getPluginManager().getPlugin(args[1]);
				if (plugin == null) break;

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

			
			case "jump":

				if (p == null) return true;

				Block b = p.getTargetBlock(null, Integer.MAX_VALUE);
				if (b == null) return true;
				Location l = b.getLocation();

				boolean top = false;
				while (!top && l.getY() <= 256) {
					l.setY(l.getY() + 1);

					if (l.getBlock().getType().equals(Material.AIR))
						top = true;
				}

				p.teleport(new Location(l.getWorld(), l.getX(), l.getY(), l.getZ(), p.getLocation().getYaw(), p.getLocation().getPitch()));

				break;

			
			case "joinsilent": {
				
				if(p == null) break;
				
				PlayerConfig config = PlayerManager.getConfig(p);
				boolean value = !config.getBoolean(PlayerConfigKey.joinSilent);
				config.set(PlayerConfigKey.joinSilent, value);
				
				if(value)
					LanguageConfig.sendMessage(sender, "join-silent.true", p.getName());
				else
					LanguageConfig.sendMessage(sender, "join-silent.false", p.getName());
				break;
			}
			case "legging":

				if (p == null) return true;

				is = p.getInventory().getItemInMainHand();
				if (is == null || is.getType().equals(Material.AIR)) return true;

				tmp = p.getInventory().getLeggings();
				p.getInventory().setLeggings(is);
				p.getInventory().setItemInMainHand(tmp);

				break;

			case "lightning": {
				if (args.length == 1) {
					if (p == null) return true;

					b = p.getTargetBlock(null, Integer.MAX_VALUE);
					if (b == null) return true;

					p.getWorld().strikeLightningEffect(b.getLocation());
				} else {
					Player p1 = Bukkit.getPlayer(args[1]);
					if (p1 == null) return true;

					p1.getWorld().strikeLightningEffect(p1.getLocation());
				}

				break;
			}
			case "language":

				if (args.length < 2) break;
				MainConfig.setLanguage(args[1]);
				LanguageConfig.load();

				LanguageConfig.sendMessage(sender, "language.change", args[1]);
				break;
				
			
			case "tablist":
				if (args.length < 2) break;
				if (args[1].equalsIgnoreCase("update")) {
					Tablist.updateAllPlayers();
				}

			case "unnick": {
				
				Player p2;
				
				if(args.length == 1) {
					p2 = p;
				} else {
					p2 = Bukkit.getPlayer(args[1]);
					if(p2 == null) break;
				}
				
				NameManager.unnick(p2);
				
				break;
			}
			case "nick": {
				
				if(args.length < 2) break;
				
				String name;
				Player p2;
				
				if(args.length == 2) {
					name = args[1];
					p2 = p;
				} else {
					p2 = Bukkit.getPlayer(args[2]);
					if(p2 == null) break;
					name = args[1];
				}
				
				NameManager.nick(p2, name);
				
				break;
			}	
			case "more":

				if (p == null) return true;

				is = p.getInventory().getItemInMainHand();
				if (is == null || is.getType().equals(Material.AIR)) return true;

				if (args.length == 1)
					is.setAmount(64);
				else if (args.length == 2)
					try {
						is.setAmount(Integer.parseInt(args[1]));
					} catch (NumberFormatException nfe) {
						LanguageConfig.sendMessage(sender, "error.NumberFormatException", args[1]);
					}

				break;

			case "mute": {
				if (args.length >= 2) {
					Player p1 = PlayerUtilities.getOfflinePlayer(args[1]).getPlayer();
					if (p1 == null) return true;

					PlayerConfig playerConfig = PlayerManager.getConfig(p1);

					if (!playerConfig.getBoolean(PlayerConfigKey.tMute)) {
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

				if (args.length < 2) break;

				if (args[1].equalsIgnoreCase("true"))
					nt.setNameTag(true);
				else if (args[1].equalsIgnoreCase("false"))
					nt.setNameTag(false);

				break;
				
			case "motd":
				
				MainConfig.setMotd(ChatUtilities.convertToColor(StringUtilities.arrayToStringRange(args, 1, args.length)));
				break;

			case "near":
				if (p == null) return true;

				for (Entity e : p.getNearbyEntities(500, 500, 500)) {
					if (e instanceof Player)
						sender.sendMessage(e.getName() + ": " + e.getLocation().distance(p.getLocation()));
				}

				break;
			case "playertime"://playertime [add/remove/set] <ticks> (<fixed> <Player>)
			{
				if(args.length < 3) break;
				
				Player currentPlayer;
				if(args.length <= 4)
					currentPlayer = p;
				else
					currentPlayer = Bukkit.getPlayer(args[4]);
				
				if(currentPlayer == null) break;
				
				long ticks;
				try {
					ticks = Long.parseLong(args[2]);
				} catch (NumberFormatException e) {
					LanguageConfig.sendMessage(sender, "error.NumberFormatException");
					break;
				}
				
				boolean fixed = true;
				try {
					if(args.length >= 4)
						fixed = Boolean.parseBoolean(args[3]);
				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
					break;
				}
				
				switch (args[1].toLowerCase()) {
					case "add":
						currentPlayer.setPlayerTime(currentPlayer.getPlayerTime() + ticks, fixed);
						break;
					case "remove":
						currentPlayer.setPlayerTime(currentPlayer.getPlayerTime() - ticks, fixed);
						break;
					case "set":
						currentPlayer.setPlayerTime(ticks, fixed);
						break;
				}
				
				break;
			}
			case "random":
				if (args.length < 3) break;

				int amount = Integer.parseInt(args[1]);
				LinkedList<Integer> pl = new LinkedList<>();
				String[] players;
				if (!args[2].contains(",")) {
					players = new String[1];
					players[0] = args[2];
				} else {
					players = args[2].split(",");
				}

				if (players.length >= amount && amount >= 0) {
					Random rnd = new Random();
					int i = 0;
					while (i < amount) {
						int z = rnd.nextInt(players.length);
						if (!pl.contains(z)) {
							pl.add(z);
							Player player1 = Bukkit.getPlayer(players[z]);
							if (player1 != null)
								player1.sendMessage("You were selected!"); //TODO: Port to Language
							i++;
						}
					}

					for (int y = 0; y < players.length; y++) {
						if (!pl.contains(y)) {
							Player player1 = Bukkit.getPlayer(players[y]);
							if (player1 != null)
								player1.sendMessage("§4You were not selected!"); //TODO: Port to Language
						}
					}
				}

				break;

			case "reload":

				MainConfig.reload();
				Main.reload();
				
				ModuleManager.unload();
				ModuleManager.load();
				
				sender.sendMessage("§aReload complete!"); //TODO: Port to Language

				break;

			case "repair": {
				if (args.length <= 1) {
					if (p == null) return true;
					is = p.getInventory().getItemInMainHand();
				} else {
					Player p1 = Bukkit.getPlayer(args[1]);
					if (p1 == null) break;
					is = p1.getInventory().getItemInMainHand();
				}

				if (is == null || is.getType().equals(Material.AIR)) return true;
				ItemUtilies.setDurability(is, 0);

				break;
			}

			case "restart":

				MainConfig.restart();
				break;
				
			case "shakeoff": {
				
				if(p == null) break;
				
				while(!p.getPassengers().isEmpty()) {
					p.getPassengers().get(0).eject();
				}
				
				break;
			}
			case "seen": {
				try {
					OfflinePlayer offlinePlayer = PlayerUtilities.getOfflinePlayer(args[1]);
					if (offlinePlayer == null) break;

					PlayerConfig config = PlayerManager.getConfig(offlinePlayer.getUniqueId(), false);
					if (config == null) break;

					LanguageConfig.sendMessage(sender, "seen.player", offlinePlayer.getName());
					LanguageConfig.sendMessage(sender, "seen.nickname", config.getString(PlayerConfigKey.nickname) + "");
					CountTime countTime = new CountTime(config.getString(PlayerConfigKey.playTime));
					Location location = null;
					
					if (offlinePlayer.isOnline()) {
						LocalDateTime loginTime = config.getLocalDateTime(PlayerConfigKey.loginTime);
						countTime.add(config.getLocalDateTime(PlayerConfigKey.loginTime), LocalDateTime.now());
						
						LanguageConfig.sendMessage(sender, "seen.loginSince", TimeUtilities.timeToString(loginTime, LocalDateTime.now()));
						location = offlinePlayer.getPlayer().getLocation();
						
					} else {
						LocalDateTime logoutTime = config.getLocalDateTime(PlayerConfigKey.logoutTime);
						
						LanguageConfig.sendMessage(sender, "seen.logoutSince", TimeUtilities.timeToString(logoutTime, LocalDateTime.now()));
						location = config.getLocation(PlayerConfigKey.logoutLocation);
					}

					ChatUtilities.sendMessage((Player) sender, ChatUtilities.createMessage(
							LanguageConfig.getString("seen.location", LocationUtilities.toString(location)),
							ChatUtilities.createExtra(
								ChatUtilities.createClickHoverMessage(" [" + LanguageConfig.getString("text.teleport") + "]", null, null, ClickAction.RUN_COMMAND,
								"/tp " + offlinePlayer.getName() + " " + location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch())
							)
						), ChatMessageType.CHAT);
					
					LanguageConfig.sendMessage(sender, "seen.playTime", countTime.format("d h m s"));
					LanguageConfig.sendMessage(sender, "seen.joinSilent", config.getBoolean(PlayerConfigKey.joinSilent) + "");
					LanguageConfig.sendMessage(sender, "seen.deathLocation", LocationUtilities.toString(config.getLocation(PlayerConfigKey.deathLocation)));
					LanguageConfig.sendMessage(sender, "seen.commandSpy", config.getInt(PlayerConfigKey.commandSpy) + "");
					LanguageConfig.sendMessage(sender, "seen.commandSpyOperator", config.getInt(PlayerConfigKey.tCommandSpyOperator) + "");
					LanguageConfig.sendMessage(sender, "seen.mute", config.getBoolean(PlayerConfigKey.tMute) + "");
					LanguageConfig.sendMessage(sender, "seen.god", config.getBoolean(PlayerConfigKey.tGod) + "");
					LanguageConfig.sendMessage(sender, "seen.fly", config.getBoolean(PlayerConfigKey.tFly) + "");

				} catch (IllegalArgumentException e) {
					LanguageConfig.sendMessage(sender, "error.IllegalArgumentException");
				}

				break;
			}

			case "sit": {
				if (p == null) return false;

				if (chair.toggle(p))
					sender.sendMessage("Chair: ON");
				else
					sender.sendMessage("Chair: OFF");

				break;
			}
			case "unskin": {
				if(args.length == 1) {
					if(p == null) break;
					
					Skin.changeSkin(p, p.getName());
				} else {
					Player p2 = Bukkit.getPlayer(args[1]);
					Skin.changeSkin(p2, p2.getName());
				}
				
				break;
			}
			case "skin": {
				
				if(args.length < 2) break;
				
				if(args.length == 2) {
					if(p == null) break;
					
					Skin.changeSkin(p, args[1]);
				} else {
					Player p2 = Bukkit.getPlayer(args[2]);
					Skin.changeSkin(p2, args[1]);
				}
				
				break;
			}
			case "status": {
				
				boolean serverInfo = false;
				boolean worldInfo = false;
				
				if(args.length < 2) {
					serverInfo = true;
				} else {
					switch(args[1].toLowerCase()) {
						case "serverinfo":
						case "s":
							serverInfo = true;
							break;
						case "world":
						case "w":
						case "worldinfo":
							worldInfo = true;
							break;
						case "all":
						case "a":
							worldInfo = true;
							serverInfo = true;
							break;
					}
				}
				
				if(serverInfo) {	//TODO: Port to Language
					sender.sendMessage("§e--------------------------------------------------");
	
					sender.sendMessage("§e Platform: §6" + SystemStatus.getPlatform() + "§e(§6" + SystemStatus.getArchitecture() + "§e)" + " §eRunning threads: §6" + SystemStatus.getRunningThreads());
					sender.sendMessage("§e System CPU usage: §6" + MathUtilities.round(SystemStatus.getSystemCPUUsage(), 2) + " % §e(§6" + SystemStatus.getCores() + " cores§e)");
					sender.sendMessage("§e Process CPU usage: §6" + MathUtilities.round(SystemStatus.getCPUUsage(), 2));
					sender.sendMessage("§e Uptime: " + SystemStatus.getOnlineSince());
	
					try {
						double[] tps = SystemStatus.getRecentTPS();
						if (tps != null)
							sender.sendMessage("§e TPS: §6" + MathUtilities.round(((tps[0] + tps[1] + tps[2]) / 3), 2) + " §e(§6" + MathUtilities.round(tps[0], 2) + " " + MathUtilities.round(tps[1], 2) + " " + MathUtilities.round(tps[2], 2) + "§e)");
					} catch (Exception e) {
						e.printStackTrace();
					}
	
					sender.sendMessage("§e Memory usage: §6" + MathUtilities.round((SystemStatus.BytestoMB(SystemStatus.getUsedMemory()) / SystemStatus.BytestoMB(SystemStatus.getMaxMemory())) * 100, 2) + "% §e(§6" + (int) SystemStatus.BytestoMB(SystemStatus.getUsedMemory()) + " §e/§6 " + (int) SystemStatus.BytestoMB(SystemStatus.getMaxMemory()) + " MB§e)");
					sender.sendMessage("§e Java version: " + SystemStatus.getJavaVersion());
					sender.sendMessage("§e Disk usage: " + MathUtilities.round((SystemStatus.BytestoGB(SystemStatus.getUsedDisk()) / SystemStatus.BytestoGB(SystemStatus.getMaxDisk())) * 100, 2) + "% §e(§6" + (int) SystemStatus.BytestoGB(SystemStatus.getUsedDisk()) + " §e/§6 " + (int) SystemStatus.BytestoGB(SystemStatus.getMaxDisk()) + " GB§e)");
				}
				
				sender.sendMessage("§e--------------------------------------------------");

				if(worldInfo) {
					for (World world : Bukkit.getWorlds())
						sender.sendMessage("§eWorld: §6" + world.getName() + "§e Loaded Chunks: §6" + world.getLoadedChunks().length + "§e ForceLoaded Chunks: §6" + world.getForceLoadedChunks().size() + "§e Entities: §6" + world.getEntities().size() + "§e Players: §6" + world.getPlayers().size());
	
					sender.sendMessage("§e--------------------------------------------------");
				}

				break;
			}
			case "uuid":
				if (args.length < 2) break;

				OfflinePlayer player = PlayerUtilities.getOfflinePlayer(args[1]);
				UUID uuid = player.getUniqueId();
				sender.sendMessage(uuid.toString());

				break;

			case "version":

				LanguageConfig.sendMessage(sender, "version.current-version", Main.getPlugin().getDescription().getVersion());
				SpigotPluginUpdater spu = new SpigotPluginUpdater(70992);
				if (spu.hasNewerVersion())
					LanguageConfig.sendMessage(sender, "version.new-version", spu.getOnlineVersion());

				break;

			default:
				break;
		}

		return false;
	}	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return null;

		final List<String> returnArguments = new LinkedList<>();
		
		if (args.length == 1) {
			CommandManager.commands.keySet().forEach(s -> returnArguments.add(s));
			CommandManager.alias.keySet().forEach(s -> returnArguments.add(s));
			
			returnArguments.add("boot");
			returnArguments.add("book");
			returnArguments.add("burn");
			returnArguments.add("broadcast");
			returnArguments.add("clearchat");
			returnArguments.add("chestplate");
			returnArguments.add("disguise");
			returnArguments.add("eventfinder");
			returnArguments.add("fly");
			returnArguments.add("for");
			returnArguments.add("feed");
			returnArguments.add("giveall");
			returnArguments.add("god");
			returnArguments.add("head");
			returnArguments.add("heal");
			returnArguments.add("hide");
			returnArguments.add("itemdb");
			returnArguments.add("info");
			returnArguments.add("inventory");
			returnArguments.add("joinsilent");
			returnArguments.add("jump");
			returnArguments.add("language");
			returnArguments.add("legging");
			returnArguments.add("lightning");
			returnArguments.add("more");
			returnArguments.add("mute");
			returnArguments.add("nametag");
			returnArguments.add("motd");
			returnArguments.add("nick");
			returnArguments.add("near");
			returnArguments.add("playertime");
			returnArguments.add("random");
			returnArguments.add("repair");
			returnArguments.add("reload");
			returnArguments.add("shakeoff");
			returnArguments.add("seen");
			returnArguments.add("sit");
			returnArguments.add("sign");
			returnArguments.add("skin");
			returnArguments.add("skull");
			returnArguments.add("status");
			returnArguments.add("restart");
			returnArguments.add("tablist");
			returnArguments.add("timer");
			returnArguments.add("unban");
			returnArguments.add("undisguise");
			returnArguments.add("unnick");
			returnArguments.add("updater");
			returnArguments.add("unskin");
			returnArguments.add("uuid");
			returnArguments.add("version");

			returnArguments.removeIf(s -> !sender.hasPermission(PermissionHelper.getPermissionCommand(s)));

		} else { // I know that I tested here the permission never -> But then he know the first arguement, I think he knows the rest...
			
			List<String> ra = CommandManager.tabcomplete(sender, cmd, cmdLabel, args);
			if(ra != null)
				return ra;
			
			switch (args[0]) {
				case "burn":
				case "more":
				case "feed":
				case "heal":
					if (args.length == 2) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if (args.length == 3) returnArguments.add("[<amount>]");

					break;
				case "eventfinder": {
					for(Plugin plugin : Bukkit.getPluginManager().getPlugins())
						returnArguments.add(plugin.getName());
				}
				case "for":
					
					switch (args.length) {
						case 2:
	
							returnArguments.add(args[1] + "0");
							returnArguments.add(args[1] + "1");
							returnArguments.add(args[1] + "2");
							returnArguments.add(args[1] + "3");
							returnArguments.add(args[1] + "4");
							returnArguments.add(args[1] + "5");
							returnArguments.add(args[1] + "6");
							returnArguments.add(args[1] + "7");
							returnArguments.add(args[1] + "8");
							returnArguments.add(args[1] + "9");
	
							break;
	
						case 3:
	
							returnArguments.addAll(BukkitUtilities.getAvailableCommands(sender));
							break;
	
						default:
	
							PluginCommand command = Bukkit.getPluginCommand(args[2]);
							if (command == null) break;
							TabCompleter tabCompleter = command.getTabCompleter();
							if (tabCompleter == null) break;
							return tabCompleter.onTabComplete(sender, command, args[2], Arrays.copyOfRange(args, 3, args.length));
					}
					
					break;
					
				case "hide":
					
					if(args.length == 2) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if(args.length == 3) {
						for(HideState state : HideState.values())
							returnArguments.add(state.name());
					}
					
					break;
				case "motd":
					
					returnArguments.add("<Text>");
					break;
				
				case "disguise":
				case "nick":
					
					if(args.length == 2) {
						returnArguments.add("<Name>");
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					} else if(args.length == 3) {
						for(Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					}
					
					break;
					
				case "playertime":
					
					if(args.length == 2) {
						returnArguments.add("add");
						returnArguments.add("remove");
						returnArguments.add("set");
					} else if(args.length == 3)
						returnArguments.add("<ticks>");
					else if(args.length == 4) {
						returnArguments.add("True");
						returnArguments.add("False");
					} else if(args.length == 5) {
						for (Player player : Bukkit.getOnlinePlayers())
							returnArguments.add(player.getName());
					}
					
					break;
					
				case "skin":
				case "undisguise":
				case "unskin":
				case "unnick":
				case "lightning":
				case "clear":
				case "fly":
				case "uuid":
				case "seen":

					for(Player player : Bukkit.getOnlinePlayers())
						returnArguments.add(player.getName());
					
					break;

				case "status":
					
					returnArguments.add("serverinfo");
					returnArguments.add("worldinfo");
					returnArguments.add("all");
					
					break;
					
				case "info":

					for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
						returnArguments.add(plugin.getName());
					break;

				case "tablist":
					returnArguments.add("update");
					break;
					
				case "join":
					returnArguments.add("help");
					break;
					
				case "language":

					returnArguments.add("en");
					returnArguments.add("de_DE");
					returnArguments.add("fr");
					
					for(File file : Files.getFiles(MainConfig.getDataFolder() + "language/example.yml")) {
						if(!file.getName().toLowerCase().contains("example"))
							returnArguments.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
					}
					
					break;

				case "nametag":
					returnArguments.add("true");
					returnArguments.add("false");
					break;

				case "random":
					if (args.length == 2) returnArguments.add("<Amount>");
					else if (args.length == 3) returnArguments.add("<Player,Player...>");
					break;
			}
		}
		return STabCompleter.sortAndRemove(returnArguments, args[args.length - 1]);
	}
}
