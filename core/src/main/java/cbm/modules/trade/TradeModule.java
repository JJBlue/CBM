package cbm.modules.trade;

import cbm.modulemanager.EModule;
import cbm.modulemanager.ModuleManager;
import cbm.modules.commands.CommandManager;
import cbm.modules.commands.tabexecutors.RedirectTabExecutor;

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
