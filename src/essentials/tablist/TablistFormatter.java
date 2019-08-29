package essentials.tablist;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TablistFormatter {
	public static String parseToString(CommandSender commandSender, String text) {
		if(text == null) return "";
		
		StringBuilder finialize = new StringBuilder();
		StringBuilder parser = null;
		String preCommand = null;
		
		boolean inParser = false;
		boolean backSlash = false;
		
		boolean couldUseArgs = false;
		boolean argsEnabled = false;
		
		for(char c : text.toCharArray()) {
			switch (c) {
				case '\\':
					
					if(!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null));
						preCommand = null;
					}
					couldUseArgs = false;
					
					backSlash = !backSlash;
					
					if(!backSlash) {
						if(inParser)
							parser.append(c);
						else
							finialize.append(c);
					}
						
					break;
				case '[':
					if(!couldUseArgs) {
						if(inParser)
							parser.append(c);
						else
							finialize.append(c);
						break;
					}
					
					argsEnabled = true;
					inParser = true;
					couldUseArgs = false;
					parser = new StringBuilder();
					
					break;
				case ']':
					
					if(!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null));
						preCommand = null;
					}
					couldUseArgs = false;
					
					if(!argsEnabled) {
						if(inParser)
							parser.append(c);
						else
							finialize.append(c);
						break;
					}
					
					argsEnabled = false;
					inParser = false;
					
					finialize.append(objectToString(commandSender, preCommand, parser.toString()));
					parser = null;
					preCommand = null;
					
					break;
				case '%':
					
					if(!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null));
						preCommand = null;
					}
					couldUseArgs = false;
					
					if(backSlash || argsEnabled) {
						backSlash = false;
						
						if(inParser)
							parser.append(c);
						else
							finialize.append(c);
						
						break;
					}
					
					inParser = !inParser;
					
					if(inParser) {
						if(parser == null)
							parser = new StringBuilder();
						parser.append(c);
					} else {
						parser.append(c);
						preCommand = parser.toString();
						parser = null;
						couldUseArgs = true;
					}
					
					break;

				default:
					if(!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null));
						preCommand = null;
					}
					
					couldUseArgs = false;
					
					if(!inParser)
						finialize.append(c);
					else
						parser.append(c);
			}
		}
		
		if(preCommand != null)
			finialize.append(objectToString(commandSender, preCommand, parser != null ? parser.toString() : null));
		
		return finialize.toString();
	}
	
	/*
	 * args:
	 * 
	 * format = Uppercase, Lowercase, firstUp
	 * boolean = text, number
	 */
	public static String objectToString(CommandSender commandSender, String text, String args) {
		Object value = parser(commandSender, text);
		
		HashMap<String, String> argsMap = new HashMap<>();
		
		{
			if(args != null) {
				for(String keyValue : args.split(",")) {
					String[] keyAndValue = keyValue.split("=");
					if(keyAndValue.length < 2) continue;
					
					keyAndValue[0] = keyAndValue[0].trim().toLowerCase();
					keyAndValue[1] = keyAndValue[1].trim().toLowerCase();
					
					argsMap.put(keyAndValue[0], keyAndValue[1]);
				}
			}
		}
		
		String endString = null;
		
		if(value instanceof String)
			endString = (String) value;
		else if(value instanceof Boolean) {
			Boolean bool = (Boolean) value;
			
			switch (argsMap.get("boolean")) {
				case "number":
					
					if(bool) endString = "1";
					else endString = "0";
					
					break;

				default:
					if(bool) endString = "True";
					else endString = "False";
					
					break;
			}
		} else
			endString = value.toString();
		
		if(argsMap.containsKey("format")) {
			switch (argsMap.get("format")) {
				case "uppercase":
					endString = endString.toUpperCase();
					break;
				case "lowercase":
					endString = endString.toLowerCase();
					break;
				case "firstUp":
					if(endString.length() <= 0) break;
					else if(endString.length() == 1) endString.toUpperCase();
					else
						endString = Character.toUpperCase(endString.charAt(0)) + endString.substring(1, endString.length()).toLowerCase();
					break;
			}
		}
		
		return endString;
	}
	

	/*
	 * @p
	 * @e
	 * @r
	 * 

user_charges_left
user_charges_max
user_charges_time
user_charges_cooldown
user_display_name
user_nickname
user_cuffed
user_muted
user_inpvp
user_god
user_afk
user_joinedcounter
user_banned
user_maxhomes
user_ping
user_canfly
user_flying
user_homeamount
user_vanished_symbol
user_balance_formatted
user_balance
user_prefix
user_suffix
user_nameplate_prefix
user_nameplate_suffix
user_group
user_votecount
user_rank
user_nextranks
user_nextrankpercent
user_nextvalidranks
user_canrankup
user_country
user_country_code
user_city
user_name_colorcode
user_glow_code
user_glow_name
user_jailname
user_jailcell
user_jailtime
user_jailreason
user_bungeeserver
user_playtime_days
user_playtime_dayst
user_playtime_hours
user_playtime_hoursf
user_playtime_hourst
user_playtime_minutes
user_playtime_minutest
user_itemcount_[itemIdName(:data)]
user_maxperm_[corePerm]_[defaultValue]
equation_[equation]
equationint_[equation]
iteminhand_realname
iteminhand_itemdata
iteminhand_worth
iteminhand_worth_one
schedule_nextin_[schedName]
baltop_name_[1-10]
baltop_money_[1-10]
votetop_[1-10]
worth_buy_[itemIdName(:data)]
worth_sell_[itemIdName(:data)]
bungee_total_[serverName]
bungee_current_[serverName]
bungee_motd_[serverName]
bungee_onlinestatus_[serverName]
vault_eco_balance_formatted
server_unique_joins
onlineplayers_names
onlineplayers_displaynames
tps_1
tps_60
tps_300
random_player_name
random_[from]_[to]
user_rank_percent_[rankName]
user_meta_[key]
user_metaint_[key]
user_kitcd_[kitName]
jail_time_[jailName]_[cellId]
jail_username_[jailName]_[cellId]
jail_reason_[jailName]_[cellId]
server_time_[timeFormat]
*/
	public static Object parser(CommandSender commandSender, String ersetzen) {
		Player player = null;
		if(commandSender instanceof Player)
			player = (Player) commandSender;
		
		Entity entity = null;
		if(commandSender instanceof Entity)
			entity = (Entity) commandSender;
		
		BlockCommandSender blockCommandSender = null;
		if(commandSender instanceof BlockCommandSender)
			blockCommandSender = (BlockCommandSender) commandSender;
		
		Location location = null;
		if(entity != null)
			location = entity.getLocation();
		else if(blockCommandSender != null)
			location = blockCommandSender.getBlock().getLocation();
		
		
		if(ersetzen.startsWith("%"))
			ersetzen = ersetzen.substring(1, ersetzen.length());
		if(ersetzen.endsWith("%"))
			ersetzen = ersetzen.substring(0, ersetzen.length() - 1);
		
		switch(ersetzen) {
			case "custom_name":
				if(entity != null)
					return entity.getCustomName();
				break;
			case "display_name":
				if(player != null)
					return player.getDisplayName();
				break;
			case "gamemode":
				if(player != null)
					return player.getGameMode();
				break;
				
			case "iteminhand_displayname":
				if(player != null)
					return player.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
				break;
			case "iteminhand_name":
				if(player != null)
					return player.getInventory().getItemInMainHand().getItemMeta().getLocalizedName();
				break;
			case "iteminhand_type":
				if(player != null)
					return player.getInventory().getItemInMainHand().getType();
				break;
			case "iteminhand_amount":
				if(player != null)
					return player.getInventory().getItemInMainHand().getAmount();
				break;
				
			case "iteminoffhand_displayname":
				if(player != null)
					return player.getInventory().getItemInOffHand().getItemMeta().getDisplayName();
				break;
			case "iteminoffhand_name":
				if(player != null)
					return player.getInventory().getItemInOffHand().getItemMeta().getLocalizedName();
				break;
			case "iteminoffhand_type":
				if(player != null)
					return player.getInventory().getItemInOffHand().getType();
				break;
			case "iteminoffhand_amount":
				if(player != null)
					return player.getInventory().getItemInOffHand().getAmount();
				break;
				
			case "name":
				return commandSender.getName();
			case "op":
				return commandSender.isOp();
				
			case "location_world":
				if(location != null)
					return location.getWorld();
				break;
			case "location_worldname":
				if(location != null)
					return location.getWorld().getName();
				break;
			case "location_x":
				if(location != null)
					return location.getX();
				break;
			case "location_blockx":
				if(location != null)
					return location.getBlockX();
				break;
			case "location_y":
				if(location != null)
					return location.getY();
				break;
			case "location_blocky":
				if(location != null)
					return location.getBlockY();
				break;
			case "location_z":
				if(location != null)
					return location.getZ();
				break;
			case "location_blockz":
				if(location != null)
					return location.getBlockZ();
				break;
			case "location_biome_name":
				if(location != null)
					return location.getWorld().getBiome(location.getBlockX(), location.getBlockY()).name();
				break;
				
			case "user_fly":
				if(player != null)
					return player.isFlying();
				break;
			
			case "server_online":
				return Bukkit.getOnlinePlayers().size();
			case "server_max_players":
				return Bukkit.getMaxPlayers();
		}
		
		return ersetzen;
	}
}
