package cbm.modules.player;

import cbm.modulemanager.EModule;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;
import cbm.modules.player.commands.MidiPlayerCommands;

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
	public String getID() {
		return "midiplayer";
	}
}
