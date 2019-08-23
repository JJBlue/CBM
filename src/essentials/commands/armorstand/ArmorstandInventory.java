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
	
	public ArmorstandInventory(ArmorStand armorstand) {
		this.armorstand = armorstand;
	}
	
	public void openInventory(Player player) {
		InventoryFactory factory = new InventoryFactory(Bukkit.createInventory(null, 54));
		InventoryPage page = factory.createFirstPage();
		
		
		armorstand.setArms(arg0);
		armorstand.setBasePlate(arg0);
		armorstand.setBodyPose(arg0);
		armorstand.setCollidable(arg0);
		armorstand.setGlowing(arg0);
		armorstand.setGravity(arg0);
		armorstand.setGliding(arg0);
		armorstand.setInvulnerable(arg0);
		armorstand.setSmall(arg0);
		
		InventoryObjectField<Boolean> visible = InventoryItemTypes.createCheckField(armorstand.isVisible());
		visible.setOnChangeValue((old, neu, item) -> {
			armorstand.setVisible(neu);
		});
		
		InventoryObjectField<Boolean> visible = InventoryItemTypes.createCheckField(armorstand.isVisible());
		visible.setOnChangeValue((old, neu, item) -> {
			armorstand.setVisible(neu);
		});
		
		
		factory.openInventory(player);
	}
}
