package cbm.modules.tablist;

import cbm.modulemanager.EModule;

public class TablistModule extends EModule {

	@Override
	public String getID() {
		return "Tablist";
	}

	@Override
	public boolean load() {
		Tablist.load();
		return true;
	}

	@Override
	public boolean unload() {
		Tablist.unload();
		return true;
	}

}
