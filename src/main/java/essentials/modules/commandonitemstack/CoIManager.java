package essentials.modules.commandonitemstack;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import essentials.modules.commandonobject.CommandAusfuehren;
import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class CoIManager {
	public static void addCommand(ItemStack itemStack, String command) {
		List<String> commands = getCommands(itemStack);
		commands.add(command);
		setCommands(itemStack, commands);
	}
	
	public static void removeCommand(ItemStack itemStack, String command) {
		List<String> commands = getCommands(itemStack);
		if(commands.remove(command))
			setCommands(itemStack, commands);
	}
	
	public static void clearCommands(ItemStack itemStack) {
		List<String> commands = getCommands(itemStack);
		if(commands.isEmpty()) return;
		commands.clear();
		setCommands(itemStack, commands);
	}
	
	public static void execute(Player player, ItemStack itemStack) {
		List<String> commands = getCommands(itemStack);
		if(commands.isEmpty()) return;
		
		for(String command : commands)
			CommandAusfuehren.Command(player, command);
	}
	
	public static List<String> getCommands(ItemStack itemStack) {
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag == null || !nbtTag.hasNBT()) return new LinkedList<>();
		
		Object v = nbtTag.getValue("commands");
		if(!(v instanceof List<?>)) return new LinkedList<>();
		
		List<String> commands = new LinkedList<>();
		for(Object obj : (List<?>) v) {
			Object value = NBTUtilities.getValue(obj);
			if(!(value instanceof String)) continue;
			
			commands.add((String) value);
		}
		
		return commands;
	}
	
	@SuppressWarnings("unchecked")
	public static void setCommands(ItemStack itemStack, List<String> commands) {
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag == null || !nbtTag.hasNBT())
			nbtTag = NBTUtilities.createNBTTag();
		
		List<Object> list = (List<Object>) NBTUtilities.createNBTTagList();
		
		for(String s : commands)
			list.add(NBTUtilities.createNBTBase(s));
		
		nbtTag.set("commands", list);
		NBTUtilities.setNBTTagCompound(itemStack, nbtTag.getNBTTagCompound());
	}
}
