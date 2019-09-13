package essentials.commands.NameTag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class nt {
	static Scoreboard board;
	static Team team;

	public static void setNameTag(boolean wert) {
		if (team == null || board == null) {
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			board = manager.getNewScoreboard();
			team = board.registerNewTeam("teamname");
			for (Player p : Bukkit.getOnlinePlayers()) team.addPlayer(p);
			team.setDisplayName("");
		}

		if (!wert) {
			for (Player p : Bukkit.getOnlinePlayers())
				if (!team.getPlayers().contains(p)) team.addPlayer(p);

			team.setNameTagVisibility(NameTagVisibility.NEVER);
			for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
			Bukkit.broadcastMessage("NameTag Visible");
		} else {
			team.setNameTagVisibility(NameTagVisibility.ALWAYS);
			for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
			Bukkit.broadcastMessage("NameTag shown");
		}
	}
}
