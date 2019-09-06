package essentials.commands.commandonobject;

public enum CoBAction {
	LEFT_CLICK,
	RIGHT_CLICK,
	EVERYTIME,
	STAND_LEFT_CLICK,
	STAND_RIGHT_CLICK,
	SNEAK_LEFT_CLICK,
	SNEAK_RIGHT_CLICK;
	
	public boolean isIn(CoBAction action) {
		switch (action) {
			case EVERYTIME:
				return true;
			case LEFT_CLICK:
				if(action.equals(CoBAction.LEFT_CLICK) || action.equals(CoBAction.SNEAK_LEFT_CLICK) || action.equals(CoBAction.STAND_LEFT_CLICK))
					return true;
			case RIGHT_CLICK:
				if(action.equals(CoBAction.RIGHT_CLICK) || action.equals(CoBAction.SNEAK_RIGHT_CLICK) || action.equals(CoBAction.STAND_RIGHT_CLICK))
					return true;
				break;
			case SNEAK_LEFT_CLICK:
			case SNEAK_RIGHT_CLICK:
			case STAND_LEFT_CLICK:
			case STAND_RIGHT_CLICK:
			default:
				return this.equals(action);
		}
		return false;
	}
}
