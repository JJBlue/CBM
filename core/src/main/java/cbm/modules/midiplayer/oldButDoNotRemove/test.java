package cbm.modules.midiplayer.oldButDoNotRemove;

import java.io.File;

import cbm.modules.midiplayer.BukkitMidiPlayerManager;

public class test {
	public static void main(String[] args) {
		File[] files = new File(".").listFiles();
		File file = files[0];
		System.out.println(file);
		
//		MidiPlayerOldHelperDoNotRemove.play(file);
		BukkitMidiPlayerManager.play(file);
	}
}
