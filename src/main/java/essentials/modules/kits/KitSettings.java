package essentials.modules.kits;

import java.util.HashMap;
import java.util.Map;

import essentials.utilities.conditions.Condition;

public class KitSettings {
	Map<String, Object> settings = new HashMap<>();
	
	public boolean isClaimOneTimeAfterDieing() {
		return (boolean) settings.get("claimOneTimeAfterDieing");
	}

	public void setClaimOneTimeAfterDieing(boolean claimOneTimeAfterDieing) {
		settings.put("claimOneTimeAfterDieing", claimOneTimeAfterDieing);
	}

	public int getCooldown() {
		if(settings.containsKey("cooldown") || this == KitManager.defaultKitSettings)
			return (int) settings.get("cooldown");
		return KitManager.defaultKitSettings.getCooldown();
	}

	public void setCooldown(int cooldown) {
		settings.put("cooldown", cooldown);
	}

	public boolean isPermission() {
		if(settings.containsKey("permission") || this == KitManager.defaultKitSettings)
			return (boolean) settings.get("permission");
		return KitManager.defaultKitSettings.isPermission();
	}

	public void setPermission(boolean permission) {
		settings.put("permission", permission);
	}

	public Condition getCondition() {
		if(settings.containsKey("condition") || this == KitManager.defaultKitSettings) {
			Condition condition = (Condition) settings.get("condition");
			if(condition.hasExecute() && condition.hasCondition()) {
				return condition;
			} else {
				return new Condition(
					condition.hasCondition() ? condition.getCondition() : KitManager.defaultKitSettings.getCondition().getCondition(),
					condition.hasExecute() ? condition.getExecute() : KitManager.defaultKitSettings.getCondition().getExecute()
				);
			}
		}
		return KitManager.defaultKitSettings.getCondition();
	}

	public void setCondition(Condition condition) {
		settings.put("condition", condition);
	}
	
	public boolean isClaimOneTime() {
		if(settings.containsKey("claimOneTime") || this == KitManager.defaultKitSettings)
			return (boolean) settings.get("claimOneTime");
		return KitManager.defaultKitSettings.isClaimOneTime();
	}

	public void setClaimOneTime(boolean claimOneTime) {
		settings.put("claimOneTime", claimOneTime);
	}
}
