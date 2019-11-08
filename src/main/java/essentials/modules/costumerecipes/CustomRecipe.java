package essentials.modules.costumerecipes;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import essentials.config.ConfigHelper;
import essentials.config.MainConfig;
import essentials.utilities.ConfigUtilities;
import essentials.utilities.RecipeUtilities;

public class CustomRecipe {
	static File file;
	static FileConfiguration configuration;
	static List<String> recipesIDs;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/CustomRecipes.yml");
		if(!file.exists())
			ConfigHelper.extractDefaultConfigs("CustomRecipes", "CustomRecipes.yml");
		
		configuration = YamlConfiguration.loadConfiguration(file);
		recipesIDs = new LinkedList<>();
		
		registerAllRecipes();
	}
	
	public static void unload() {
		file = null;
		configuration = null;
		unregisterAllRecipes();
		recipesIDs = null;
	}
	
	public static void registerAllRecipes() {
		ConfigurationSection section = configuration.getConfigurationSection("recipes");
		if(section == null)
			section = configuration.createSection("recipes");
		
		for(String id : section.getKeys(false)) {
			try {
				String keyID = id.toLowerCase();
				NamespacedKey key = NamespacedKey.minecraft(keyID);
				ConfigurationSection recipeSection = section.getConfigurationSection(id);
				ConfigurationSection resultSection = recipeSection.getConfigurationSection("result");
				
				if(!recipeSection.getBoolean("enable")) continue;
				
				ItemStack result = ConfigUtilities.readItemStack(resultSection);
				
				float experience = (float) recipeSection.getDouble("experience");
				int cookingTime = recipeSection.getInt("cookingTime");
				
				CustomRecipeType type = CustomRecipeType.valueOf(recipeSection.getString("type").toUpperCase());
				RecipeChoice input = null;
				String shape = recipeSection.getString("recipe");
				Map<Character, Material> ingredient = null;
				
				if(recipeSection.contains("input")) {
					List<String> inputs = recipeSection.getStringList("input");
					List<Material> materials = new LinkedList<>();
					for(String m : inputs) {
						materials.add(Material.valueOf(m.toUpperCase()));
					}
					input = new RecipeChoice.MaterialChoice(materials);
				}
				
				if(recipeSection.contains("ingredient")) {
					ingredient = new HashMap<>();
					for(char c : shape.toCharArray()) {
						Material m = Material.valueOf(recipeSection.getString("ingredient." + c));
						ingredient.put(c, m);
					}
				}
				
				boolean added = false;
				Recipe recipe;
				
				switch (type) {
					case BLASTING:
						recipe = CustomRecipeSince_1_14.getBlasting(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						break;
					case CAMPFIRE:
						recipe = CustomRecipeSince_1_14.getCampfire(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						break;
					case COOKING:
						recipe = getFurnace(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						recipe = CustomRecipeSince_1_14.getSmoking(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						recipe = CustomRecipeSince_1_14.getCampfire(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						break;
					case FURNACE:
						recipe = getFurnace(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						break;
					case SHAPED:
						recipe = getShapedRecipe(key, result, shape, ingredient);
						added = Bukkit.addRecipe(recipe);
						break;
					case SHAPELESS:
						recipe = getShapeless(key, result, shape, ingredient);
						added = Bukkit.addRecipe(recipe);
						break;
					case SMOKING:
						recipe = CustomRecipeSince_1_14.getSmoking(key, result, input, experience, cookingTime);
						added = Bukkit.addRecipe(recipe);
						break;
					case STONECUTTING:
						recipe = CustomRecipeSince_1_14.getStonecutting(key, result, input);
						added = Bukkit.addRecipe(recipe);
						break;
				}
				
				if(added) {
					recipesIDs.add(keyID);
				} else {
					System.out.println("[CBM] Recipe (" + id + ") could not be added");
				}
			} catch (Exception e) {
				System.out.println("Some problems in the CustomRecipe config");
				e.printStackTrace();
			}
		}
	}
	
	public static void unregisterAllRecipes() {
		try {
			Iterator<Recipe> recipes = RecipeUtilities.recipeIterator();
			
			while(recipes.hasNext()) {
				Recipe recipe = recipes.next();
				NamespacedKey key = getNamespacedKey(recipe);
				if(recipesIDs.contains(key.getKey())) {
					recipes.remove();
				}
			}
			
			recipesIDs.clear();
		} catch (Exception e) {
			System.out.println("Could not remove recipes");
			e.printStackTrace();
		}
	}
	
	public static ShapedRecipe getShapedRecipe(NamespacedKey key, ItemStack result, String shape, Map<Character, Material> ingredient) {
		ShapedRecipe recipe = new ShapedRecipe(key, result);
		recipe.shape(splitShape(shape));
		List<Character> contains = new LinkedList<>();
		
		for(char c : ingredient.keySet()) {
			if(Character.isWhitespace(c)) continue;
			if(contains.contains(c)) continue;
			contains.add(c);
			
			Material m = ingredient.get(c);
			recipe.setIngredient(c, m);
		}
		
		return recipe;
	}
	
	public static ShapelessRecipe getShapeless(NamespacedKey key, ItemStack result, String shape, Map<Character, Material> ingredient) {
		ShapelessRecipe recipe = new ShapelessRecipe(key, result);
		List<Character> contains = new LinkedList<>();
		
		for(char c : ingredient.keySet()) {
			if(Character.isWhitespace(c)) continue;
			if(contains.contains(c)) continue;
			contains.add(c);
			
			Material m = ingredient.get(c);
			recipe.addIngredient(c, m);
		}
		
		return recipe;
	}
	
	public static FurnaceRecipe getFurnace(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime) {
		return new FurnaceRecipe(key, result, input, experience, cookingTime);
	}
	
	public static NamespacedKey getNamespacedKey(Recipe recipe) {
		if(recipe instanceof CookingRecipe) {
			return ((CookingRecipe<?>) recipe).getKey();
		} else if(recipe instanceof ShapedRecipe) {
			return ((ShapedRecipe) recipe).getKey();
		} else if(recipe instanceof ShapelessRecipe) {
			return ((ShapelessRecipe) recipe).getKey();
		}
		return CustomRecipeSince_1_14.getNamespacedKey(recipe); //TODO only 1.14 and above
	}

	public static String[] splitShape(String shape) {
		int length = shape.length() / 3;
		if(shape.length() % 3 > 0) {
			length++;
		}
		
		if(length > 3)
			length = 3;
		
		String[] array = new String[length];
		
		if(array.length >= 1)
			array[0] = shape.substring(0, shape.length() > 3 ? 3 : shape.length());
		
		if(array.length >= 2)
			array[1] = shape.substring(3, shape.length() > 6 ? 6 : shape.length());
		
		if(array.length >= 3)
			array[2] = shape.substring(6, shape.length() > 9 ? 9 : shape.length());
		
		return array;
	}
}
