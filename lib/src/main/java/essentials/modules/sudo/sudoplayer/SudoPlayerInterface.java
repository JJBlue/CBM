package essentials.modules.sudo.sudoplayer;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface SudoPlayerInterface extends CommandSender, Player {
	SudoPlayerInterface setSilentOutputMessage(boolean silent);
}
