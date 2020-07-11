package cbm.modules.midiplayer;

import cbm.modulemanager.EModule;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;
import cbm.modules.midiplayer.commands.MidiPlayerCommands;

public class MidiPlayerModule extends EModule {
	@Override
	public boolean load() {
		CommandManager.register("midiplayer", new RedirectTabExecutor(new MidiPlayerCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("midiplayer");
		return true;
	}
	
	@Override
	public boolean disable() {
		BukkitMidiPlayerManager.stopAll();
		return true;
	}

	@Override
	public String getID() {
		return "midiplayer";
	}
}
