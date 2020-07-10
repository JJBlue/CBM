package cbm.modules.midiplayer.values;

public class BukkitMidiTimeSignature implements BukkitMidiValue {
	public long numerator;
	public long denominator;
	public long clocksperclick;
	public long notated32ndNotesPerBeat;
	
	public BukkitMidiTimeSignature(long numerator, long denominator, long clocksperclick, long notated32ndNotesPerBeat) {
		this.numerator = numerator;
		this.denominator = denominator;
		this.clocksperclick = clocksperclick;
		this.notated32ndNotesPerBeat = notated32ndNotesPerBeat;
	}
	
	@Override
	public String toString() {
		return "Numerator: " + numerator + " denominator: " + denominator + " clocksperclick: " + clocksperclick + " notated32ndNotesPerBeat: " + notated32ndNotesPerBeat;
	}
}
