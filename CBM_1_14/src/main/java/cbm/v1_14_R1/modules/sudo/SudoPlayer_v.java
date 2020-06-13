package cbm.v1_14_R1.modules.sudo;

import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import cbm.modules.sudo.sudoplayer.SudoPlayerInterface;

public class SudoPlayer_v extends CraftPlayer implements SudoPlayerInterface {

	public static Player createSudoPlayer(CommandSender usedSudo, Player player) {
		return new SudoPlayer_v(usedSudo, player);
	}
	
	public static Player createSudoPlayer(CommandSender usedSudo) {
		return new SudoPlayer_v(usedSudo);
	}
	
	public final CommandSender usedSudo;
	public final Player player;
	public boolean silentOutputMessage;
	public GameMode gameMode;

	public SudoPlayer_v(CommandSender usedSudo, Player player) {
		super(
				(CraftServer) usedSudo.getServer(),
				((CraftPlayer) player).getHandle()
		);
		this.usedSudo = usedSudo;
		this.player = player;
	}

	public SudoPlayer_v(CommandSender usedSudo) {
		super(
				(CraftServer) usedSudo.getServer(),
				((CraftPlayer) usedSudo).getHandle()
		);
		this.usedSudo = usedSudo;
		this.player = (Player) usedSudo;
	}

	public PermissionAttachment addAttachment(Plugin arg0) {
		if (usedSudo == null)
			return super.addAttachment(arg0);
		return usedSudo.addAttachment(arg0);
	}

	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		if (usedSudo == null)
			return super.addAttachment(arg0, arg1);
		return usedSudo.addAttachment(arg0, arg1);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2) {
		if (usedSudo == null)
			return super.addAttachment(arg0, arg1, arg2);
		return usedSudo.addAttachment(arg0, arg1, arg2);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1, boolean arg2, int arg3) {
		if (usedSudo == null)
			return super.addAttachment(arg0, arg1, arg2, arg3);
		return usedSudo.addAttachment(arg0, arg1, arg2, arg3);
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		if (usedSudo == null)
			return super.getEffectivePermissions();
		return usedSudo.getEffectivePermissions();
	}

	@Override
	public boolean hasPermission(String arg0) {
		if (usedSudo == null)
			return super.hasPermission(arg0);
		return usedSudo.hasPermission(arg0);
	}

	@Override
	public boolean hasPermission(Permission arg0) {
		if (usedSudo == null)
			return super.hasPermission(arg0);
		return usedSudo.hasPermission(arg0);
	}

	@Override
	public boolean isPermissionSet(String arg0) {
		if (usedSudo == null)
			return super.isPermissionSet(arg0);
		return usedSudo.isPermissionSet(arg0);
	}

	@Override
	public boolean isPermissionSet(Permission arg0) {
		if (usedSudo == null)
			return super.isPermissionSet(arg0);
		return usedSudo.isPermissionSet(arg0);
	}

	@Override
	public void recalculatePermissions() {
		if (usedSudo != null)
			usedSudo.recalculatePermissions();
	}

	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		if (player != null)
			player.removeAttachment(arg0);
	}

	@Override
	public boolean isOp() {
		if (usedSudo == null)
			return super.isOp();
		return usedSudo.isOp();
	}

	@Override
	public void setOp(boolean arg0) {
		if (usedSudo == null) return;
		usedSudo.setOp(arg0);
	}

	@Override
	public void sendMessage(String arg0) {
		if (silentOutputMessage) return;
		if (player != null)
			player.sendMessage(arg0);
	}

	@Override
	public void sendMessage(String[] arg0) {
		if (silentOutputMessage) return;
		if (player != null)
			player.sendMessage(arg0);
	}

	@Override
	public GameMode getGameMode() {
		if (gameMode != null) return gameMode;
		return super.getGameMode();
	}

	public SudoPlayer_v setSilentOutputMessage(boolean silent) {
		this.silentOutputMessage = silent;
		return this;
	}
}
