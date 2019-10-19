package essentials.utilities.placeholderCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlaceCommandholderFormatter {
	public static String setPlaceholders(CommandSender sender, String text) {
		return parseToString(sender, text, false);
	}
	
	public static String parseToString(CommandSender commandSender, String text, boolean deleteNotExistPlaceHolders) {
		if (text == null) return "";

		StringBuilder finialize = new StringBuilder();
		StringBuilder parser = null;
		String preCommand = null;

		boolean inParser = false;
		boolean backSlash = false;

		boolean couldUseArgs = false;
		boolean argsEnabled = false;

		for (char c : text.toCharArray()) {
			switch (c) {
				case '\\':

					if (!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null, deleteNotExistPlaceHolders));
						preCommand = null;
					}
					couldUseArgs = false;

					backSlash = !backSlash;

					if (!backSlash) {
						if (inParser)
							parser.append(c);
						else
							finialize.append(c);
					}

					break;
				case '[':
					backSlash = false;
					
					if (!couldUseArgs) {
						if (inParser)
							parser.append(c);
						else
							finialize.append(c);
						break;
					}
					
					if(inParser && parser != null) {
						preCommand = parser.toString();
						parser = null;
					}

					argsEnabled = true;
					inParser = true;
					couldUseArgs = false;
					parser = new StringBuilder();

					break;
				case ']':

					backSlash = false;
					
					if (!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null, deleteNotExistPlaceHolders));
						preCommand = null;
					}
					couldUseArgs = false;

					if (!argsEnabled) {
						if (inParser)
							parser.append(c);
						else
							finialize.append(c);
						break;
					}

					argsEnabled = false;
					inParser = false;

					finialize.append(objectToString(commandSender, preCommand, parser.toString(), deleteNotExistPlaceHolders));
					parser = null;
					preCommand = null;

					break;
				case '@':

					if (!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null, deleteNotExistPlaceHolders));
						preCommand = null;
					}
					couldUseArgs = true;

					if (backSlash || argsEnabled) {
						backSlash = false;

						if (inParser)
							parser.append(c);
						else
							finialize.append(c);

						break;
					}

					inParser = !inParser;

					if (inParser) {
						if (parser == null)
							parser = new StringBuilder();
						parser.append(c);
					}

					break;
					
				case ' ':
					
					if(!argsEnabled) {
						if(inParser && parser != null) {
							preCommand = parser.toString();
							parser = null;
						}
						
						couldUseArgs = false;
						inParser = false;
					}

				default:
					if (!argsEnabled && preCommand != null) {
						finialize.append(objectToString(commandSender, preCommand, null, deleteNotExistPlaceHolders));
						preCommand = null;
					}

					if (!inParser)
						finialize.append(c);
					else
						parser.append(c);
			}
		}
		
		if(parser != null)
			preCommand = parser.toString();
		
		if (preCommand != null)
			finialize.append(objectToString(commandSender, preCommand, parser != null ? parser.toString() : null, deleteNotExistPlaceHolders));
		
		return finialize.toString();
	}

	/*
	 * advancements
	 * distance
	 * dx
	 * dy
	 * dz
	 * gamemode
	 * level
	 * limit
	 * name
	 * nbt
	 * scores
	 * sort
	 * tag
	 * team
	 * type
	 * x
	 * x_rotation
	 * y	
	 * y_rotation
	 * z
	 * uuid
	 */
	public static String objectToString(CommandSender commandSender, String text, String args, boolean deleteNotExistPlaceHolders) {
		HashMap<String, String> argsMap = new HashMap<>();

		{
			if (args != null) {
				for (String keyValue : args.split(",")) {
					String[] keyAndValue = keyValue.split("=");
					if (keyAndValue.length < 2) continue;

					keyAndValue[0] = keyAndValue[0].trim().toLowerCase();
					keyAndValue[1] = keyAndValue[1].trim().toLowerCase();

					argsMap.put(keyAndValue[0], keyAndValue[1]);
				}
			}
		}
		
		Object value = null;
		
		if(value == null)
			value = parser(commandSender, text, argsMap);
		if(value == null) { //Not found replacement
			if(deleteNotExistPlaceHolders)
				return "";
			return text + ((args != null) ? "[" + args + "]" : "");
		}
		
		//set arguments
		if(value instanceof Player) {
			if(argsMap.containsKey("uuid") && argsMap.get("uuid").equals("true"))
				value = ((Player) value).getUniqueId().toString();
			value = commandSender.getName();
		} else if(value instanceof List) {
			//TODO
			Bukkit.broadcastMessage(Arrays.toString(((List) value).toArray()));
		}
		
		if(value instanceof String)
			return (String) value;

		return null;
	}

	public static Object parser(CommandSender commandSender, String ersetzen, HashMap<String, String> args) {
		if(ersetzen == null) return null;

		if (ersetzen.startsWith("@"))
			ersetzen = ersetzen.substring(1, ersetzen.length());

		switch (ersetzen) {
			case "s":
				return "lol";
			case "p":{
				if(!(commandSender instanceof Player)) return null;
				return commandSender;
			}
			case "a": {
				final Location startLocation = getLocation(commandSender);
				
				if(args.containsKey("entities") && args.get("entities").equals("true"))
					return getEntities(startLocation, false, args);
				return getEntities(startLocation, true, args);
			}
			case "r": {
				final Location startLocation = getLocation(commandSender);
				List<Entity> entities;
				
				if(args.containsKey("entities") && args.get("entities").equals("true"))
					entities = getEntities(startLocation, false, args);
				else
					entities = getEntities(startLocation, true, args);
				
				if(entities == null || entities.isEmpty()) return null;
				
				int random = new Random().nextInt(entities.size());
				return entities.get(random);
			}
		}

		return null;
	}
	
	protected static Location getLocation(CommandSender sender) {
		if(sender instanceof BlockCommandSender)
			return ((BlockCommandSender) sender).getBlock().getLocation();
		else if(sender instanceof Entity)
			return  ((Entity) sender).getLocation();
		return null;
	}
	
	/**
	 * 
	 * @param location
	 * @param distances
	 * @return distance.length = 3; [2] == 1,2,3 : toDistance, fromDistance, between
	 */
	protected static double[] getDistance(Location location, String distances) {
		double[] distance = new double[3];
		distance[0] = -1;
		distance[1] = -1;
		
		String distanceParamterOne = distances;
		String distanceParamterTwo = null;
		
		final boolean toDistance = distanceParamterOne.startsWith("..");
		final boolean fromDistance = distanceParamterOne.endsWith("..");
		final boolean between = !toDistance && !fromDistance && distanceParamterOne.contains("..");
		
		if((toDistance && fromDistance))
			return null; //ERROR
		
		if(toDistance) {
			distance[2] = 1;
			distanceParamterOne = distanceParamterOne.substring(2);
		} else if(fromDistance) {
			distance[2] = 2;
			distanceParamterOne = distanceParamterOne.substring(0, distanceParamterOne.length() - 2);
		} else if(between) {
			distance[2] = 3;
			String[] distancesString = distanceParamterOne.split("..");
			if(distancesString.length > 2) return null; //ERROR
			distanceParamterOne = distancesString[0];
			distanceParamterTwo = distancesString[1];
		}
	
		try {
			if(distanceParamterOne != null)
				distance[0] = Double.parseDouble(distanceParamterOne);
			if(distanceParamterTwo != null)
				distance[1] = Double.parseDouble(distanceParamterTwo);
			return distance;
		} catch (NumberFormatException e) {}
		return null;
	}
	
	protected static List<Entity> getEntities(Location location, boolean onlyPlayers, HashMap<String, String> args) {
		List<Entity> entities = new LinkedList<>();
		
		if(args.containsKey("x")) {
			String xS = args.get("x");
			double x = 0;
			
			if(xS.startsWith("~")) {
				try {
					x = location.getX() + Double.parseDouble(xS.substring(1, xS.length()));
				} catch (NumberFormatException e) {}
			} else {
				try {
					x = Double.parseDouble(xS);
				} catch (NumberFormatException e) {}
			}
			
			location.setX(x);
		}
		
		if(args.containsKey("y")) {
			String xS = args.get("y");
			double x = 0;
			
			if(xS.startsWith("~")) {
				try {
					x = location.getX() + Double.parseDouble(xS.substring(1, xS.length()));
				} catch (NumberFormatException e) {}
			} else {
				try {
					x = Double.parseDouble(xS);
				} catch (NumberFormatException e) {}
			}
			
			location.setY(x);
		}
		
		if(args.containsKey("z")) {
			String xS = args.get("z");
			double x = 0;
			
			if(xS.startsWith("~")) {
				try {
					x = location.getX() + Double.parseDouble(xS.substring(1, xS.length()));
				} catch (NumberFormatException e) {}
			} else {
				try {
					x = Double.parseDouble(xS);
				} catch (NumberFormatException e) {}
			}
			
			location.setZ(x);
		}
		
		if(onlyPlayers) {
			entities.addAll(Bukkit.getOnlinePlayers());
			
			if(args.containsKey("distance") && location != null) {
				final double[] distance = getDistance(location, args.get("distance"));
				if(distance == null) return null; //ERROR
				
				entities.removeIf((e) -> {
					double dResult = location.distance(e.getLocation());
					
					if(distance[2] == 1) //To Distance
						return dResult > distance[0];
					else if(distance[2] == 2) //From Distance
						return dResult < distance[0];
					else if(distance[2] == 3) //Between
						return dResult < distance[0] || dResult > distance[1];
					return location.distance(e.getLocation()) != distance[0];
				});
			}
		} else {
			boolean useDistance = args.containsKey("distance") && location != null;
			final double[] distance = useDistance ? getDistance(location, args.get("distance")) : null;
			if((useDistance && distance == null) || location == null) return null;
			
			entities.addAll(location.getWorld().getNearbyEntities(location, 0, 0, 0, (e) -> {
				if(!useDistance) return true;
				
				double dResult = location.distance(e.getLocation());
				
				if(distance[2] == 1) //To Distance
					return dResult <= distance[0];
				else if(distance[2] == 2) //From Distance
					return dResult >= distance[0];
				else if(distance[2] == 3) //Between
					return dResult >= distance[0] && dResult <= distance[1];
				return location.distance(e.getLocation()) == distance[0];
			}));
		}
		
		if(args.containsKey("limit")) {
			try {
				int limit = Integer.parseInt(args.get("limit"));
				
				Iterator<Entity> iterator = entities.iterator();
				
				for(int i = 0; i < limit && iterator.hasNext(); i++)
					iterator.next();
				
				while(iterator.hasNext()) {
					iterator.next();
					iterator.remove();
				}
			}catch (NumberFormatException e) {}
		}
		
		if(args.containsKey("type")) {
			entities.removeIf((e) -> {
				return !e.getType().name().equalsIgnoreCase(args.get("type"));
			});
		}
		
		if(args.containsKey("gamemode")) {
			entities.removeIf((e) -> {
				if(e instanceof Player)
					return !((Player) e).getGameMode().name().equalsIgnoreCase(args.get("gamemode"));
				return false;
			});
		}
		
		return entities;
	}
}
