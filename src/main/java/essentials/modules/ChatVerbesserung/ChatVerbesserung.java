package essentials.modules.ChatVerbesserung;

import essentials.config.MainConfig;
import essentials.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Not used yet
 */
public class ChatVerbesserung implements Listener {

	public static File CV = new File(MainConfig.getDataFolder(), "ChatCleaner.yml");
	public static FileConfiguration CVConf = YamlConfiguration.loadConfiguration(CV);
	private static List<String> Verboten = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static void Load() {
		CVConf.addDefault("aktiv", false);

		ArrayList<String> l = new ArrayList<>();
		l.add("Scheisse");
		l.add("Depp");
		l.add("Pfotze");
		l.add("Kacke");
		l.add("Arschloch");
		l.add("Arsch");
		l.add("Vollpfosten");
		l.add("Penner");
		l.add("Schwuchtel");
		l.add("Opfer");
		l.add("Bettnaesser");
		l.add("Bettbrunza");
		l.add("Weichei");
		l.add("Hosenscheisser");
		l.add("Schisshase");
		l.add("Fehlgeburt");
		l.add("Fettsack");
		l.add("Hurensohn");
		l.add("Idiot");
		l.add("Pisser");
		l.add("Schlappschwanz");
		l.add("Fresse");
		l.add("verpiss");
		l.add("wixer");
		l.add("huren");
		l.add("scheiss");

		CVConf.addDefault("Verboten", l);
		CVConf.options().copyDefaults(true);
		try {
			CVConf.save(CV);
		} catch (IOException e1) {

		}

		if (CVConf.getList("Verboten") != null) {
			Verboten = (List<String>) CVConf.getList("Verboten");
		}
	}

	@EventHandler
	public void ChatV(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String text = e.getMessage();

		if (CVConf.getBoolean("aktiv")) {
			String Stern = "*****";
			String textsend = text;

			for (String s : Verboten)
				textsend = textsend.replaceAll("(?i)" + s, Stern);

			if (!text.equals(textsend)) {
				e.setMessage(textsend);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> p.damage(1.0));
			}
		}
	}

	public static boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("cv")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("help")) {
					help(sender);
				} else if (args[0].equalsIgnoreCase("list")) {
					for (String s : Verboten)
						sender.sendMessage(s);
				}
			} else if (args.length >= 2) {
				if (args[0].compareTo("add") == 0) {
					int anzahl = args.length;
					StringBuilder argsstring = new StringBuilder();

					for (int y = 2; y <= anzahl; y++) {
						int y2 = y - 1;
						if (y == 2)
							argsstring = new StringBuilder(args[y2]);
						else
							argsstring.append(" ").append(args[y2]);
					}

					Verboten.add(argsstring.toString());

					CVConf.set("Verboten", Verboten);

					sender.sendMessage("Das Wort " + argsstring + " added.");

					try {
						CVConf.save(CV);
					} catch (IOException ignored) {
					}

					Load();
				} else if (args[0].compareTo("remove") == 0) {
					int anzahl = args.length;
					StringBuilder argsstring = new StringBuilder();

					for (int y = 2; y <= anzahl; y++) {
						int y2 = y - 1;
						if (y == 2)
							argsstring = new StringBuilder(args[y2]);
						else
							argsstring.append(" ").append(args[y2]);
					}

					if (Verboten.contains(argsstring.toString())) {
						Verboten.remove(argsstring.toString());

						CVConf.set("Verboten", Verboten);

						sender.sendMessage("The word " + argsstring + " removed.");

						try {
							CVConf.save(CV);
						} catch (IOException ignored) {
						}
						Load();
					} else
						sender.sendMessage("The word " + argsstring + " doesn't find.");
				}
			} else
				help(sender);

			return true;
		}

		return false;
	}

	private static void help(CommandSender sender) {
		sender.sendMessage("/cv list");
		sender.sendMessage("/cv add <text>");
		sender.sendMessage("/cv remove <text>");
	}
}
