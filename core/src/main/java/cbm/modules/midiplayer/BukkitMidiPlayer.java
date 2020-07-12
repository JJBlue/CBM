package cbm.modules.midiplayer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

import cbm.modules.midiplayer.utils.MetaMessageType;
import cbm.modules.midiplayer.values.BukkitMidiTimeSignature;

public class BukkitMidiPlayer {
	public final int id;
	
	public final File file;
	public final Sequence sequence;
	
	private Thread thread;
	private boolean running;
	
	private long tick;
	private long maxTicks;
	
	private long sleep;
	private int tempo;
	private BukkitMidiTimeSignature time_signature;
	
	public List<BukkitTrack> tracks;
	public Map<BukkitTrack, Instrument> instruments;
	
	public BukkitMidiPlayer(int id, File file) throws InvalidMidiDataException, IOException {
		this.id = id;
		this.file = file;
    	sequence = MidiSystem.getSequence(file);
    	load();
	}
	
	private void load() {
		tracks = new LinkedList<>();
		instruments = new HashMap<>();
		
		for (Track track : sequence.getTracks()) {
			BukkitTrack bukkitTrack = new BukkitTrack(track);
    		tracks.add(bukkitTrack);
    		instruments.put(bukkitTrack, Instrument.PIANO);
    		
    		if(bukkitTrack.maxTick > maxTicks)
    			maxTicks = bukkitTrack.maxTick;
		}
	}
	
	private void setAndNextTicks() {
		// TODO sequence.getResolution() -> sequence.getDivisionType() == 0 not 1
		long ticks = (long) (sleep * (MetaMessageType.getBPM(tempo) * sequence.getResolution() / (60_000_000_000D)));
		
		for(int i = 0; i < ticks && tick <= maxTicks; i++) {
			activateTick(tick);
			tick++;
		}
		
		if(tick > maxTicks) {
			running = false;
			BukkitMidiPlayerManager.remove(id);
		}
	}
	
	private void activateTick(long tick) {
		tracks.forEach(track -> track.execute(tick, this));
	}
	
	public void playNote(BukkitTrack track, Note note) {
		playNote(instruments.get(track), note);
	}
	
	public void playNote(Instrument instrument, Note note) {
//		for(Player player : Bukkit.getOnlinePlayers())
//			playNote(player, instrument, note);
	}
	
	public void playNote(Player player, Instrument instrument, Note note) {
		player.playNote(player.getLocation(), instrument, note);
	}
	
	public void setInstrument(BukkitTrack track, Instrument instrument) {
		instruments.put(track, instrument);
	}
	
	public void changeTempo(BukkitTrack track, int tempo) {
		this.tempo = tempo;
		sleep = 1_000_000_000 / time_signature.clocksperclick; // ns
	}
	
	public void setTimeSignature(BukkitTrack track, BukkitMidiTimeSignature time_signature) {
		this.time_signature = time_signature;
	}
	
	public synchronized void start() {
		if(thread != null) return;
		
		running = true;
		sleep = 500_000_000; //in nano, default value
		
		thread = new Thread(() -> {
			activateTick(tick);
			tick++;
			
			while(running) {
				LockSupport.parkNanos(sleep);
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
