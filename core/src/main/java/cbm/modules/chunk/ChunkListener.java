package cbm.modules.chunk;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

//TODO
public class ChunkListener implements Listener {
	@EventHandler
	public void load(ChunkLoadEvent event) {
		System.out.print("#Loading");
	}

	@EventHandler
	public void unload(ChunkUnloadEvent event) {

	}

	@EventHandler
	public void populate(ChunkPopulateEvent event) {

	}
}
