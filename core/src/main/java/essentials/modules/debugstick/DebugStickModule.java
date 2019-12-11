package essentials.modules.debugstick;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;

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
