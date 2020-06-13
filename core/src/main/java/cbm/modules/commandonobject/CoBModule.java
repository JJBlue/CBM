package cbm.modules.commandonobject;

import cbm.main.Main;
import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

public class CoBModule extends EModule{

	@Override
	public boolean load() {
		CommandOnBlock.load();
		ModuleManager.addListener(new CommandListener(), this);
		CommandManager.register("cob", new RedirectTabExecutor(new CoBCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("cob");
		Main.unloadHelper(CommandOnBlock::unload);
		return true;
	}

	@Override
	public String getID() {
		return "CommandsOnBlock";
	}

}
