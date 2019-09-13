package essentials.modules.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

public class ChunkFunctions {
	private ChunkFunctions() {
	}
	//TODO

	/*
	 * SlimeChunk
	 * ForceLoaded
	 */

	public static void unloadAllChunks() {
		for (World world : Bukkit.getWorlds())
			unloadAllChunks(world);
	}

	public static void unloadAllChunks(World world) {
		for (Chunk chunk : world.getLoadedChunks()) {
			if (!world.unloadChunk(chunk))
//				Bukkit.broadcastMessage(chunk.isForceLoaded() + " " + chunk.isSlimeChunk() + " " + chunk.isLoaded())
				;
		}

		System.out.println(world.getLoadedChunks().length);
	}

	/**
	 * No nearby Player
	 */
	public static void unloadChunk() {

	}

	public static long getAllChunksLoaded() {
		long count = 0;
		for (World world : Bukkit.getWorlds())
			count += getAllChunksLoaded(world);
		return count;
	}

	public static long getAllChunksLoaded(World world) {
		return world.getLoadedChunks().length;
	}
}
