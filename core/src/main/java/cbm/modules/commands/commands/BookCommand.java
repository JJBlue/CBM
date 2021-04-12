package cbm.modules.commands.commands;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import cbm.config.MainConfig;

public class BookCommand implements TabExecutor {
	private final static String filePath = MainConfig.getDataFolder() + "books/";

	//TODO commans in book

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (args.length < 1) return true;

		Player p = null;

		if (sender instanceof Player)
			p = (Player) sender;

		switch (args[0].toLowerCase()) {
			case "author":
				if (p == null || args.length < 2) break;

				if (p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK) {
					BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
					meta.setAuthor(args[1]);
					p.getInventory().getItemInMainHand().setItemMeta(meta);
				}

				break;

			case "title":

				if (p == null || args.length < 2) break;
				if (p.getInventory().getItemInMainHand().getType() == Material.WRITTEN_BOOK) {
					BookMeta meta = (BookMeta) p.getInventory().getItemInMainHand().getItemMeta();
					meta.setTitle(args[1]);
					p.getInventory().getItemInMainHand().setItemMeta(meta);
				}

				break;

			case "give":

				if (p == null || args.length < 2) break;
				p.getInventory().addItem(load(args[1]));

				break;

			case "save":

				if (p == null || args.length < 2) break;
				save(p.getInventory().getItemInMainHand(), args[1]);
				break;

			case "convert":

				if (p == null) break;
				ItemStack is = p.getInventory().getItemInMainHand();
				if (is == null || !is.getType().equals(Material.WRITTEN_BOOK)) return true;
				is.setType(Material.WRITABLE_BOOK);

				break;
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		List<String> returnArguments = new LinkedList<>();

		if (args.length == 1) {
			returnArguments.add("author");
			returnArguments.add("title");
			returnArguments.add("give");
			returnArguments.add("save");
			returnArguments.add("convert");

		} else {
			switch (args[0]) {
				case "author":
					returnArguments.add("<Author>");
					break;
				case "title":
					returnArguments.add("<Title>");
					break;
				case "give":

					File[] files = new File(filePath).listFiles();
					if (files == null) break;
					for (File file : files) {
						if (!file.isDirectory())
							returnArguments.add(file.getName());
					}

					break;

				case "save":
					returnArguments.add("<Name>");
					break;

				default:
					break;
			}
		}

		returnArguments.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()));

		returnArguments.sort(Comparator.naturalOrder());

		return returnArguments;
	}

	public static void saveDefaultBook() {
		File file = new File(filePath + "default.yml");
		file.getParentFile().mkdirs();
		if (file.exists()) return;
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

		configuration.set("author", "§4Administrator");
		configuration.set("title", "§3Default Book");
		configuration.set("DisplayName", "§3Default Book");

		List<String> list = new LinkedList<>();
		list.add("0:Seite 1");
		list.add("1:Seite 2");
		list.add("4:Seite 5");

		configuration.set("pages", list);
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save(ItemStack itemStack, String name) {
		if (name == null || name.isEmpty() || itemStack == null || !(itemStack.getItemMeta() instanceof BookMeta))
			return;

		File file = new File(filePath + name);
		file.getParentFile().mkdirs();
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

		BookMeta meta = (BookMeta) itemStack.getItemMeta();

		configuration.set("author", meta.getAuthor());
		configuration.set("title", meta.getTitle());
		configuration.set("DisplayName", meta.getDisplayName());

		List<String> list = new LinkedList<>();
		int count = 1;
		for (String s : meta.getPages())
			list.add((count++) + ":" + s);

		configuration.set("pages", list);
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ItemStack load(String name) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();

		File file = new File(filePath + name);
		if (!file.exists()) return null;
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

		List<String> list = new LinkedList<>();

		for (String s : configuration.getStringList("pages")) {
			if (s.length() < 3) continue;

			try {
				pageHelper(list, Integer.parseInt(s.substring(0, 1)), s.substring(2));
			} catch (NumberFormatException ignored) {
			}
		}

		meta.setAuthor(configuration.getString("author"));
		meta.setTitle(configuration.getString("title"));
		meta.setDisplayName(configuration.getString("displayName"));
		meta.setPages(list);
		book.setItemMeta(meta);
		return book;
	}

	private static void pageHelper(List<String> list, int page, String text) {
		while (page > list.size())
			list.add("");

		list.add(page, text);
	}
}
