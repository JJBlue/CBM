package essentials.commands.armorstand;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.util.EulerAngle;

import essentials.player.PlayerConfig;
import essentials.player.PlayerManager;
import essentials.utilities.MathUtilities;

public class ArmorstandListener implements Listener {
	@EventHandler
	public void interactEntity(PlayerInteractAtEntityEvent event) {
		if(!(event.getRightClicked() instanceof ArmorStand)) return;
		
		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		if(!config.getBoolean("armorstandEditorListener")) return;
		
		ArmorStand armorStand = (ArmorStand) event.getRightClicked();
		ArmorstandBodyParts bodyPart = (ArmorstandBodyParts) config.get("armorstandEditorEnum");
		
		switch(bodyPart) {
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
				switch(player.getInventory().getHeldItemSlot()) {
					case 1:
						armorStand.getLocation().add(0.1, 0, 0);
						break;
					case 2:
						armorStand.getLocation().add(0, 0.1, 0);
						break;
					case 3:
						armorStand.getLocation().add(0, 0, 0.1);
						break;
						
					case 5:
						armorStand.getLocation().add(1, 0, 0);
						break;
					case 6:
						armorStand.getLocation().add(0, 1, 0);
						break;
					case 7:
						armorStand.getLocation().add(0, 0, 1);
						break;
				}
				break;
			default:
				break;
		}
		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player) && !(event.getEntity() instanceof ArmorStand)) return;
		Player player = (Player) event.getDamager();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		
		if(!config.getBoolean("armorstandEditorListener")) return;
		ArmorStand armorStand = (ArmorStand) event.getEntity();
		ArmorstandBodyParts bodyPart = (ArmorstandBodyParts) config.get("armorstandEditorEnum");
		
		switch(bodyPart) {
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
				switch(player.getInventory().getHeldItemSlot()) {
					case 1:
						armorStand.getLocation().add(0.1, 0, 0);
						break;
					case 2:
						armorStand.getLocation().add(0, 0.1, 0);
						break;
					case 3:
						armorStand.getLocation().add(0, 0, 0.1);
						break;
						
					case 5:
						armorStand.getLocation().add(1, 0, 0);
						break;
					case 6:
						armorStand.getLocation().add(0, 1, 0);
						break;
					case 7:
						armorStand.getLocation().add(0, 0, 1);
						break;
				}
				break;
			default:
				break;
		}
		
		event.setCancelled(true);
	}
	
	private EulerAngle getNewAngle(EulerAngle angle, Player player, boolean sub) {
		switch(player.getInventory().getHeldItemSlot()) {
			case 0:
				return angle.add(MathUtilities.degreeToEuler((sub ? -1 : 1) * 1), 0, 0);
			case 1:
				return angle.add(0, MathUtilities.degreeToEuler((sub ? -1 : 1) * 1), 0);
			case 2:
				return angle.add(0, 0, MathUtilities.degreeToEuler((sub ? -1 : 1) * 1));
			case 4:
				return angle.add(MathUtilities.degreeToEuler((sub ? -1 : 1) * 10), 0, 0);
			case 5:
				return angle.add(0, MathUtilities.degreeToEuler((sub ? -1 : 1) * 10), 0);
			case 6:
				return angle.add(0, 0, MathUtilities.degreeToEuler((sub ? -1 : 1) * 10));
		}
		return angle;
	}
	
	@EventHandler
	public void exit(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		PlayerConfig config = PlayerManager.getPlayerConfig(player);
		if(!config.getBoolean("armorstandEditorListener")) return;
		
		config.removeBuffer("armorstandEditorListener");
		config.removeBuffer("armorstandEditorEnum");
		event.setCancelled(true);
	}
}
