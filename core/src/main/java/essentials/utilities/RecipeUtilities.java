package essentials.utilities;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

import essentials.utilities.minecraft.MinecraftVersions;
import essentials.utilitiesvr.recipes.RecipeIterator_v1_14;
import essentials.utilitiesvr.recipes.RecipeIterator_v1_15;

public class RecipeUtilities {
	public static Iterator<Recipe> recipeIterator() {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return new RecipeIterator_v1_14();
			case v1_15_R1:
				return new RecipeIterator_v1_15();
		}
		return Bukkit.recipeIterator();
	}
}
