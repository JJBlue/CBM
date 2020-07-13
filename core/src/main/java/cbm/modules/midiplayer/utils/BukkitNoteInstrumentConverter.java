package cbm.modules.midiplayer.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;

public class BukkitNoteInstrumentConverter {
	public static Note getNote(int octave, MidiNote midiNote) {
		if(octave < 0) octave = 0;
		if(octave > 1) octave = 1;
		
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
		/*
Honky-tonk
Harpsichord 
Clav.       
Celesta     
Glockenspiel
Music Box   
Vibraphone  
Marimba     
Xylophone   
Tubular-bell
Santur      
Organ 1  
Church Org.1
Reed Organ  
Accordion Fr
Harmonica   
Bandoneon   
Nylon-str.Gt
Steel-str.Gt
Jazz Gt.    
Clean Gt.   
Muted Gt.   
Overdrive Gt
DistortionGt
Gt.Harmonics
Acoustic Bs.
Fingered Bs.
Picked Bs.  
Fretless Bs.
Slap Bass 1
Violin      
Viola       
Cello       
Contrabass  
Tremolo Str 
PizzicatoStr
Harp        
Timpani     
Strings     
Slow Strings
Syn.Strings1
Syn.Strings2
Choir Aahs  
Voice Oohs  
SynVox      
OrchestraHit
Trumpet     
Trombone    
Tuba        
MutedTrumpet
French Horns
Brass 1     
Synth Brass1
Synth Brass2
Soprano Sax 
Alto Sax    
Tenor Sax   
Baritone Sax
Oboe        
English Horn
Bassoon     
Clarinet    
Piccolo     
Flute       
Recorder    
Pan Flute   
Bottle Blow 
Shakuhachi  
Whistle     
Ocarina     
Square Wave 
Saw Wave    
Syn.Calliope
Chiffer Lead
Charang     
Solo Vox    
5th Saw Wave
Bass & Lead 
Fantasia    
Warm Pad    
Polysynth   
Space Voice 
Bowed Glass 
Metal Pad   
Halo Pad    
Sweep Pad   
Ice Rain    
Soundtrack  
Crystal     
Atmosphere  
Brightness  
Goblin      
Echo Drops  
Star Theme  
Sitar       
Shamisen    
Koto        
Kalimba     
Bagpipe     
Fiddle      
Shanai      
Tinkle Bell 
Agogo       
Steel Drums 
Woodblock   
Taiko       
Melo. Tom 1 
Synth Drum  
Reverse Cym.
Gt.FretNoise
Breath Noise
Seashore    
Bird        
Telephone 1 
Helicopter  
Applause    
Gun Shot    
SynthBass101
Trombone 2  
Fr.Horn 2   
Square      
Saw         
Syn Mallet  
Echo Bell   
Sitar 2     
Gt.Cut Noise
Fl.Key Click
Rain        
Dog         
Telephone 2 
Car-Engine  
Laughing    
Machine Gun 
Echo Pan    
String Slap 
Thunder     
Horse-Gallop
DoorCreaking
Car-Stop    
Screaming   
Lasergun    
Wind        
Bird 2      
Door        
Car-Pass    
Punch       
Explosion   
Stream      
Scratch     
Car-Crash   
Heart Beat  
Bubble      
Siren       
Footsteps   
Train       
Jetplane     
Honky-tonk  
Detuned EP 1
Detuned EP 2
Coupled Hps.
Vibraphone  
Marimba     
Church Bell 
Detuned Or.1
Detuned Or.2
Church Org.2
Accordion It
Ukulele     
12-str.Gt   
Hawaiian Gt.
Chorus Gt.  
Funk Gt.    
Feedback Gt.
Gt. Feedback
Synth Bass 3
Synth Bass 4
Slow Violin 
Orchestra   
Syn.Strings3
Brass 2     
Sine Wave   
Doctor Solo 
Taisho Koto 
Castanets   
Concert BD  
Melo. Tom 2 
808 Tom     
Starship    
Carillon    
Elec Perc.  
Burst Noise 
Harpsichord 
60's Organ 1
Church Org.3
Nylon Gt.o  
Mandolin    
Funk Gt.2   
Rubber Bass 
AnalogBrass1
AnalogBrass2
Harpsi.o    
Organ 4     
Organ 5     
Nylon Gt.2  
Choir Aahs 2
Standard    
Room        
Power       
Electronic  
TR-808      
Jazz        
Brush       
Orchestra
		 */
	
	protected static Map<Pattern, Instrument> instruments;
	
	static {
		instruments = new HashMap<>();
		
		addInstrument(".*piano.*", Instrument.PIANO);
		addInstrument(".*banjo.*", Instrument.BANJO);
		addInstrument(".*(drum|fantasia).*", Instrument.BASS_DRUM);
		addInstrument(".*snare.*", Instrument.SNARE_DRUM);
		addInstrument(".*bass.*guitare.*", Instrument.BASS_GUITAR); //TODO
		addInstrument(".*guitare.*", Instrument.GUITAR);
		addInstrument(".*cow.*bell.*", Instrument.COW_BELL); //TODO
		addInstrument(".*bell.*", Instrument.BELL);
		addInstrument(".*bit.*", Instrument.BIT);
		addInstrument(".*chime.*", Instrument.CHIME);
		addInstrument(".*didgeridoo.*", Instrument.DIDGERIDOO);
		addInstrument(".*flute.*", Instrument.FLUTE);
		addInstrument(".*pling.*", Instrument.PLING);
		addInstrument(".*sticks.*", Instrument.STICKS);
		addInstrument(".*iron.*xylophone.*", Instrument.IRON_XYLOPHONE); //TODO
		addInstrument(".*xylophone.*", Instrument.XYLOPHONE);
	}
	
	public static void addInstrument(String regex, Instrument instrument) {
		Pattern pattern = Pattern.compile(regex);
		instruments.put(pattern, instrument);
	}
	
	public static Instrument getInstrument(String name) {
		if(name == null) return Instrument.PIANO;
		
		String iname = name.toLowerCase();
		
		Pattern p = instruments.keySet().stream().parallel()
			.filter(pattern -> pattern.matcher(iname).matches())
			.findFirst().orElse(null);
		
		return p == null ? Instrument.PIANO : instruments.get(p);
	}
	
	/*
	 * Strings
	 * Harp
	 * PizzicatoStr
	 * Contrabass
	 * clarinet
	 * Celesta
	 * Trombone
	 * Alto Sax
	 * Choir Aahs
	 * Trumpet
	 * Tuba
	 * OrchestraHit
	 * Timpani
	 * percussions
	 */
	public static Instrument getInstrument(javax.sound.midi.Instrument instrument) {
		return getInstrument(instrument.getName());
	}
}
