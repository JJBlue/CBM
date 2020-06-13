package cbm.utilities.chat;

public enum HoverAction {
	SHOW_ACHIEVEMENT("show_achievement"),
	SHOW_ENTITY("show_entity"),
	SHOW_ITEM("show_item"),
	SHOW_Text("show_text");

	String value;

	HoverAction(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null) super.toString();
		return value;
	}
}
