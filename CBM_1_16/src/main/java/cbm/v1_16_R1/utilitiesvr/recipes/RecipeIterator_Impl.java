package cbm.v1_16_R1.utilitiesvr.recipes;

import java.util.Iterator;

import org.bukkit.inventory.Recipe;

import cbm.utilitiesvr.recipes.RecipeUtilities_Interface;

public class RecipeIterator_Impl implements RecipeUtilities_Interface {
	@Override
	public Iterator<Recipe> recipeIterator() {
		return new RecipeIterator_v();
	}
}
