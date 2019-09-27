package essentials.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;

import essentials.config.MainConfig;
import essentials.config.MainConfigEnum;
import essentials.language.LanguageConfig;
import essentials.player.PlayersYMLConfig;
import essentials.utilities.PlayerUtilities;
import essentials.utilities.StringUtilities;
import essentials.utilities.permissions.PermissionHelper;
import essentials.utilities.placeholder.PlaceholderFormatter;

public class Join implements Listener {
	private static ArrayList<String> tempPlayer = new ArrayList<>();

	@EventHandler
	private void J(PlayerJoinEvent event) {
		ConfigurationSection section = PlayersYMLConfig.getConfigurationSection("join");
		if(section == null) return;
		
		Player player = event.getPlayer();
		
		if(section.getBoolean("silent"))
			event.setJoinMessage(null);
		else if(!event.getPlayer().hasPlayedBefore() && section.getBoolean("first-time-messages-enable")) {
			List<String> messages = section.getStringList("first-time-messages");
			
			if(!messages.isEmpty()) {
				String message = messages.get(new Random().nextInt(messages.size()));
				if(message.startsWith("!language"))
					message = LanguageConfig.getString(message.substring("!language ".length()));
				
				message = StringUtilities.setArgs(message, player.getDisplayName());
				message = PlaceholderFormatter.setPlaceholders(player, message);
				
				event.setJoinMessage(message);
			}
		} else if(section.getBoolean("messages-enable")) {
			List<String> messages = section.getStringList("messages");
			
			if(!messages.isEmpty()) {
				String message = messages.get(new Random().nextInt(messages.size()));
				if(message.startsWith("!language"))
					message = LanguageConfig.getString(message.substring("!language ".length()));
				
				message = StringUtilities.setArgs(message, player.getDisplayName());
				message = PlaceholderFormatter.setPlaceholders(player, message);
				
				event.setJoinMessage(message);
			}
		}
			
		fullServer(event);
	}
	
	private void fullServer(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isOp()) return;
		if (MainConfig.getFullSize() == -1 || Bukkit.getOnlinePlayers().size() < MainConfig.getFullSize()) return;
		if (MainConfig.getJoinable().contains(player.getUniqueId().toString())) return;
		if (tempPlayer.contains(event.getPlayer().getName())) {
			tempPlayer.remove(event.getPlayer().getName());
			return;
		}

		System.out.println("Spieler mit der UUID: " + event.getPlayer().getUniqueId() + " versuchte zu joinen!");
		event.getPlayer().kickPlayer(MainConfig.getFullMessage());
		event.setJoinMessage(null);
	}

	@EventHandler
	private void Slots(ServerListPingEvent e) {
		if (MainConfig.getFullSize() != -1)
			e.setMaxPlayers(MainConfig.getFullSize());
		if(MainConfig.getConfiguration().getBoolean(MainConfigEnum.MotdEnable.value))
			e.setMotd(MainConfig.getMotd());
	}

	//TODO update
	@SuppressWarnings("deprecation")
	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("help")) {
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join list");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join clear");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join add <player>");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join remove <player>");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join full <Zahl>");
			} else if (args[1].equalsIgnoreCase("helpo")) {
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join listo");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join addo <player>");
				sender.sendMessage("/" + PermissionHelper.getPluginDefaultCommand() + " join removeo <player>");
			} else if (args[1].equalsIgnoreCase("list")) {
				if (!tempPlayer.isEmpty()) {
					StringBuilder list = new StringBuilder();
					for (String s : tempPlayer) {
						if (list.toString().equals(""))
							list = new StringBuilder(s);
						else
							list.append(", ").append(s);
					}
				} else
					sender.sendMessage("§4Liste ist leer");
			} else if (args[1].equalsIgnoreCase("listo")) {
				List<String> list = MainConfig.getJoinable();

				if (!list.isEmpty()) {
					StringBuilder builder = new StringBuilder();
					boolean start = true;

					for (String uuid : list) {
						if (!start)
							builder.append(", ");
						else
							start = false;

						OfflinePlayer player = PlayerUtilities.getOfflinePlayerFromUUID(uuid);
						if (player != null)
							builder.append(player.getName());
						else
							builder.append("§4").append(uuid);
					}

					sender.sendMessage(builder.toString());
				} else
					sender.sendMessage("§4Liste ist leer");

			} else if (args[1].equalsIgnoreCase("clear")) {
				if (!tempPlayer.isEmpty()) {
					for (String s : tempPlayer)
						tempPlayer.remove(s);

					sender.sendMessage("Alle tempPlayers wurden gel$scht!");
				}
			}
		} else if (args.length == 3) {
			if (args[1].equalsIgnoreCase("add")) {
				if (!tempPlayer.contains(args[2]))
					tempPlayer.add(args[2]);

				sender.sendMessage("Spieler: " + args[2] + " wurde hinzugef$gt.");
			} else if (args[1].equalsIgnoreCase("addo")) {
				List<String> list = MainConfig.getJoinable();
				String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();

				if (!list.contains(uuid)) {
					list.add(uuid);
					MainConfig.setJoinable(list);
				}

				sender.sendMessage("Player: " + args[2] + " added");
			} else if (args[1].equalsIgnoreCase("remove")) {
				if (tempPlayer.contains(args[2]))
					tempPlayer.remove(args[2]);

				sender.sendMessage("Spieler: " + args[2] + " wurde gel$scht.");
			} else if (args[1].equalsIgnoreCase("removeo")) {
				List<String> list = MainConfig.getJoinable();
				String uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId().toString();

				if (!list.contains(uuid)) {
					list.remove(uuid);
					MainConfig.setJoinable(list);
				}

				sender.sendMessage("Player: " + args[2] + " removed");
			} else if (args[1].equalsIgnoreCase("full")) {
				try {
					MainConfig.setFullSize(Integer.parseInt(args[2]));
				} catch (NumberFormatException e) {
				}
				sender.sendMessage("Maximale Spieleranzahl wurde auf " + args[2] + " gesetzt.");
			}
		}

		return true;
	}
}
