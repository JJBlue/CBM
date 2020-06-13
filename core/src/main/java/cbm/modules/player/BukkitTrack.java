package cbm.modules.player;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Track;

import org.bukkit.Note;

import cbm.modules.player.utils.BukkitNoteInstrumentConverter;
import cbm.modules.player.utils.MetaMessageType;
import cbm.modules.player.utils.MidiNote;
import cbm.modules.player.values.BukkitMidiInstrument;
import cbm.modules.player.values.BukkitMidiNote;
import cbm.modules.player.values.BukkitMidiTempo;
import cbm.modules.player.values.BukkitMidiValue;

public class BukkitTrack {
	
	// Tick List<Instrument, Note>
	public Map<Long, List<BukkitMidiValue>> ticks = new HashMap<>();
	public long maxTick = 0;
	
	public BukkitTrack(Track track) {
		load(track);
	}
	
	public void load(Track track) {
		for (int i = 0; i < track.size(); i++) { 
			MidiEvent event = track.get(i);
			long tick = event.getTick();
			
			if(tick > maxTick)
				maxTick = tick;
			
			MidiMessage message = event.getMessage();
			
			if (message instanceof ShortMessage) {
				ShortMessage sm = (ShortMessage) message;

				switch(sm.getCommand()) {
    				case ShortMessage.NOTE_ON: {
    					int key = sm.getData1();
    					int octave = (key / 12) - 1;
    					int note = key % 12;
    					
    					MidiNote noteName = null;
    					switch (note) {
    						case 0:
    							noteName = MidiNote.C;
    							break;
    						case 1:
    							noteName = MidiNote.C_Sharp;
    							break;
    						case 2:
    							noteName = MidiNote.D;
    							break;
    						case 3:
    							noteName = MidiNote.D_Sharp;
    							break;
    						case 4:
    							noteName = MidiNote.E;
    							break;
    						case 5:
    							noteName = MidiNote.F;
    							break;
    						case 6:
    							noteName = MidiNote.F_Sharp;
    							break;
    						case 7:
    							noteName = MidiNote.G;
    							break;
    						case 8:
    							noteName = MidiNote.G_Sharp;
    							break;
    						case 9:
    							noteName = MidiNote.A;
    							break;
    						case 10:
    							noteName = MidiNote.A_Sharp;
    							break;
    						case 11:
    							noteName = MidiNote.H;
    							break;
						}
//    					int velocity = sm.getData2();
    					
    					Note bukkitNote = BukkitNoteInstrumentConverter.getNote(octave, noteName);
    					getOrCreateList(tick).add(new BukkitMidiNote(bukkitNote));
    				}
    				case ShortMessage.NOTE_OFF:
    					break;
    				case ShortMessage.ACTIVE_SENSING:
    					break;
    				case ShortMessage.CHANNEL_PRESSURE:
    					break;
    				case ShortMessage.CONTINUE:
    					break;
    				case ShortMessage.CONTROL_CHANGE:
    					break;
    				case ShortMessage.END_OF_EXCLUSIVE:
    					break;
    				case ShortMessage.MIDI_TIME_CODE:
    					break;
    				case ShortMessage.PITCH_BEND:
    					break;
    				case ShortMessage.POLY_PRESSURE:
    					break;
    				case ShortMessage.PROGRAM_CHANGE:
    					
						try {
							Soundbank soundbank = MidiSystem.getSynthesizer().getDefaultSoundbank();
							javax.sound.midi.Instrument instrument = soundbank.getInstruments()[sm.getData1()];
							
							getOrCreateList(tick).add(new BukkitMidiInstrument(BukkitNoteInstrumentConverter.getInstrument(instrument)));
						} catch (MidiUnavailableException e) {
							e.printStackTrace();
						}
    					
    					break;
    				case ShortMessage.SONG_POSITION_POINTER:
    					break;
    				case ShortMessage.SONG_SELECT:
    					break;
    				case ShortMessage.START:
    					break;
    				case ShortMessage.STOP:
    					break;
    				case ShortMessage.SYSTEM_RESET:
    					break;
    				case ShortMessage.TIMING_CLOCK:
    					break;
    				case ShortMessage.TUNE_REQUEST:
    					break;
    				default: {
    					System.out.println("Command:" + sm.getCommand());
    				}
				}

			} else if(message instanceof MetaMessage) {
				MetaMessage metaMessage = (MetaMessage) message;
				metaMessage.getData();
				
				switch (metaMessage.getType()) {
					case MetaMessageType.sequence_number:
						break;
					case MetaMessageType.text:
						break;
    				case MetaMessageType.copyright:
    					break;
    				case MetaMessageType.track_name:
    					break;
    				case MetaMessageType.instrument_name:
    					break;
    				case MetaMessageType.lyrics:
    					break;
    				case MetaMessageType.marker:
    					break;
    				case MetaMessageType.cue_marker:
    					break;
    				case MetaMessageType.device_name:
    					break;
    				case MetaMessageType.channel_prefix:
    					break;
    				case MetaMessageType.midi_port:
    					break;
    				case MetaMessageType.end_of_track:
    					break;
    				case MetaMessageType.set_tempo:
    					getOrCreateList(tick).add(new BukkitMidiTempo(MetaMessageType.getTempo(metaMessage)));
    					break;
    				case MetaMessageType.smpte_offset:
    					break;
    				case MetaMessageType.time_signature:
    					break;
    				case MetaMessageType.key_signature:
    					break;
    				case MetaMessageType.sequencer_specific:
    					break;
				}
			}
		}
	}
	
	public List<BukkitMidiValue> getOrCreateList(long tick){
		if(ticks.containsKey(tick))
			return ticks.get(tick);
		
		List<BukkitMidiValue> list = new LinkedList<>();
		ticks.put(tick, list);
		return list;
	}
	
	public void execute(long tick, BukkitMidiPlayer bukkitMidiPlayer) {
		if(!ticks.containsKey(tick)) return;
		
		for(BukkitMidiValue value : ticks.get(tick)) {
			if(value instanceof BukkitMidiNote) {
				bukkitMidiPlayer.playNote(((BukkitMidiNote) value).note);
			} else if(value instanceof BukkitMidiInstrument) {
				bukkitMidiPlayer.setInstrument(this, ((BukkitMidiInstrument) value).instrument);
			} else if(value instanceof BukkitMidiTempo) {
				bukkitMidiPlayer.changeTempo(((BukkitMidiTempo) value).tempo);
			}
		}
	}
}
