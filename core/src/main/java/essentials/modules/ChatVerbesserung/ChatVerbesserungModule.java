package essentials.modules.ChatVerbesserung;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;

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
