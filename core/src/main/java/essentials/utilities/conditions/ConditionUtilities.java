package essentials.utilities.conditions;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;

import components.json.JSONArray;
import components.json.JSONObject;
import essentials.economy.EconomyManager;
import essentials.utilities.AdvancementUtilities;
import essentials.utilities.placeholder.PlaceholderFormatter;

public class ConditionUtilities {
	public static boolean execute(Player player, JSONObject json) {
		if(json == null) return true;
		
		if(json.containsKey("money")) {
			EconomyManager.removeMoney(player.getUniqueId(), json.getDouble("money"));
		}
		
		if(json.containsKey("level")) {
			player.setLevel(player.getLevel() + json.getInt("level"));
		}
		
		if(json.containsKey("exp")) {
			player.setExp(player.getExp() + json.getFloat("exp"));
		}
		
		if(json.containsKey("add-criteria")) {
			JSONArray array = (JSONArray) json.get("add-criteria");
			
			for(Object opair : array.getList()) {
				JSONArray pair = (JSONArray) opair;
				Advancement advancement = AdvancementUtilities.getAdvancement(pair.get(0).toString());
				AdvancementUtilities.addAdvancementCriteria(player, advancement, pair.get(1).toString());
			}
		}
		
		if(json.containsKey("remove-criteria")) {
			JSONArray array = (JSONArray) json.get("remove-criteria");
			
			for(Object opair : array.getList()) {
				JSONArray pair = (JSONArray) opair;
				Advancement advancement = AdvancementUtilities.getAdvancement(pair.get(0).toString());
				AdvancementUtilities.addAdvancementCriteria(player, advancement, pair.get(1).toString());
			}
		}
		
		if(json.containsKey("reset-advancements")) {
			JSONArray array = (JSONArray) json.get("reset-advancements");
			
			for(String name : array.toStringList()) {
				Advancement advancement = AdvancementUtilities.getAdvancement(name);
				AdvancementUtilities.resetAdvancement(player, advancement);
			}
		}
		
		if(json.containsKey("run")) {
			player.performCommand(PlaceholderFormatter.setPlaceholders(player, json.getString("run")));
		}
		
		if(json.containsKey("run-server")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderFormatter.setPlaceholders(player, json.getString("run-server")));
		}
		
		if(json.containsKey("sendmessage")) {
			player.sendMessage(PlaceholderFormatter.setPlaceholders(player, json.getString("sendmessage")));
		}
		
		if(json.containsKey("broadcast")) {
			Bukkit.broadcastMessage(PlaceholderFormatter.setPlaceholders(player, json.getString("broadcast")));
		}
		
		//Add here more executes
		
		return true;
	}
	
	public static boolean checkCondition(Player player, JSONObject conditions) {
		if(conditions == null) return true;
		
		boolean result = true;
		
		if(result && conditions.containsKey("money") && !EconomyManager.hasMoney(player.getUniqueId(), conditions.getDouble("money"))) {
			result = false;
		}
		
		if(result && conditions.containsKey("level") && player.getLevel() < conditions.getInt("level")) {
			result = false;
		}
		
		if(result && conditions.containsKey("exp") && player.getExp() < conditions.getFloat("exp")) {
			result = false;
		}
		
		if(result && conditions.containsKey("advancement")) {
			JSONArray array = (JSONArray) conditions.get("advancement");
			
			for(String advancement : array.toStringList()) {
				if(!AdvancementUtilities.isAdvancementComplete(player, AdvancementUtilities.getAdvancement(advancement))) {
					result = false;
				}
			}
		}
		
		if(result && conditions.containsKey("permissions")) {
			JSONArray array = (JSONArray) conditions.get("permissions");
			
			for(String perm : array.toStringList()) {
				if(!player.hasPermission(perm)) {
					result = false;
				}
			}
		}
		
		//Add here more conditions
		
		//And bind more, so its could bevor or
		if(result && conditions.containsKey("and")) {
			boolean cond = checkCondition(player, (JSONObject) conditions.get("and"));
			if(cond) return true;
		}
		
		if(result) return true;
		
		if(!result && conditions.containsKey("or")) {
			boolean cond = checkCondition(player, (JSONObject) conditions.get("or"));
			if(cond) return true;
		}
		
		return result;
	}
	
	//TODO language
	public static List<String> conditionsToString(JSONObject conditions) {
		final List<String> list = new LinkedList<>();
		
		if(conditions == null)
			return list;
		
		if(conditions.containsKey("money")) {
			list.add("Cost: " + conditions.getDouble("money"));
		}
		
		if(conditions.containsKey("level")) {
			list.add("Cost Exp-Level: " + conditions.getInt("level"));
		}
		
		if(conditions.containsKey("exp")) {
			list.add("Cost exp: " + conditions.getFloat("exp"));
		}
		
		if(conditions.containsKey("advancement")) {
			JSONArray array = (JSONArray) conditions.get("advancement");
			
			for(String advancement : array.toStringList()) {
				list.add("Advancements: " + advancement);
			}
		}
		
		if(conditions.containsKey("permissions")) {
			JSONArray array = (JSONArray) conditions.get("permissions");
			
			for(String perm : array.toStringList()) {
				list.add("Permissions: " + perm);
			}
		}
		
		//Add here more strings to list
		
		return list;
	}
}
