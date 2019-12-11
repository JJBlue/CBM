package essentials.modules.trade;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class TradeModule extends EModule {

	@Override
	public boolean load() {
		ModuleManager.addListener(new TradeListener(), this);
		CommandManager.register("trade", new RedirectTabExecutor(new TradeCommands()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("trade");
		return true;
	}

	@Override
	public String getID() {
		return "Trade";
	}

}
