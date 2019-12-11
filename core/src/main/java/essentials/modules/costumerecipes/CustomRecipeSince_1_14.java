package essentials.modules.costumerecipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;

public class CustomRecipeSince_1_14 {
	public static Recipe getSmoking(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime) {
		return new SmokingRecipe(key, result, input, experience, cookingTime);
	}
	
	public static Recipe getStonecutting(NamespacedKey key, ItemStack result, RecipeChoice input) {
		return new StonecuttingRecipe(key, result, input);
	}
	
	public static Recipe getBlasting(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime) {
		return new BlastingRecipe(key, result, input, experience, cookingTime);
	}
	
	public static Recipe getCampfire(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime) {
		return new CampfireRecipe(key, result, input, experience, cookingTime);
	}
	
	public static NamespacedKey getNamespacedKey(Recipe recipe) {
		if(recipe instanceof StonecuttingRecipe) {
			return ((StonecuttingRecipe) recipe).getKey();
		}
		return null;
	}
}
