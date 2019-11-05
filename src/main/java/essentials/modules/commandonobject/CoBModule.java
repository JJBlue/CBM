package essentials.modules.commandonobject;

import essentials.main.Main;
import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

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
