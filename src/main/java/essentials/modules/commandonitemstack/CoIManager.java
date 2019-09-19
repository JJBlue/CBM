package essentials.modules.commandonitemstack;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import essentials.modules.commandonobject.CommandAusfuehren;
import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class CoIManager {
	public static void addCommand(ItemStack itemStack, CoIAction action, String command) {
		List<String> commands = getCommands(itemStack, action);
		commands.add(command);
		setCommands(itemStack, action, commands);
	}
	
	public static void removeCommand(ItemStack itemStack, CoIAction action, String command) {
		List<String> commands = getCommands(itemStack, action);
		if(commands.remove(command))
			setCommands(itemStack, action, commands);
	}
	
	public static void clearCommands(ItemStack itemStack, CoIAction action) {
		List<String> commands = getCommands(itemStack, action);
		if(commands.isEmpty()) return;
		commands.clear();
		setCommands(itemStack, action, commands);
	}
	
	public static void execute(Entity entity, ItemStack itemStack, CoIAction action) {
		List<String> commands = getCommands(itemStack, action);
		if(commands.isEmpty()) return;
		
		for(String command : commands)
			CommandAusfuehren.Command(entity, command);
	}
	
	public static List<String> getCommands(ItemStack itemStack, CoIAction action) {
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag == null || !nbtTag.hasNBT()) return new LinkedList<>();
		
		Object v = nbtTag.getValue("commands" + (action.equals(CoIAction.DEFAULT) ? "" : "-" + action.value));
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
	public static void setCommands(ItemStack itemStack, CoIAction action, List<String> commands) {
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag == null || !nbtTag.hasNBT())
			nbtTag = NBTUtilities.createNBTTag();
		
		if(commands == null || commands.isEmpty()) {
			nbtTag.remove("commands" + (action.equals(CoIAction.DEFAULT) ? "" : "-" + action.value));
			NBTUtilities.setNBTTagCompound(itemStack, nbtTag.getNBTTagCompound());
			return;
		}
		
		List<Object> list = (List<Object>) NBTUtilities.createNBTTagList();
		
		for(String s : commands)
			list.add(NBTUtilities.createNBTBase(s));
		
		nbtTag.set("commands" + (action.equals(CoIAction.DEFAULT) ? "" : "-" + action.value), list);
		NBTUtilities.setNBTTagCompound(itemStack, nbtTag.getNBTTagCompound());
	}
}
