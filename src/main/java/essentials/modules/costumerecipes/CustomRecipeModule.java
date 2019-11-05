package essentials.modules.costumerecipes;

import essentials.modulemanager.EModule;

public class CustomRecipeModule extends EModule {
	@Override
	public boolean load() {
		CustomRecipe.load();
		return true;
	}

	@Override
	public boolean unload() {
		CustomRecipe.unload();
		return true;
	}

	@Override
	public String getID() {
		return "CustomRecipe";
	}
}
