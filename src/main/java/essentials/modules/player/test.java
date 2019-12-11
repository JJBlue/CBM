package essentials.modules.player;

import java.io.File;

import essentials.modules.player.oldButDoNotRemove.MidiPlayerOldHelperDoNotRemove;

public class test {
	public static void main(String[] args) {
//		File file = new File(".\\midi\\LeagueOfLegends-Warriors.mid");
		File file = new File(".\\midi\\Simpsons.mid");
		MidiPlayerOldHelperDoNotRemove.play(file);
	}
}
