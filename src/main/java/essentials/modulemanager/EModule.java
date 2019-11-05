package essentials.modulemanager;

public abstract class EModule implements Module {

	protected boolean loaded;
	
	@Override
	public boolean enable() {
		if(loaded) return true;
		loaded = true;
		return load();
	}

	@Override
	public boolean disable() {
		if(!loaded) return true;
		loaded = false;
		return unload();
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}
	
	public abstract boolean load();
	public abstract boolean unload();

	@Override
	public abstract String getID();

}
