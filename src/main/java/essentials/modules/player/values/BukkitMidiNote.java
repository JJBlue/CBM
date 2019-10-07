package essentials.modules.player.values;

import org.bukkit.Note;

public class BukkitMidiNote implements BukkitMidiValue {
	public final Note note;
	
	public BukkitMidiNote(Note note) {
		this.note = note;
	}
}
