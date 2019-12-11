package essentials.modules.player.values;

import org.bukkit.Instrument;

public class BukkitMidiInstrument implements BukkitMidiValue {
	public final Instrument instrument;
	
	public BukkitMidiInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
}
