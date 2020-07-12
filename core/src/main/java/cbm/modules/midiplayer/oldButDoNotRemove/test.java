package cbm.modules.midiplayer.oldButDoNotRemove;

import java.io.File;

import cbm.modules.midiplayer.BukkitMidiPlayerManager;

public class test {
	public static void main(String[] args) {
		File file = new File("J:\\D\\Test", "midi\\LeagueOfLegends-Warriors.mid");
//		MidiPlayerOldHelperDoNotRemove.play(file);
		BukkitMidiPlayerManager.play(file);
	}
}
