package essentials.modules.costumerecipes;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.Recipe;

public class CustomRecipeSince_1_15 {
	//CraftComplexRecipe
	public static NamespacedKey getNamespacedKey(Recipe recipe) {
		if(recipe instanceof ComplexRecipe) {
			return ((ComplexRecipe) recipe).getKey();
		}
		return null;
	}
}
