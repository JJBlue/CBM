package essentials.modules.debugstick;

import essentials.modulemanager.Module;
import essentials.modulemanager.ModuleManager;

public class DebugStickModule implements Module {

	private boolean loaded = false;
	
	@Override
	public boolean enable() {
		if(loaded) return true;
		loaded = true;
		
		ModuleManager.addListener(new DebugStickListener(), this);
		
		return true;
	}

	@Override
	public boolean disable() {
		loaded = false;
		return true;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public String getID() {
		return "DebugStick";
	}

}
