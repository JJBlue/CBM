package cbm.modules.player;

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

public class BukkitMidiPlayer {
	public final File file;
	public final Sequence sequence;
	
	private Thread thread;
	private boolean running;
	
	private long tick;
	private long maxTicks;
	private long sleep;
	
	public List<BukkitTrack> tracks;
	public Map<BukkitTrack, Instrument> instruments;
	
	public BukkitMidiPlayer(File file) throws InvalidMidiDataException, IOException {
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
		for(BukkitTrack track : tracks)
			track.execute(tick, this);
		
		tick++;
	}
	
	public void playNote(BukkitTrack track, Note note) {
		playNote(instruments.get(track), note);
	}
	
	public void playNote(Instrument instrument, Note note) {
		for(Player player : Bukkit.getOnlinePlayers())
			playNote(player, instrument, note);
	}
	
	public void playNote(Player player, Instrument instrument, Note note) {
		player.playNote(player.getLocation(), instrument, note);
	}
	
	public void setInstrument(BukkitTrack track, Instrument instrument) {
		instruments.put(track, instrument);
	}
	
	public void changeTempo(int tempo) {
		Bukkit.broadcastMessage("Changed tempo: " + tempo + " (" + (tempo / 1000000) + "ms)");
		sleep = tempo;
	}
	
	//TODO how get a thread, which does not use 8% and higher of the cpu
	// /cbm midiplayer play midi/SuperMario64-Medley.mid
	public synchronized void start() {
		if(thread != null) return;
		
		running = true;
		sleep = 500_000; //in nano, default value
		
		thread = new Thread(() -> {
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
