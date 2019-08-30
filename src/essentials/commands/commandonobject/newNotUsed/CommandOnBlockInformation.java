package essentials.commands.commandonobject.newNotUsed;

import java.util.LinkedList;
import java.util.List;

import com.sun.jdi.Location;

public class CommandOnBlockInformation {
	private final Location location;
	private List<String> commands;
	
	public CommandOnBlockInformation(Location location) {
		this.location = location;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public synchronized List<String> getCommands(){
		if(commands != null)
			return commands;
		
		commands = new LinkedList<>();
		
		//TODO load
		
		return commands;
	}
	
	public void addCommand(String command) {
		commands.add(command);
	}
	
	public void removeCommand(String command) {
		commands.remove(command);
	}
	
	public void save() {
		
	}
}
