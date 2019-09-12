package essentials.utilities.chat;

public enum ClickAction {
	CHANGE_PAGE("change_page"),
	OPEN_FILE("open_file"),
	OPEN_URL("open_url"),
	RUN_COMMAND("run_command"),
	SUGGEST_COMMAND("suggest_command");

	String value;

	ClickAction(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null) super.toString();
		return value;
	}
}
