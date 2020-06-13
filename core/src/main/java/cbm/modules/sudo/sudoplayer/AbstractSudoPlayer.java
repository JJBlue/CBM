package cbm.modules.sudo.sudoplayer;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class AbstractSudoPlayer implements SudoPlayerInterface {
	public final CommandSender usedSudo;
	public final Player player;
	public boolean silentOutputMessage;
	public GameMode gameMode;

	public AbstractSudoPlayer(CommandSender usedSudo, Player player) {
		this.usedSudo = usedSudo;
		this.player = player;
	}

	public AbstractSudoPlayer(CommandSender usedSudo) {
		this.usedSudo = usedSudo;
		this.player = (Player) usedSudo;
	}

	public PermissionAttachment addAttachment(Plugin arg0) {
		if (usedSudo == null)
			return player.addAttachment(arg0);
		return usedSudo.addAttachment(arg0);
	}

	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		if (usedSudo == null)
			return player.addAttachment(arg0, arg1);
		return usedSudo.addAttachment(arg0, arg1);
	}

	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		if (usedSudo == null)
			return player.addAttachment(arg0, arg1, arg2);
		return usedSudo.addAttachment(arg0, arg1, arg2);
	}

	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		if (usedSudo == null)
			return player.addAttachment(arg0, arg1, arg2, arg3);
		return usedSudo.addAttachment(arg0, arg1, arg2, arg3);
	}

	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		if (usedSudo == null)
			return player.getEffectivePermissions();
		return usedSudo.getEffectivePermissions();
	}

	public boolean hasPermission(String arg0) {
		if (usedSudo == null)
			return player.hasPermission(arg0);
		return usedSudo.hasPermission(arg0);
	}

	public boolean hasPermission(Permission arg0) {
		if (usedSudo == null)
			return player.hasPermission(arg0);
		return usedSudo.hasPermission(arg0);
	}

	public boolean isPermissionSet(String arg0) {
		if (usedSudo == null)
			return player.isPermissionSet(arg0);
		return usedSudo.isPermissionSet(arg0);
	}

	public boolean isPermissionSet(Permission arg0) {
		if (usedSudo == null)
			return player.isPermissionSet(arg0);
		return usedSudo.isPermissionSet(arg0);
	}

	public void recalculatePermissions() {
		if (usedSudo != null)
			usedSudo.recalculatePermissions();
	}

	public void removeAttachment(PermissionAttachment arg0) {
		if (player != null)
			player.removeAttachment(arg0);
	}

	public boolean isOp() {
		if (usedSudo == null)
			return player.isOp();
		return usedSudo.isOp();
	}

	public void setOp(boolean arg0) {
		if (usedSudo == null) return;
		usedSudo.setOp(arg0);
	}

	public void sendMessage(String arg0) {
		if (silentOutputMessage) return;
		if (player != null)
			player.sendMessage(arg0);
	}

	public void sendMessage(String[] arg0) {
		if (silentOutputMessage) return;
		if (player != null)
			player.sendMessage(arg0);
	}

	public GameMode getGameMode() {
		if (gameMode != null) return gameMode;
		return player.getGameMode();
	}

	public AbstractSudoPlayer setSilentOutputMessage(boolean silent) {
		this.silentOutputMessage = silent;
		return this;
	}
}
