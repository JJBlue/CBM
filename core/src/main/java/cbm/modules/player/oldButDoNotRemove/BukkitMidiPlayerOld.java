package cbm.modules.player.oldButDoNotRemove;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.entity.Player;

import cbm.modules.player.utils.BukkitNoteInstrumentConverter;
import cbm.modules.player.utils.MetaMessageType;
import cbm.modules.player.utils.MidiNote;

public class BukkitMidiPlayerOld {
	public final File file;
	public final Sequence sequence;
	
	private Thread thread;
	private boolean running;
	
	private long tick;
	private long sleep;
	
	private Map<Long, List<MidiMessage>> ticks = new HashMap<>();
	
	public BukkitMidiPlayerOld(File file) throws InvalidMidiDataException, IOException {
		this.file = file;
    	sequence = MidiSystem.getSequence(file);
    	sort();
	}
	
	private void sort() {
		for (Track track : sequence.getTracks()) {
    		for (int i = 0; i < track.size(); i++) {
    			MidiEvent event = track.get(i);
    			
    			long tick = event.getTick();
    			
    			List<MidiMessage> messages = ticks.get(tick);
    			if(messages == null) {
    				messages = new LinkedList<>();
    				ticks.put(tick, messages);
    			}
    			
    			messages.add(event.getMessage());
    		}
		}
	}
	
	private void setAndNextTicks() {
		if(!ticks.containsKey(tick)) {
			tick++;
			return;
		}
		
		for(MidiMessage message : ticks.get(tick)) {
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
    					
    					playNote(octave, noteName);
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
    					running = false;
    					break;
    				case MetaMessageType.set_tempo:
        				sleep = MetaMessageType.getTempo(metaMessage);
        				
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
		
		tick++;
	}
	
	private void playNote(int octave, MidiNote note) {
		for(Player player : Bukkit.getOnlinePlayers())
			playNote(player, octave, note);
	}
	
	private void playNote(Player player, int octave, MidiNote note) {
		player.playNote(player.getLocation(), Instrument.PIANO, BukkitNoteInstrumentConverter.getNote(octave, note));
	}
	
	public synchronized void start() {
		if(thread != null) return;
		
		running = true;
		
		sleep = 500_000; //in nano, default value
		
		thread = new Thread(() -> {
			long time = 0;
			
			while(running) {
				if(System.nanoTime() - time < sleep) continue;
				time = System.nanoTime();
				
				setAndNextTicks();
			}
			thread = null;
		});
		
		thread.start();
	}
	
	public synchronized void stop() {
		if(thread == null) return;
		
		thread.interrupt();
		running = false;
	}
}
