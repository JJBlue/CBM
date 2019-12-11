package essentials.modules.timer;

import essentials.modulemanager.EModule;
import essentials.modulemanager.ModuleManager;
import essentials.modules.commands.CommandManager;
import essentials.modules.commands.tabexecutors.RedirectTabExecutor;

public class TimerModule extends EModule {

	@Override
	public boolean load() {
		TimerConfig.load();
		ModuleManager.addListener(new TimerListener(), this);
		CommandManager.register("timer", new RedirectTabExecutor(new TimerCommand()));
		return true;
	}

	@Override
	public boolean unload() {
		CommandManager.unregister("timer");
		TimerConfig.unload();
		return true;
	}

	@Override
	public String getID() {
		return "Timer";
	}

}
