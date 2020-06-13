package cbm.modules.debugstick;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;

public class DebugStickModule extends EModule {
	
	@Override
	public boolean load() {
		ModuleManager.addListener(new DebugStickListener(), this);
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public String getID() {
		return "DebugStick";
	}

}
