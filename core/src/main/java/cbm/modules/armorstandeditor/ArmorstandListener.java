package cbm.modules.armorstandeditor;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.EulerAngle;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.utilities.MathUtilities;
import cbm.utilitiesvr.chat.ChatMessageType;
import cbm.utilitiesvr.chat.ChatUtilities;

public class ArmorstandListener implements Listener {
	@EventHandler
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		if (!(event.getRightClicked() instanceof ArmorStand)) return;

		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getConfig(player);

		if (!config.getBoolean("armorstandEditorListener")) return;

		ArmorStand armorStand = (ArmorStand) event.getRightClicked();
		ArmorstandBodyParts bodyPart = (ArmorstandBodyParts) config.get("armorstandEditorEnum");

		switch (bodyPart) {
			case HEAD:
				armorStand.setHeadPose(getNewAngle(armorStand.getHeadPose(), player, false));
				break;
			case BODY:
				armorStand.setBodyPose(getNewAngle(armorStand.getBodyPose(), player, false));
				break;
			case RIGHT_ARM:
				armorStand.setRightArmPose(getNewAngle(armorStand.getRightArmPose(), player, false));
				break;
			case LEFT_ARM:
				armorStand.setLeftArmPose(getNewAngle(armorStand.getLeftArmPose(), player, false));
				break;
			case RIGHT_LEG:
				armorStand.setRightLegPose(getNewAngle(armorStand.getRightLegPose(), player, false));
				break;
			case LEFT_LEG:
				armorStand.setLeftLegPose(getNewAngle(armorStand.getLeftLegPose(), player, false));
				break;
			case POSITION:
				Location location = armorStand.getLocation();

				switch (player.getInventory().getHeldItemSlot()) {
					case 1:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + (0.1)), ChatMessageType.GAME_INFO); //TODO: Replace with Language
						armorStand.teleport(location.add(0.1, 0, 0));
						break;
					case 2:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + (0.1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 0.1, 0));
						break;
					case 3:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + (0.1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 0, 0.1));
						break;

					case 5:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + (1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(1, 0, 0));
						break;
					case 6:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + (1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 1, 0));
						break;
					case 7:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + (1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 0, 1));
						break;
				}
				break;
			case ROTATION:
				location = armorStand.getLocation();
				switch (player.getInventory().getHeldItemSlot()) {
					case 1:
						location.setYaw(location.getYaw() + 1);
					case 2:
						location.setYaw(location.getYaw() + 10);
				}
				armorStand.teleport(location);
				break;
			default:
				break;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player) && !(event.getEntity() instanceof ArmorStand)) return;
		Player player = (Player) event.getDamager();
		PlayerConfig config = PlayerManager.getConfig(player);

		if (!config.getBoolean("armorstandEditorListener")) return;
		ArmorStand armorStand = (ArmorStand) event.getEntity();
		ArmorstandBodyParts bodyPart = (ArmorstandBodyParts) config.get("armorstandEditorEnum");

		switch (bodyPart) {
			case HEAD:
				armorStand.setHeadPose(getNewAngle(armorStand.getHeadPose(), player, true));
				break;
			case BODY:
				armorStand.setBodyPose(getNewAngle(armorStand.getBodyPose(), player, true));
				break;
			case RIGHT_ARM:
				armorStand.setRightArmPose(getNewAngle(armorStand.getRightArmPose(), player, true));
				break;
			case LEFT_ARM:
				armorStand.setLeftArmPose(getNewAngle(armorStand.getLeftArmPose(), player, true));
				break;
			case RIGHT_LEG:
				armorStand.setRightLegPose(getNewAngle(armorStand.getRightLegPose(), player, true));
				break;
			case LEFT_LEG:
				armorStand.setLeftLegPose(getNewAngle(armorStand.getLeftLegPose(), player, true));
				break;
			case POSITION:
				Location location = armorStand.getLocation();

				switch (player.getInventory().getHeldItemSlot()) {
					case 1:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + (-0.1)), ChatMessageType.GAME_INFO); //TODO: Replace with Language
						armorStand.teleport(location.add(-0.1, 0, 0));
						break;
					case 2:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + (-0.1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, -0.1, 0));
						break;
					case 3:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + (-0.1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 0, -0.1));
						break;

					case 5:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + (-1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(-1, 0, 0));
						break;
					case 6:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + (-1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, -1, 0));
						break;
					case 7:
						ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + (-1)), ChatMessageType.GAME_INFO);
						armorStand.teleport(location.add(0, 0, -1));
						break;
				}
				break;
			case ROTATION:
				location = armorStand.getLocation();
				switch (player.getInventory().getHeldItemSlot()) {
					case 1:
						location.setYaw(location.getYaw() - 1);
					case 2:
						location.setYaw(location.getYaw() - 10);
				}
				armorStand.teleport(location);
				break;
			default:
				break;
		}

		event.setCancelled(true);
	}

	private EulerAngle getNewAngle(EulerAngle angle, Player player, boolean sub) {
		switch (player.getInventory().getHeldItemSlot()) {
			case 0:
				int degrees = (sub ? -1 : 1);
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + degrees), ChatMessageType.GAME_INFO); //TODO: Replace with Language
				return angle.add(MathUtilities.degreeToEuler(degrees), 0, 0);
			case 1:
				degrees = (sub ? -1 : 1);
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + degrees), ChatMessageType.GAME_INFO);
				return angle.add(0, MathUtilities.degreeToEuler(degrees), 0);
			case 2:
				degrees = (sub ? -1 : 1);
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + degrees), ChatMessageType.GAME_INFO);
				return angle.add(0, 0, MathUtilities.degreeToEuler(degrees));
			case 4:
				degrees = (sub ? -1 : 1) * 10;
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move x " + degrees), ChatMessageType.GAME_INFO);
				return angle.add(MathUtilities.degreeToEuler(degrees), 0, 0);
			case 5:
				degrees = (sub ? -1 : 1) * 10;
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move y " + degrees), ChatMessageType.GAME_INFO);
				return angle.add(0, MathUtilities.degreeToEuler(degrees), 0);
			case 6:
				degrees = (sub ? -1 : 1) * 10;
				ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§3Move z " + degrees), ChatMessageType.GAME_INFO);
				return angle.add(0, 0, MathUtilities.degreeToEuler(degrees));
		}
		return angle;
	}

	@EventHandler
	public void exit(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getConfig(player);
		if (!config.getBoolean("armorstandEditorListener")) return;

		config.removeBuffer("armorstandEditorListener");
		config.removeBuffer("armorstandEditorEnum");
		ChatUtilities.sendMessage(player, ChatUtilities.createMessage("§4Exit"), ChatMessageType.GAME_INFO); //TODO: Replace with Language
		event.setCancelled(true);
	}
}
