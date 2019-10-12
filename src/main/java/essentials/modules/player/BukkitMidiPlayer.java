package essentials.modules.player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		
		for (Track track : sequence.getTracks()) {
			BukkitTrack bukkitTrack = new BukkitTrack(track);
    		tracks.add(bukkitTrack);
    		
    		if(bukkitTrack.maxTick > maxTicks)
    			maxTicks = bukkitTrack.maxTick;
		}
		
		instruments = new HashMap<>();
	}
	
	private void setAndNextTicks() {
		for(BukkitTrack track : tracks)
			track.execute(tick, this);
		
		tick++;
	}
	
	public void playNote(Note note) {
		playNote(Instrument.PIANO, note); //TODO instrument
	}
	
	public void playNote(Instrument instrument, Note note) {
		for(Player player : Bukkit.getOnlinePlayers())
			playNote(player, instrument, note);
	}
	
	public void playNote(Player player, Instrument instrument, Note note) {
		player.playNote(player.getLocation(), instrument, note);
	}
	
	public void setInstrument(BukkitTrack track, Instrument instrument) {
		//TODO
	}
	
	public void getInstrument() {
		
	}
	
	public void changeTempo(int tempo) {
		sleep = tempo;
	}
	
	//TODO how get a thread, which does not use 8% and higher of the cpu
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
