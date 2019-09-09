package essentials.utilitiesvr.recipes;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.inventory.Recipe;

import net.minecraft.server.v1_14_R1.IRecipe;
import net.minecraft.server.v1_14_R1.MinecraftKey;

/*
 * Didn't work. Minecraft isn't support this
 */
public class RecipeIterator_v1_14 implements RecipeIterator {
	Iterator<? extends Map<MinecraftKey, IRecipe<?>>> iterator;
	Iterator<IRecipe<?>> currentIterator;
	
	public RecipeIterator_v1_14() {	
		iterator = RecipesUtilities_v1_14.getCraftingManager().recipes.values().iterator();
	}

	@Override
	public boolean hasNext() {
		if(currentIterator == null || !currentIterator.hasNext()) {
			while(iterator.hasNext()) {
				currentIterator = iterator.next().values().iterator();
				if(currentIterator.hasNext())
					return true;
			}
		}
		
		return currentIterator.hasNext();
	}

	@Override
	public Recipe next() {
		if(currentIterator == null) return null;
		return currentIterator.next().toBukkitRecipe();
	}
}
