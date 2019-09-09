package essentials.utilitiesvr.recipes;

import net.minecraft.server.v1_14_R1.CraftingManager;
import net.minecraft.server.v1_14_R1.MinecraftServer;
/*
 * Didn't work. Minecraft isn't support this
 */
public class RecipesUtilities_v1_14 {
	public static RecipeIterator getRecipesIterator(){
		return new RecipeIterator_v1_14();
	}
	
	public static CraftingManager getCraftingManager() {
		return getMinecraftServer().getCraftingManager();
	}
	
	@SuppressWarnings("deprecation")
	public static MinecraftServer getMinecraftServer() {
		return MinecraftServer.getServer();
	}
}
