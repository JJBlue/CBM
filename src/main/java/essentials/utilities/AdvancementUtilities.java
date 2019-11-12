package essentials.utilities;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;

import essentials.main.Main;

public class AdvancementUtilities {
	public static Advancement getMinecraftAdvancement(String key) {
		return Bukkit.getServer().getAdvancement(NamespacedKey.minecraft(key));
	}
	
	public static Advancement getAdvancement(NamespacedKey key) {
		return Bukkit.getServer().getAdvancement(key);
	}
	
	public static Advancement getAdvancement(String key) {
		Iterator<Advancement> iterator = Bukkit.getServer().advancementIterator();
		while(iterator.hasNext()) {
			Advancement advancement = iterator.next();
			if(advancement.getKey().getKey().equalsIgnoreCase(key)) {
				return advancement;
			}
		}
		return null;
	}
	
	public static boolean isAdvancementComplete(Player player, Advancement advancement) {
		AdvancementProgress progress = player.getAdvancementProgress(advancement);
		return progress.isDone();
	}
	
	public static Advancement addAdvancement(String key) {
		Advancement advancement = Bukkit.getServer().getAdvancement(new NamespacedKey(Main.getPlugin(), key));
		//TODO
		return advancement;
	}
	
	public static boolean removeAdvancement(Advancement advancement) {
		Iterator<Advancement> adIterator = Bukkit.advancementIterator();
		while(adIterator.hasNext()) {
			if(advancement == adIterator.next()) {
				adIterator.remove();
				return true;
			}
		}
		return false;
	}
	
	public static void addAdvancementCriteria(Player player, Advancement advancement, String criteria) {
		AdvancementProgress progress = player.getAdvancementProgress(advancement);
		progress.revokeCriteria(criteria);
	}
	
	public static void removeAdvancementCriteria(Player player, Advancement advancement, String criteria) {
		AdvancementProgress progress = player.getAdvancementProgress(advancement);
		progress.awardCriteria(criteria);
	}
	
	public static void resetAdvancement(Player player, Advancement advancement) {
		AdvancementProgress progress = player.getAdvancementProgress(advancement);
		
		for(String criteria : progress.getAwardedCriteria()) {
			progress.revokeCriteria(criteria);
		}
	}
	
	public static AdvancementProgress getAdvancementProgress(Player player, Advancement advancement) {
		return player.getAdvancementProgress(advancement);
	}
}
