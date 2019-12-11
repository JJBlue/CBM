package essentials.utilities;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.recipes.RecipeIterator_v1_14;

public class RecipeUtilities {
	public static Iterator<Recipe> recipeIterator() {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return new RecipeIterator_v1_14();
		}
		return Bukkit.recipeIterator();
	}
}