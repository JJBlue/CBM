package essentials.utilitiesvr.recipes;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.inventory.Recipe;

import net.minecraft.server.v1_15_R1.CraftingManager;
import net.minecraft.server.v1_15_R1.IRecipe;
import net.minecraft.server.v1_15_R1.MinecraftKey;
import net.minecraft.server.v1_15_R1.MinecraftServer;

public class RecipeIterator_v1_15 implements RecipeIterator {
	Iterator<? extends Map<MinecraftKey, IRecipe<?>>> iterator;
	Iterator<IRecipe<?>> currentIterator;

	public RecipeIterator_v1_15() {
		iterator = getCraftingManager().recipes.values().iterator();
	}

	@Override
	public boolean hasNext() {
		if (iterator == null) return false;

		if (currentIterator == null || !currentIterator.hasNext()) {
			while (iterator.hasNext()) {
				currentIterator = iterator.next().values().iterator();

				if (currentIterator == null) continue;

				if (currentIterator.hasNext())
					return true;
			}
		}

		if (currentIterator != null)
			return currentIterator.hasNext();
		return false;
	}

	@Override
	public Recipe next() {
		hasNext();

		if (currentIterator == null) return null;
		return currentIterator.next().toBukkitRecipe();
	}

	@Override
	public void remove() {
		if (currentIterator == null) return;
		currentIterator.remove();
	}
	
	public static CraftingManager getCraftingManager() {
		return getMinecraftServer().getCraftingManager();
	}

	@SuppressWarnings("deprecation")
	public static MinecraftServer getMinecraftServer() {
		return MinecraftServer.getServer();
	}
}
