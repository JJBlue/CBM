package cbm.modules.armorstandeditor;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

import cbm.player.PlayerConfig;
import cbm.player.PlayerManager;
import cbm.utilities.inventory.InventoryFactory;
import cbm.utilities.inventory.InventoryItem;
import cbm.utilities.inventory.InventoryPage;
import cbm.utilities.inventory.itemtypes.InventoryItemTypes;
import cbm.utilities.inventory.itemtypes.InventoryObjectField;

public class ArmorstandInventory {
	private final ArmorStand armorstand;

	public ArmorstandInventory(final ArmorStand armorstand) {
		this.armorstand = armorstand;
	}

	public void openInventory(Player player) {
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54));
		InventoryPage page = factory.createFirstPage();

		InventoryObjectField<Boolean> visibleField = InventoryItemTypes.createCheckField("Visible", armorstand.isVisible());
		visibleField.setOnChangeValue((old, neu, item) -> armorstand.setVisible(neu));

		InventoryObjectField<Boolean> smallField = InventoryItemTypes.createCheckField("Small", armorstand.isSmall());
		smallField.setOnChangeValue((old, neu, item) -> armorstand.setSmall(neu));

		InventoryObjectField<Boolean> invulnerableField = InventoryItemTypes.createCheckField("Invulnerable", armorstand.isInvulnerable());
		invulnerableField.setOnChangeValue((old, neu, item) -> armorstand.setInvulnerable(neu));

		InventoryObjectField<Boolean> armsField = InventoryItemTypes.createCheckField("Arms", armorstand.hasArms());
		armsField.setOnChangeValue((old, neu, item) -> armorstand.setArms(neu));

		InventoryObjectField<Boolean> basePlateField = InventoryItemTypes.createCheckField("Base Plate", armorstand.hasBasePlate());
		basePlateField.setOnChangeValue((old, neu, item) -> armorstand.setBasePlate(neu));

		InventoryObjectField<Boolean> collidableField = InventoryItemTypes.createCheckField("Collidable", armorstand.isCollidable());
		collidableField.setOnChangeValue((old, neu, item) -> armorstand.setCollidable(neu));

		InventoryObjectField<Boolean> glowingField = InventoryItemTypes.createCheckField("Glowing", armorstand.isGlowing());
		glowingField.setOnChangeValue((old, neu, item) -> armorstand.setGlowing(neu));

		InventoryObjectField<Boolean> gravityField = InventoryItemTypes.createCheckField("Gravity", armorstand.hasGravity());
		gravityField.setOnChangeValue((old, neu, item) -> armorstand.setGravity(neu));

		InventoryObjectField<Boolean> glidingField = InventoryItemTypes.createCheckField("Gliding", armorstand.isGliding());
		glidingField.setOnChangeValue((old, neu, item) -> armorstand.setGliding(neu));

		InventoryObjectField<Boolean> customNameVisible = InventoryItemTypes.createCheckField("Custom Name Visible", armorstand.isCustomNameVisible());
		customNameVisible.setOnChangeValue((old, neu, item) -> armorstand.setCustomNameVisible(neu));

		InventoryObjectField<Boolean> markerField = InventoryItemTypes.createCheckField("Marker | Interactable", armorstand.isMarker());
		markerField.setOnChangeValue((old, neu, item) -> armorstand.setMarker(neu));

		InventoryItem reset = new InventoryItem(Material.GRAY_WOOL);
		reset.setDisplayName("Reset Gesture"); //TODO: Replace with Language
		reset.setOnClick((event, item) -> {
			armorstand.setHeadPose(new EulerAngle(0.05494790421765526, 0.11621542312304131, 0));
			armorstand.setBodyPose(new EulerAngle(0, 0.05842652523351677, 0));
			armorstand.setLeftArmPose(new EulerAngle(-0.17453292519943295, 0, -0.17453292519943295));
			armorstand.setRightArmPose(new EulerAngle(-0.2617993877991494, 0, 0.17453292519943295));
			armorstand.setLeftLegPose(new EulerAngle(-0.017453292519943295, 0, -0.017453292519943295));
			armorstand.setRightLegPose(new EulerAngle(0.017453292519943295, 0, 0.017453292519943295));
			event.setCancelled(true);
		});

		List<String> usage = new LinkedList<>();
		usage.add("<Cause> <Selected Slot>: <Result>"); //TODO: Replace with Language
		usage.add("Hit 0: x - 1");
		usage.add("Hit 1: y - 1");
		usage.add("Hit 2: z - 1");
		usage.add("Hit 4: x - 10");
		usage.add("Hit 5: y - 10");
		usage.add("Hit 6: z - 10");
		usage.add("Place <Slot>: [x,y,z] + <see above>");
		usage.add("Drop: Exit");
		usage.add("§4Warning this deaktivate Marker");

		InventoryItem head = new InventoryItem(Material.PLAYER_HEAD);
		head.setDisplayName("Move Head"); //TODO: Replace with Language
		head.setLore(usage);
		head.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.HEAD);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem body = new InventoryItem(Material.CHAINMAIL_CHESTPLATE);
		body.setDisplayName("Move Body"); //TODO: Replace with Language
		body.setLore(usage);
		body.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.BODY);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem leftArm = new InventoryItem(Material.STICK);
		leftArm.setDisplayName("Move Left Arm"); //TODO: Replace with Language
		leftArm.setLore(usage);
		leftArm.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.LEFT_ARM);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem rightArm = new InventoryItem(Material.STICK);
		rightArm.setDisplayName("Move Right Arm"); //TODO: Replace with Language
		rightArm.setLore(usage);
		rightArm.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.RIGHT_ARM);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem leftLeg = new InventoryItem(Material.DIAMOND_BOOTS);
		leftLeg.setDisplayName("Move Left Leg"); //TODO: Replace with Language
		leftLeg.setLore(usage);
		leftLeg.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.LEFT_LEG);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem rightLeg = new InventoryItem(Material.DIAMOND_BOOTS);
		rightLeg.setDisplayName("Move Right Leg"); //TODO: Replace with Language
		rightLeg.setLore(usage);
		rightLeg.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.RIGHT_LEG);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem position = new InventoryItem(Material.ARMOR_STAND);
		position.setDisplayName("Move Position"); //TODO: Replace with Language
		position.setLore(usage);
		position.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.POSITION);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem rotation = new InventoryItem(Material.ARROW);
		rotation.setDisplayName("Rotation"); //TODO: Replace with Language
		usage.clear();
		usage.add("Hit 1: rotation - 1");
		usage.add("Place 1: rotation + 1");
		usage.add("Hit 2: rotation - 10");
		usage.add("Place 2: rotation + 10");
		usage.add("Drop: Exit");
		usage.add("§4Warning this deaktivate Marker");
		rotation.setLore(usage);
		rotation.setOnClick((event, item) -> {
			PlayerConfig config = PlayerManager.getConfig(player);
			config.setTmp("armorstandEditorListener", true);
			config.setTmp("armorstandEditorEnum", ArmorstandBodyParts.ROTATION);
			armorstand.setMarker(false);
			player.closeInventory();
			event.setCancelled(true);
		});

		InventoryItem nothing = new InventoryItem(Material.GRAY_STAINED_GLASS_PANE);
		nothing.setOnClick((event, item) -> event.setCancelled(true));

		// 0 1 2 | 3 | 4 5 6 7 8
		page.addItem(0, nothing);
		page.addItem(1, armorstand.getEquipment().getHelmet());
		page.addItem(2, nothing);
		page.addItem(3, nothing);
		page.addItem(4, basePlateField);
		page.addItem(5, smallField);
		page.addItem(6, visibleField);
		page.addItem(7, gravityField);
		page.addItem(8, armsField);

		// 9 10 11 | 12 | 13 14 15 16 17
		page.addItem(9, armorstand.getEquipment().getItemInMainHand());
		page.addItem(10, armorstand.getEquipment().getChestplate());
		page.addItem(11, armorstand.getEquipment().getItemInOffHand());
		page.addItem(12, nothing);
		page.addItem(13, customNameVisible);
		page.addItem(14, glowingField);
		page.addItem(15, invulnerableField);
		page.addItem(16, markerField);
		page.addItem(17, glidingField);

		// 18 19 20 | 21 | 22 23 24 25 26
		page.addItem(18, nothing);
		page.addItem(19, armorstand.getEquipment().getLeggings());
		page.addItem(20, nothing);
		page.addItem(21, nothing);
		page.addItem(22, nothing);
		page.addItem(23, nothing);
		page.addItem(24, nothing);
		page.addItem(25, collidableField);
		page.addItem(26, reset);

		// 27 28 29 | 30 | 31 32 33 34 35
		page.addItem(27, nothing);
		page.addItem(28, armorstand.getEquipment().getBoots());
		page.addItem(29, nothing);
		page.addItem(30, nothing);
		page.addItem(31, nothing);
		page.addItem(32, nothing);
		page.addItem(33, nothing);
		page.addItem(34, nothing);
		page.addItem(35, nothing);

		// 36 37 38 | 39 40 41 42 43 44
		page.addItem(36, nothing);
		page.addItem(37, nothing);
		page.addItem(38, nothing);
		page.addItem(39, nothing);
		page.addItem(40, head);
		page.addItem(41, body);
		page.addItem(42, leftArm);
		page.addItem(43, rightArm);
		page.addItem(44, nothing);

		// 45 46 47 48 49 50 51 52 53
		page.addItem(45, nothing);
		page.addItem(46, nothing);
		page.addItem(47, nothing);
		page.addItem(48, nothing);
		page.addItem(49, leftLeg);
		page.addItem(50, rightLeg);
		page.addItem(51, position);
		page.addItem(52, rotation);
		page.addItem(53, nothing);

		factory.setOnClick((event, item) -> {
			int slot = event.getSlot();

			switch (slot) {
				case 1:
					armorstand.getEquipment().setHelmet(event.getCursor());
					break;
				case 9:
					armorstand.getEquipment().setItemInMainHand(event.getCursor());
					break;
				case 10:
					armorstand.getEquipment().setChestplate(event.getCursor());
					break;
				case 11:
					armorstand.getEquipment().setItemInOffHand(event.getCursor());
					break;
				case 19:
					armorstand.getEquipment().setLeggings(event.getCursor());
					break;
				case 28:
					armorstand.getEquipment().setBoots(event.getCursor());
					break;
			}
		});

		factory.refreshPage();
		factory.openInventory(player);
	}
}
