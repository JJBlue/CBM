package essentials.modules.commandonobject;

public class CoBCommandInfo {
	public boolean saved;
	public CoBAction action;
	public String command;
	
	public CoBCommandInfo() {}
	
	public CoBCommandInfo(CoBAction action, String command) {
		saved = false;
		this.action = action;
		this.command = command;
	}
}
