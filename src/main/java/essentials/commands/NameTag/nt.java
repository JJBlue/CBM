package essentials.commands.NameTag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

public class nt {
	static Scoreboard board;
	static Team team;

	public static void setNameTag(boolean wert) {
		if (team == null || board == null) {
			ScoreboardManager manager = Bukkit.getScoreboardManager();
			if (manager != null)
				board = manager.getMainScoreboard();
			
			team = board.getTeam("nametag_hide");
			if(team == null)
				team = board.registerNewTeam("nametag_hide");
			team.setDisplayName("");
		}

		if (!wert) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if(!team.hasEntry(p.getName()))
					team.addEntry(p.getName());
			}

			team.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
			for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
			Bukkit.broadcastMessage("NameTag Visible");
		} else {
			team.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.ALWAYS);
			for (Player p : Bukkit.getOnlinePlayers()) p.setScoreboard(board);
			Bukkit.broadcastMessage("NameTag shown");
		}
	}
}
