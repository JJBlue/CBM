package cbm.modules.alias;

import cbm.modulemanager.EModule;

public class CustomAliasModule extends EModule {

	@Override
	public boolean load() {
		CustomAlias.load();
		return true;
	}

	@Override
	public boolean unload() {
		CustomAlias.unload();
		return true;
	}

	@Override
	public String getID() {
		return "CustomAlias";
	}

}
