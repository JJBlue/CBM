package essentials.player;

public class PlayerConfigValue {
	private boolean isSaved;
	private boolean isTmp;
	private Object value;
	
	public PlayerConfigValue(Object value, boolean saved) {
		setSaved(saved);
		this.value = value;
		this.isTmp = false;
	}
	
	public PlayerConfigValue(Object value, boolean saved, boolean isTmp) {
		setSaved(saved);
		this.value = value;
		this.isTmp = isTmp;
	}
	
	public void set(Object object) {
		setSaved(false);
		value = object;
	}
	
	public Object getObject() {
		return value;
	}

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
}
