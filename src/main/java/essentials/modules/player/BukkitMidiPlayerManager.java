package essentials.modules.player;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;

public class BukkitMidiPlayerManager {
	private static Map<Integer, BukkitMidiPlayer> players = Collections.synchronizedMap(new HashMap<>());
	private static int counter = 1;
	
	public static int play(File file) {
		try {
			BukkitMidiPlayer player = new BukkitMidiPlayer(file);
			int ID = getFreeID();
			if(ID < 0) return ID;
			
			players.put(ID, player);
			player.start();
			return ID;
		} catch (InvalidMidiDataException | IOException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public static void stop(int ID) {
		BukkitMidiPlayer player = players.remove(ID);
		if(player == null) return;
		player.stop();
	}
	
	public synchronized static int getFreeID() {
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
