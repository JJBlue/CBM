package essentials.utilities;

import org.bukkit.entity.Player;

import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.tablist.TablistUtilities_v1_14;

public class TablistUtilities {
	public static void sendHeaderFooter(String header, String footer) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
//			case "v1_14_R1":
//				TablistUtilities_v1_14.sendHeaderFooter(header, footer);
//				break;
			default:
				TablistUtilities.sendHeaderFooter(header, footer);
				break;
		}
	}
	
	public static void sendHeaderFooter(Player player, String header, String footer) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
//			case "v1_14_R1":
//				TablistUtilities_v1_14.sendHeaderFooter(player, header, footer);
//				break;
			default:
				TablistUtilities.sendHeaderFooter(player, header, footer);
				break;
		}
	}
}
