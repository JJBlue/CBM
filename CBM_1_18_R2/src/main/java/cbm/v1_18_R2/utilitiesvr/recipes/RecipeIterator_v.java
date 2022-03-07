package cbm.v1_18_R2.utilitiesvr.recipes;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.inventory.Recipe;

import cbm.utilitiesvr.recipes.RecipeIterator;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.CraftingManager;
import net.minecraft.world.item.crafting.IRecipe;

public class RecipeIterator_v implements RecipeIterator {
	Iterator<? extends Map<MinecraftKey, IRecipe<?>>> iterator;
	Iterator<IRecipe<?>> currentIterator;

	public RecipeIterator_v() {
		iterator = getCraftingManager().c.values().iterator();
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
		return getMinecraftServer().aC();
	}

	@SuppressWarnings("deprecation")
	public static MinecraftServer getMinecraftServer() {
		return MinecraftServer.getServer();
	}
}
