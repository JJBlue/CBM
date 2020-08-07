package cbm.modules.midiplayer;

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

import cbm.modules.midiplayer.utils.BukkitNoteInstrumentConverter;
import cbm.modules.midiplayer.utils.MetaMessageType;
import cbm.modules.midiplayer.utils.MidiNote;
import cbm.modules.midiplayer.values.BukkitMidiInstrument;
import cbm.modules.midiplayer.values.BukkitMidiNote;
import cbm.modules.midiplayer.values.BukkitMidiTempo;
import cbm.modules.midiplayer.values.BukkitMidiTimeSignature;
import cbm.modules.midiplayer.values.BukkitMidiValue;

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
							
							System.out.println("!" + instrument.getName() + ": " + BukkitNoteInstrumentConverter.getInstrument(instrument));
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
    					System.out.print("Command:" + sm.getCommand());
    				}
				}

			} else if(message instanceof MetaMessage) {
				MetaMessage metaMessage = (MetaMessage) message;
				metaMessage.getData();
				
				switch (metaMessage.getType()) {
					case MetaMessageType.sequence_number:
						break;
					case MetaMessageType.text:
						System.out.println("Text: " + MetaMessageType.getText(metaMessage));
						break;
    				case MetaMessageType.copyright:
//    					System.out.println("Copyright: " + MetaMessageType.getCopyright(metaMessage));
    					break;
    				case MetaMessageType.track_name:
    					System.out.println("track_name: " + MetaMessageType.getTrackName(metaMessage));
    					break;
    				case MetaMessageType.instrument_name:
    					System.out.println("instrument_name: " + MetaMessageType.getInstrumentName(metaMessage));
    					break;
    				case MetaMessageType.lyrics: // Song text
//    					System.out.println("lyrics: " + MetaMessageType.getLyricsName(metaMessage));
    					break;
    				case MetaMessageType.marker:
//    					System.out.println("marker: " + MetaMessageType.getMarkerName(metaMessage));
    					break;
    				case MetaMessageType.cue_marker:
//    					System.out.println("cue_marker: " + MetaMessageType.getCueMarkerName(metaMessage));
    					break;
    				case MetaMessageType.device_name:
//    					System.out.println("device_name: " + MetaMessageType.getDeviceName(metaMessage));
    					break;
    				case MetaMessageType.channel_prefix:
//    					System.out.println("channel_prefix: " + MetaMessageType.getChannelPrefix(metaMessage));
    					break;
    				case MetaMessageType.midi_port:
    					break;
    				case MetaMessageType.end_of_track:
//    					System.out.println("end_of_track");
    					break;
    				case MetaMessageType.set_tempo:
    					getOrCreateList(tick).add(new BukkitMidiTempo(MetaMessageType.getTempo(metaMessage)));
    					break;
    				case MetaMessageType.smpte_offset:
//    					System.out.println("smpte_offset: " + MetaMessageType.getSmpteOffset(metaMessage));
    					break;
    				case MetaMessageType.time_signature:
    					getOrCreateList(tick).add(
							new BukkitMidiTimeSignature(
								MetaMessageType.getNumerator(metaMessage),
								MetaMessageType.getDenominator(metaMessage),
								MetaMessageType.getClocksPerClick(metaMessage),
								MetaMessageType.getNotated32ndNotesPerBeat(metaMessage)
							)
    					);
    					break;
    				case MetaMessageType.key_signature:
    					break;
    				case MetaMessageType.sequencer_specific:
    					System.out.println("sequencer_specific" + MetaMessageType.getSequencerSpecific(metaMessage));
    					break;
				}
			} else {
				System.out.println("MidiMessage type is unknown");
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
		
		ticks.get(tick).forEach(value -> {
			if(value instanceof BukkitMidiNote) {
				bukkitMidiPlayer.playNote(this, ((BukkitMidiNote) value).note);
			} else if(value instanceof BukkitMidiInstrument) {
				bukkitMidiPlayer.setInstrument(this, ((BukkitMidiInstrument) value).instrument);
			} else if(value instanceof BukkitMidiTempo) {
				bukkitMidiPlayer.changeTempo(this, ((BukkitMidiTempo) value).tempo);
			} else if(value instanceof BukkitMidiTimeSignature) {
				bukkitMidiPlayer.setTimeSignature(this, (BukkitMidiTimeSignature) value);
			}
		});
	}
}
