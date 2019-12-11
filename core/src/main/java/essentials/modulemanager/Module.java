package essentials.modulemanager;

public interface Module {
	public boolean enable();
	public boolean disable();
	
	public boolean isLoaded();
	public String getID();
}
