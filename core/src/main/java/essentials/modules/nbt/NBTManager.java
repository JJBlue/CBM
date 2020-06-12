package essentials.modules.nbt;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import components.json.JSONObject;
import components.json.abstractJSON;
import components.json.parser.JSONParser;
import essentials.utilitiesvr.nbt.NBTTag;
import essentials.utilitiesvr.nbt.NBTUtilities;

public class NBTManager {
	public static void listNBT(CommandSender sender, ItemStack itemStack) {
		StringBuilder builder = new StringBuilder();
		builder.append("§e-----------------------------------------------------");
		listNBT(builder, NBTUtilities.getNBTTag(itemStack), 0);
		builder.append("\n§e-----------------------------------------------------");
		
		sender.sendMessage(builder.toString());
	}
	
	public static void listNBT(StringBuilder builder, NBTTag nbtTag, int spaces) {
		if(!nbtTag.hasNBT()) return;
		
		Set<String> set = nbtTag.getKeys();
		if(set == null) return;
		
		List<String> keys = set.stream().collect(Collectors.toList());
		keys.sort(Comparator.naturalOrder());
		
		StringBuilder spacesBuilder = new StringBuilder();
		for(int i = 0; i < spaces; i++)
			spacesBuilder.append("  ");
		
		for(String key : keys) {
			Object value = nbtTag.getValue(key);
			listNBTBase(builder, key, value, spaces, spacesBuilder, true);
		}
	}
	
	public static void listNBTBase(StringBuilder builder, String key, Object value, int spacesCount, StringBuilder spaces, boolean newLine) {
		if(value instanceof NBTTag) {
			listNBT(builder, (NBTTag) value, spacesCount++);
			return;
		}
		
		if(newLine)
			builder.append("\n§6" + spaces.toString() + key + " ");
		
		if(value instanceof List) {
			builder.append("(List):");
			
			for(Object obj : (List<?>) value) {
				builder.append("\n§6" + spaces.toString() + "- ");
				obj = NBTUtilities.getValue(obj);
				listNBTBase(builder, "", obj, spacesCount + 1, spaces, obj instanceof NBTTag || obj instanceof List);
			}
		} else if(value instanceof Byte)
			builder.append("(Byte): §f" + value);
		else if(value instanceof byte[])
			builder.append("(Byte[]): §f" + Arrays.toString((byte[]) value));
		else if(value instanceof Double)
			builder.append("(Double): §f" + value);
		else if(value instanceof Float)
			builder.append("(Float): §f" + value);
		else if(value instanceof Integer)
			builder.append("(Int): §f" + value);
		else if(value instanceof int[])
			builder.append("(Int[]): §f" + Arrays.toString((int[]) value));
		else if(value instanceof Long)
			builder.append("(Long): §f" + value);
		else if(value instanceof long[])
			builder.append("(Long[]): §f" + Arrays.toString((long[]) value));
		else if(value instanceof Short)
			builder.append("(Short): §f" + value);
		else if(value instanceof String) {
			if(key != null && !key.isEmpty())
				builder.append(": ");
			
			try {
				abstractJSON aJSON = JSONParser.parse((String) value);
				
				if(aJSON instanceof JSONObject) {
					JSONObject jsonObject = (JSONObject) aJSON;
					if(jsonObject.size() == 1  && jsonObject.getString("text") != null)
						builder.append("§f" + jsonObject.getString("text"));
				}
				
				return;
			} catch (Exception e) {}
			
			builder.append("§f" + value);
		}
	}
}
