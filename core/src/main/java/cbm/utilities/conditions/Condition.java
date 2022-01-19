package cbm.utilities.conditions;

import java.util.List;

import org.bukkit.entity.Player;

import components.json.JSONObject;
import components.json.parser.JSONParser;

public class Condition {
	
	JSONObject condition;
	JSONObject execute;
	
	public Condition(String condition, String execute) {
		if(condition != null && !condition.isEmpty())
			this.condition = (JSONObject) JSONParser.parse(condition);
		
		if(execute != null && !execute.isEmpty())
			this.execute = (JSONObject) JSONParser.parse(execute);
	}
	
	public Condition(JSONObject condition, JSONObject execute) {
		this.condition = condition;
		this.execute = execute;
	}
	
	public boolean check(Player player) {
		return ConditionUtilities.checkCondition(player, condition);
	}
	
	public boolean execute(Player player) {
		return ConditionUtilities.execute(player, execute);
	}
	
	public boolean checkAndExecute(Player player) {
		if(!check(player)) return false;
		return execute(player);
	}
	
	public List<String> getConditiontoList() {
		return ConditionUtilities.conditionsToString(condition);
	}
	
	public JSONObject getCondition() {
		return condition;
	}
	
	public String getConditionToString() {
		if(condition == null) return "";
		return condition.toJSONString();
	}
	
	public JSONObject getExecute() {
		return execute;
	}
	
	public String getExecuteToString() {
		if(condition == null) return "";
		return execute.toJSONString();
	}
	
	public boolean hasCondition() {
		return condition != null;
	}
	
	public boolean hasExecute() {
		return execute != null;
	}
}
