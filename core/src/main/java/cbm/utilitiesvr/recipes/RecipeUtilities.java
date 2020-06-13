package cbm.utilitiesvr.recipes;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;

public class RecipeUtilities {
	public final static VersionDependency<RecipeUtilities_Interface> version_dependency = new VersionDependency<>();
	
	public static Iterator<Recipe> recipeIterator() {
		RecipeUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.recipeIterator();
		return Bukkit.recipeIterator();
	}
}
