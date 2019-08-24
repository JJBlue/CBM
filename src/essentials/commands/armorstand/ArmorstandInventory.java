package essentials.commands.armorstand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import essentials.inventory.InventoryFactory;
import essentials.inventory.InventoryItem;
import essentials.inventory.InventoryPage;
import essentials.inventory.itemtypes.InventoryItemTypes;
import essentials.inventory.itemtypes.InventoryObjectField;

public class ArmorstandInventory {
	private final ArmorStand armorstand;
	
	public ArmorstandInventory(final ArmorStand armorstand) {
		this.armorstand = armorstand;
	}
	
	public void openInventory(Player player) {
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54));
		InventoryPage page = factory.createFirstPage();
		
		InventoryObjectField<Boolean> visibleField = InventoryItemTypes.createCheckField("Visible", armorstand.isVisible());
		visibleField.setOnChangeValue((old, neu, item) -> {
			armorstand.setVisible(neu);
		});
		
		InventoryObjectField<Boolean> smallField = InventoryItemTypes.createCheckField("Small", armorstand.isSmall());
		smallField.setOnChangeValue((old, neu, item) -> {
			armorstand.setSmall(neu);
		});
		
		InventoryObjectField<Boolean> invulnerableField = InventoryItemTypes.createCheckField("Invulnerable", armorstand.isInvulnerable());
		invulnerableField.setOnChangeValue((old, neu, item) -> {
			armorstand.setInvulnerable(neu);
		});
		
		InventoryObjectField<Boolean> armsField = InventoryItemTypes.createCheckField("Arms", armorstand.hasArms());
		armsField.setOnChangeValue((old, neu, item) -> {
			armorstand.setArms(neu);
		});
		
		InventoryObjectField<Boolean> basePlateField = InventoryItemTypes.createCheckField("Base Plate", armorstand.hasBasePlate());
		basePlateField.setOnChangeValue((old, neu, item) -> {
			armorstand.setBasePlate(neu);
		});
		
		InventoryObjectField<Boolean> collidableField = InventoryItemTypes.createCheckField("Collidable", armorstand.isCollidable());
		collidableField.setOnChangeValue((old, neu, item) -> {
			armorstand.setCollidable(neu);
		});
		
		InventoryObjectField<Boolean> glowingField = InventoryItemTypes.createCheckField("Glowing", armorstand.isGlowing());
		glowingField.setOnChangeValue((old, neu, item) -> {
			armorstand.setGlowing(neu);
		});
		
		InventoryObjectField<Boolean> gravityField = InventoryItemTypes.createCheckField("Gravity", armorstand.hasGravity());
		gravityField.setOnChangeValue((old, neu, item) -> {
			armorstand.setGravity(neu);
		});
		
		InventoryObjectField<Boolean> glidingField = InventoryItemTypes.createCheckField("Gliding", armorstand.isGliding());
		glidingField.setOnChangeValue((old, neu, item) -> {
			armorstand.setGliding(neu);
		});
		
		InventoryObjectField<Boolean> customNameVisible = InventoryItemTypes.createCheckField("Custom Name Visible", armorstand.isCustomNameVisible());
		customNameVisible.setOnChangeValue((old, neu, item) -> {
			armorstand.setCustomNameVisible(neu);
		});
		
		InventoryObjectField<Boolean> markerField = InventoryItemTypes.createCheckField("Marker | Interactable", armorstand.isMarker());
		markerField.setOnChangeValue((old, neu, item) -> {
			armorstand.setMarker(neu);
		});
		
		InventoryItem reset = new InventoryItem(Material.GRAY_WOOL);
		reset.setDisplayName("Reset Gesture");
		reset.setOnClick((event, item) -> {
			armorstand.setHeadPose(new EulerAngle(0.05494790421765526, 0.11621542312304131, 0));
			armorstand.setBodyPose(new EulerAngle(0, 0.05842652523351677, 0));
			armorstand.setLeftArmPose(new EulerAngle(-0.17453292519943295, 0, -0.17453292519943295));
			armorstand.setRightArmPose(new EulerAngle(-0.2617993877991494, 0, 0.17453292519943295));
			armorstand.setLeftLegPose(new EulerAngle(-0.017453292519943295, 0, -0.017453292519943295));
			armorstand.setRightLegPose(new EulerAngle(0.017453292519943295, 0, 0.017453292519943295));
			event.setCancelled(true);
		});
		
		InventoryItem nothing = new InventoryItem(Material.GRAY_STAINED_GLASS_PANE);
		nothing.setOnClick((event, item) -> event.setCancelled(true));
		
		// 0 1 2 | 3 | 4 5 6 7 8
		page.addItem(0, nothing);
		page.addItem(1, armorstand.getHelmet());
		page.addItem(2, nothing);
		page.addItem(3, nothing);
		page.addItem(4, basePlateField);
		page.addItem(5, smallField);
		page.addItem(6, visibleField);
		page.addItem(7, gravityField);
		page.addItem(8, armsField);
		
		// 9 10 11 | 12 | 13 14 15 16 17
		page.addItem(9, armorstand.getEquipment().getItemInMainHand());
		page.addItem(10, armorstand.getChestplate());
		page.addItem(11, armorstand.getEquipment().getItemInOffHand());
		page.addItem(12, nothing);
		page.addItem(13, customNameVisible);
		page.addItem(14, glowingField);
		page.addItem(15, invulnerableField);
		page.addItem(16, markerField);
		page.addItem(17, glidingField);
		
		// 18 19 20 | 21 | 22 23 24 25 26
		page.addItem(18, nothing);
		page.addItem(19, armorstand.getLeggings());
		page.addItem(20, nothing);
		page.addItem(21, nothing);
		page.addItem(22, nothing);
		page.addItem(23, nothing);
		page.addItem(24, nothing);
		page.addItem(25, collidableField);
		page.addItem(26, reset);
		
		// 27 28 29 | 30 | 31 32 33 34 35
		page.addItem(27, nothing);
		page.addItem(28, armorstand.getBoots());
		page.addItem(29, nothing);
		page.addItem(30, nothing);
		page.addItem(31, nothing);
		page.addItem(32, nothing);
		page.addItem(33, nothing);
		page.addItem(34, nothing);
		page.addItem(35, nothing);
		
		// 36 37 38 | 
		page.addItem(36, nothing);
		page.addItem(37, nothing);
		page.addItem(38, nothing);
		
		factory.setOnClick((event, item) -> {
			int slot = event.getSlot();
			
			switch(slot) {
				case 1:
					armorstand.setHelmet(event.getCursor());
					break;
				case 9:
					armorstand.getEquipment().setItemInMainHand(event.getCursor());
					break;
				case 10:
					armorstand.setChestplate(event.getCursor());
					break;
				case 11:
					armorstand.getEquipment().setItemInOffHand(event.getCursor());
					break;
				case 19:
					armorstand.setLeggings(event.getCursor());
					break;
				case 28:
					armorstand.setBoots(event.getCursor());
					break;
			}
		});
		
//		armorstand.setLeftArmPose(new EulerAngle(x, y, z));
//		armorstand.setLeftLegPose(arg0);
//		armorstand.setHeadPose(arg0);
//		armorstand.setBodyPose(arg0);
//		armorstand.setRightArmPose(arg0);
//		armorstand.setRightLegPose(arg0);
//		Position
		
		factory.refreshPage();
		factory.openInventory(player);
	}
	
	private String shsaaa(EulerAngle angle) {
		return angle.getX() + " " + angle.getY() + " " + angle.getZ();
	}
}
