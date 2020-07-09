package cbm.modules.player.oldButDoNotRemove;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Track;

import cbm.modules.player.utils.MetaMessageType;

public class MidiPlayerOldHelperDoNotRemove {
	
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "H"};

    public static void play(File file) {
    	 try {
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(new BufferedInputStream(new FileInputStream(file)));
			sequencer.start();
		} catch (MidiUnavailableException | IOException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) throws Exception {
    	File file = new File(".\\midi\\SuperMario64-Medley.mid");
    	
    	Soundbank soundbank2 = MidiSystem.getSoundbank(file);
    	System.out.println(soundbank2.getInstruments().length);
    	
    	Soundbank soundbank = MidiSystem.getSynthesizer().getDefaultSoundbank();
    	Sequence sequence = MidiSystem.getSequence(file);

    	int trackNumber = 0;
    	for (Track track : sequence.getTracks()) {
    		trackNumber++;

    		System.out.println("Track " + trackNumber + ": size = " + track.size());

    		for (int i = 0; i < track.size(); i++) {
    			MidiEvent event = track.get(i);
    			System.out.println();
    			System.out.println("@" + event.getTick() + " ");

//    			if(event.getTick() > 0) continue; //TODO
    			
    			MidiMessage message = event.getMessage();
    			if (message instanceof ShortMessage) {
    				ShortMessage sm = (ShortMessage) message;
    				System.out.print("Channel: " + sm.getChannel() + " ");

    				switch(sm.getCommand()) {
	    				case ShortMessage.NOTE_ON: {
	    					int key = sm.getData1();
	    					int octave = (key / 12) - 1;
	    					int note = key % 12;
	    					String noteName = NOTE_NAMES[note];
	    					int velocity = sm.getData2();
	    					System.out.print("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	    					break;
	    				}
	    				case ShortMessage.NOTE_OFF: {
	    					int key = sm.getData1();
	    					int octave = (key / 12) - 1;
	    					int note = key % 12;
	    					String noteName = NOTE_NAMES[note];
	    					int velocity = sm.getData2();
	    					System.out.print("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	    					break;
	    				}
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
	    					System.out.print("Instrument: " + soundbank.getInstruments()[sm.getData1()].getName());
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
	    					break;
	    				}
    				}
    				
    			} else if(message instanceof MetaMessage) {
    				MetaMessage metaMessage = (MetaMessage) message;
    				metaMessage.getData();
    				
    				switch (metaMessage.getType()) {
    					case MetaMessageType.sequence_number:
    						System.out.print("MetaMessage: sequence_number");
    						break;
    					case MetaMessageType.text:
    						System.out.print("MetaMessage: text: " + MetaMessageType.getText(metaMessage));
    						break;
	    				case MetaMessageType.copyright:
	    					System.out.print("MetaMessage: copyright: " + MetaMessageType.getCopyright(metaMessage));
	    					break;
	    				case MetaMessageType.track_name:
	    					System.out.print("MetaMessage: track_name " + MetaMessageType.getTrackName(metaMessage));
	    					break;
	    				case MetaMessageType.instrument_name:
	    					System.out.print("MetaMessage: instrument_name " + MetaMessageType.getInstrumentName(metaMessage));
	    					break;
	    				case MetaMessageType.lyrics:
	    					System.out.print("MetaMessage: lyrics");
	    					break;
	    				case MetaMessageType.marker:
	    					System.out.print("MetaMessage: marker");
	    					break;
	    				case MetaMessageType.cue_marker:
	    					System.out.print("MetaMessage: cue_marker");
	    					break;
	    				case MetaMessageType.device_name:
	    					System.out.print("MetaMessage: device_name " + MetaMessageType.getDeviceName(metaMessage));
	    					break;
	    				case MetaMessageType.channel_prefix:
	    					System.out.print("MetaMessage: channel_prefix");
	    					break;
	    				case MetaMessageType.midi_port:
	    					System.out.print("MetaMessage: midi_port");
	    					break;
	    				case MetaMessageType.end_of_track:
	    					System.out.print("MetaMessage: end_of_track");
	    					break;
	    				case MetaMessageType.set_tempo:
	        				int tempo = MetaMessageType.getTempo(metaMessage);
	        				int bpm = MetaMessageType.getBPM(metaMessage);
	        				
	        				// bpm * ppq = ticks per minute
	        				
	        				// ticks per second = (ticks/quarter-note) * (quarter-note/beat) * (beats/minute) * (minute/second) = PPQN * (4/denominator) * (tempo/60)
	        				// PPQN = (ticks per second) / ( (4/denominator) * (tempo/60) )
	        				
	        				double sleep = 60 / ((bpm/60d) * MetaMessageType.getClocksPerClick(metaMessage));
	        				
	        				System.out.print("MetaMessage: set_tempo " + tempo + " " + bpm  + " " + sleep);
	    					break;
	    				case MetaMessageType.smpte_offset:
	    					System.out.print("MetaMessage: smpte_offset " + Arrays.toString(metaMessage.getData()));
	    					break;
	    				case MetaMessageType.time_signature:
	    					System.out.print("MetaMessage: time_signature");
	    					break;
	    				case MetaMessageType.key_signature:
	    					System.out.print("MetaMessage: key_signature");
	    					break;
	    				case MetaMessageType.sequencer_specific:
	    					System.out.print("MetaMessage: sequencer_specific");
	    					break;
    				}
    			} else {
    				System.out.print("Other message: " + message.getClass());
    			}
    		}
    		
    		System.out.println();
    	}
    }
}
