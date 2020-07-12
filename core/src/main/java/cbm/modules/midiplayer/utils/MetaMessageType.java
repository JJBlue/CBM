package cbm.modules.midiplayer.utils;

import java.nio.charset.StandardCharsets;

import javax.sound.midi.MetaMessage;

public class MetaMessageType {
	public final static int sequence_number = 0x00;
	public final static int text = 0x01;
	public final static int copyright = 0x02;
	public final static int instrument_name = 0x03; //TODO don't know if 0x03 or 0x04
	public final static int track_name = 0x04; //TODO don't know if 0x03 or 0x04
	public final static int lyrics = 0x05;
	public final static int marker = 0x06;
	public final static int cue_marker = 0x07;
	public final static int device_name = 0x09;
	public final static int channel_prefix = 0x20;
	public final static int midi_port = 0x21;
	public final static int end_of_track = 0x2f;
	public final static int set_tempo = 0x51;
	public final static int smpte_offset = 0x54;
	public final static int time_signature = 0x58; // numerator, denominator, clocks_per_click, notated_32nd_notes_per_beat, time
	public final static int key_signature  = 0x59;
	public final static int sequencer_specific = 0x7f;
	
	public static int getSequenceNumber(MetaMessage message) {
		byte[] data = message.getData();
		return ((data[0] & 0xFF) << 8) | (data[1] & 0xFF); //0-65535
	}
	
	public static String getText(MetaMessage message) {
		return getString(message);
	}
	
	public static String getCopyright(MetaMessage message) {
		return getString(message);
	}
	
	public static String getTrackName(MetaMessage message) {
		return getString(message);
	}
	
	public static String getInstrumentName(MetaMessage message) {
		return getString(message);
	}
	
	public static String getLyricsName(MetaMessage message) {
		return getString(message);
	}
	
	public static String getMarkerName(MetaMessage message) {
		return getString(message);
	}
	
	public static String getCueMarkerName(MetaMessage message) {
		return getString(message);
	}
	
	public static String getDeviceName(MetaMessage message) {
		return getString(message);
	}
	
	public static int getChannelPrefix(MetaMessage message) {
		byte[] data = message.getData();
		return data[0] & 0xFF; //0-255
	}
	
	public static int getMidiPort(MetaMessage message) {
		byte[] data = message.getData();
		return data[0] & 0xFF; //0-255
	}
	
	public static int getTempo(MetaMessage message) {
		byte[] data = message.getData();
		int value = (data[0] & 0xff) << 16 | (data[1] & 0xff) << 8 | (data[2] & 0xff); //0-16777215
		
		if(value < 0 || value > 16_777_215)
			return 500000;
		return value;
	}
	
	public static int getBPM(MetaMessage message) {
		return getBPM(getTempo(message));
	}
	
	public static int getBPM(int tempo) {
		return 60_000_000 / tempo;
	}
	
	/*
	 	frame_rate	24, 25, 29.97, 30	Default: 24
		hours	0..255
		minutes	0..59
		seconds	0..59
		frames	0..255
		sub_frames	0..99
	 */
	public static String getSmpteOffsetAsString(MetaMessage message) {
		byte[] data = message.getData();
		return "SMTPE Offset: " + (data[0] & 0xFF) + ":" + (data[1] & 0xFF) + ":" + (data[2] & 0xFF) + "." + (data[3] & 0xFF) + "." + (data[4] & 0xFF);
	}
	
	public static int getSmpteOffset(MetaMessage message) { //TODO
		byte[] data = message.getData();
		int value = (data[0] & 0xFF) << 32 | (data[1] & 0xFF) << 24 | (data[2] & 0xFF) << 16 | (data[3] & 0xFF) << 8 | (data[4] & 0xFF);
		if(value == 0)
			return 24;
		return value;
	}
	
	public static int getFrameRate(MetaMessage message) { //TODO
		return 24;
	}
	
	public static int getHours(MetaMessage message) { //TODO
		byte[] data = message.getData();
		return (data[0] & 0xFF);
	}
	
	public static int getMinutes(MetaMessage message) { //TODO
		byte[] data = message.getData();
		return (data[1] & 0xFF);
	}
	
	public static int getSeconds(MetaMessage message) { //TODO
		byte[] data = message.getData();
		return (data[2] & 0xFF);
	}
	
	public static int getFrames(MetaMessage message) { //TODO
		byte[] data = message.getData();
		return (data[3] & 0xFF);
	}
	
	public static int getSubFrames(MetaMessage message) { //TODO
		byte[] data = message.getData();
		return (data[4] & 0xFF);
	}
	
	/*
	 	numerator	0..255	Default: 4
		denominator	1..2**255	Default: 4
		clocks_per_click	0..255	Default: 24
		notated_32nd_notes_per_beat	0..255	Default: 8
	 */
	public static String getTimeSignatureAsString(MetaMessage message) {
		byte[] data = message.getData();
		return "Time Signature: " + (data[0] & 0xFF) + "/" + (1  << (data[1] & 0xFF)) + ", MIDI clocks per metronome tick: " + (data[2] & 0xFF) + ", 1/32 per 24 MIDI clocks: " + (data[3] & 0xFF);
	}
	
	public static int getTimeSignature(MetaMessage message) {
		return getNumerator(message) << 24 | getDenominator(message) << 16 | getClocksPerClick(message) << 8 | getNotated32ndNotesPerBeat(message);
	}
	
	public static int getNumerator(MetaMessage message) { // beats/bar  : 4/4 6/8 ...
		byte[] data = message.getData();
		int value = data[0] & 0xFF;
		if(value == 0)
			return 4;
		return value;
	}
	
	public static int getDenominator(MetaMessage message) { // beats/whole-note
		byte[] data = message.getData();
		int value =  1 << (data[1] & 0xFF);
		if(value == 0)
			return 4;
		return value;
	}
	
	public static int getClocksPerClick(MetaMessage message) {
		byte[] data = message.getData();
		int value =  data[2] & 0xFF;
		if(value == 0)
			return 24;
		return value;
	}
	
	public static int getNotated32ndNotesPerBeat(MetaMessage message) {
		byte[] data = message.getData();
		if(data.length < 4) return 8;
		int value =  data[3] & 0xFF;
		if(value == 0)
			return 8;
		return value;
	}
	
	/*  */
	
	public static byte[] getSequencerSpecific(MetaMessage message) {
		return message.getData();
	}
	
	public static String getString(MetaMessage message) {
		return new String(message.getData(), StandardCharsets.UTF_8);
	}
}
