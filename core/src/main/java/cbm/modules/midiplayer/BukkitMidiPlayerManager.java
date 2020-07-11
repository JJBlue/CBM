package cbm.modules.midiplayer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sound.midi.InvalidMidiDataException;

public class BukkitMidiPlayerManager {
	protected static Map<Integer, BukkitMidiPlayer> players = Collections.synchronizedMap(new HashMap<>());
	protected static int counter = 1;
	
	public static int play(File file) {
		try {
			int ID = getFreeID();
			if(ID < 0) return ID;
			
			BukkitMidiPlayer player = new BukkitMidiPlayer(ID, file);
			
			players.put(ID, player);
			player.start();
			return ID;
		} catch (InvalidMidiDataException | IOException e) {}
		
		return -1;
	}
	
	public static void remove(int id) {
		players.remove(id);
	}
	
	public static void stop(int id) {
		BukkitMidiPlayer player = players.remove(id);
		if(player == null) return;
		player.stop();
	}
	
	public static void stopAll() {
		players.values().forEach(player -> player.stop());
	}
	
	public static Collection<Integer> getIDs() {
		return players.keySet().stream().collect(Collectors.toList());
	}
	
	public static BukkitMidiPlayer getBukkitMidiPlayer(int id) {
		return players.get(id);
	}
	
	protected synchronized static int getFreeID() {
		if(players.size() >= Integer.MAX_VALUE) return -1;
		
		do {
			counter++;
			if(counter < 0)
				counter = 1;
		} while(players.containsKey(counter));
		
		players.put(counter, null);
		
		return counter;
	}
}
