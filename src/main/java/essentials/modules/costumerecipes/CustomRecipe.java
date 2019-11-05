package essentials.modules.costumerecipes;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import essentials.config.MainConfig;

public class CustomRecipe {
	static File file;
	static FileConfiguration configuration;
	
	public static void load() {
		file = new File(MainConfig.getDataFolder() + "/customrecipes");
		configuration = YamlConfiguration.loadConfiguration(file);
		
		registerAllRecipes();
	}
	
	public static void registerAllRecipes() {
		ConfigurationSection section = configuration.getConfigurationSection("recipes");
		if(section == null)
			section = configuration.createSection("recipes");
		
		for(String id : section.getKeys(false)) { //TODO checks of nullpointer
			try {
				ConfigurationSection rSection = section.getConfigurationSection(id);
				
				if(!rSection.getBoolean("enable")) continue;
				
				Material material = Material.valueOf(rSection.getString("result").toUpperCase());
				ItemStack result = new ItemStack(material);
				
				ItemMeta meta = result.getItemMeta();
				if(rSection.contains("displayname"))
					meta.setDisplayName(rSection.getString("displayname"));
				if(rSection.contains("lore"))
					meta.setLore(rSection.getStringList("lore"));
				if(rSection.contains("unbreakable"))
					meta.setUnbreakable(rSection.getBoolean("unbreakable"));
				result.setItemMeta(meta);
				
				if(rSection.contains("amount"))
					result.setAmount(rSection.getInt("amount"));
				
				NamespacedKey key = NamespacedKey.minecraft(id);
				float experience = (float) rSection.getDouble("experience");
				int cookingTime = rSection.getInt("cookingTime");
				
				CustomRecipeType type = CustomRecipeType.valueOf(rSection.getString("type").toUpperCase());
				RecipeChoice input = null;
				String shape = rSection.getString("recipe");
				Map<Character, Material> ingredient = null;
				
				if(rSection.contains("input")) {
					List<String> inputs = rSection.getStringList("input");
					List<Material> materials = new LinkedList<>();
					for(String m : inputs) {
						materials.add(Material.valueOf(m.toUpperCase()));
					}
					input = new RecipeChoice.MaterialChoice(materials);
				}
				
				if(rSection.contains("ingredient")) {
					ingredient = new HashMap<>();
					for(char c : shape.toCharArray()) {
						Material m = Material.valueOf(rSection.getString("ingredient." + c));
						ingredient.put(c, m);
					}
				}
				
				switch (type) {
					case BLASTING:
						Recipe recipe = CustomRecipeSince_1_14.getBlasting(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						break;
					case CAMPFIRE:
						recipe = CustomRecipeSince_1_14.getCampfire(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						break;
					case COOKING:
						recipe = getFurnace(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						recipe = CustomRecipeSince_1_14.getSmoking(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						recipe = CustomRecipeSince_1_14.getCampfire(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						break;
					case FURNACE:
						recipe = getFurnace(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						break;
					case SHAPED:
						recipe = getShapedRecipe(key, result, shape, ingredient);
						Bukkit.addRecipe(recipe);
						break;
					case SHAPELESS:
						recipe = getShapeless(key, result, shape, ingredient);
						Bukkit.addRecipe(recipe);
						break;
					case SMOKING:
						recipe = CustomRecipeSince_1_14.getSmoking(key, result, input, experience, cookingTime);
						Bukkit.addRecipe(recipe);
						break;
					case STONECUTTING:
						recipe = CustomRecipeSince_1_14.getStonecutting(key, result, input);
						Bukkit.addRecipe(recipe);
						break;
				}
			} catch (Exception e) {
				System.out.println("Some problems in the CustomRecipe config");
				e.printStackTrace();
			}
		}
	}
	
	public static Recipe getShapedRecipe(NamespacedKey key, ItemStack result, String shape, Map<Character, Material> ingredient) {
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
	
	public static Recipe getShapeless(NamespacedKey key, ItemStack result, String shape, Map<Character, Material> ingredient) {
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
	
	public static Recipe getFurnace(NamespacedKey key, ItemStack result, RecipeChoice input, float experience, int cookingTime) {
		return new FurnaceRecipe(key, result, input, experience, cookingTime);
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
	
	public static void unload() {
		file = null;
		configuration = null;
	}
}
