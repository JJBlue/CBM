package essentials.modules.player.utils;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;

public class BukkitNoteInstrumentConverter {
	public static Note getNote(int octave, MidiNote midiNote) {
		if(octave < 0 || octave > 1)
			octave = 0;
		
		switch (midiNote) {
			case A:
				return Note.natural(octave, Tone.A);
			case A_Sharp:
				return Note.sharp(octave, Tone.A);
			case C:
				return Note.natural(octave, Tone.C);
			case C_Sharp:
				return Note.sharp(octave, Tone.C);
			case D:
				return Note.natural(octave, Tone.D);
			case D_Sharp:
				return Note.sharp(octave, Tone.D);
			case E:
				return Note.natural(octave, Tone.E);
			case F:
				return Note.natural(octave, Tone.F);
			case F_Sharp:
				return Note.sharp(octave, Tone.F);
			case G:
				return Note.natural(octave, Tone.G);
			case G_Sharp:
				return Note.sharp(octave, Tone.G);
			case H:
				return Note.natural(octave, Tone.B);
		}
		return null;
	}
	
	public static Instrument getInstrument(javax.sound.midi.Instrument instrument) {
		return Instrument.PIANO; //TODO
	}
}
