package essentials.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.tablist.TablistUtilitiesReflections;
import essentials.utilitiesvr.tablist.TablistUtilities_v1_14;

public class TablistUtilities {
	public static void sendHeaderFooter(String header, String footer) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			switch (ReflectionsUtilities.getPackageVersionName()) {
				case "v1_14_R1":
					TablistUtilities_v1_14.sendHeaderFooter(onlinePlayer, header, footer);
					return;
			}
			TablistUtilitiesReflections.sendHeaderFooter(onlinePlayer, header, footer);
		}
	}
	
	public static void sendHeaderFooter(Player player, String header, String footer) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				TablistUtilities_v1_14.sendHeaderFooter(player, header, footer);
				return;
		}
		TablistUtilitiesReflections.sendHeaderFooter(player, header, footer);
	}
}
