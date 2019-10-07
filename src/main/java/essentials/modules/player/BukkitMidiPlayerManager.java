package essentials.modules.player;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;

public class BukkitMidiPlayerManager {
	private static Map<Integer, BukkitMidiPlayer> players = Collections.synchronizedMap(new HashMap<>());
	
	public static int play(File file) {
		try {
			BukkitMidiPlayer player = new BukkitMidiPlayer(file);
			int ID = getFreeID();
			if(ID < 0) return ID;
			
			player.start();
			players.put(getFreeID(), player);
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
	
	public static int getFreeID() {
		if(players.size() >= Integer.MAX_VALUE) return -1;
		
		Random random = new Random();
		int value;
		
		do {
			value = random.nextInt(Integer.MAX_VALUE) + 1;
		} while(players.containsKey(value));
		
		players.put(value, null);
		
		return value;
	}
}
