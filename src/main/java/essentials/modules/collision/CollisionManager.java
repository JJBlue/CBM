package essentials.modules.collision;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

public class CollisionManager {
	private static Team team;
	
	public synchronized static void load() {
		if(team != null) return;
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getMainScoreboard();
		team = scoreboard.getTeam("cbm_collision");
		if(team == null)
			team = scoreboard.registerNewTeam("cbm_collision");
		team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
	}
	
	public static void setCollision(Player player, boolean value) {
		if(team == null)
			load();
		
		if(!value) {
			if(!team.hasEntry(player.getName())) {
				team.addEntry(player.getName());
				team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			}
		} else {
			team.removeEntry(player.getName());
		}
	}
}
