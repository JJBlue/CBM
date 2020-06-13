package cbm.modules.ChatVerbesserung;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;

public class ChatVerbesserungModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new ChatVerbesserung(), this);
		return true;
	}

	@Override
	public boolean unload() {
		return true;
	}

	@Override
	public String getID() {
		return "ChatVerbesserung";
	}

}
