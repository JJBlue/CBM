package essentials.modules.holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class Hologram {
	private ArmorStand armorStand;
	
	public Hologram(Location location) {
		spawn(location);
	}
	
	public Hologram() {}
	
	public void spawn(Location location) {
		if(armorStand != null) return;
		
		armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		armorStand.setAI(false);
		armorStand.setGravity(false);
		armorStand.setMarker(true);
		armorStand.setInvulnerable(true);
		armorStand.setGravity(false);
		armorStand.setSmall(true);
		armorStand.setVisible(false);
		armorStand.setCustomNameVisible(true);
		armorStand.setCollidable(false);
		armorStand.setCanPickupItems(false);
		armorStand.setRemoveWhenFarAway(false);
		armorStand.setArms(true);
		
		ItemStack paper = new ItemStack(Material.PAPER);
		armorStand.getEquipment().setItemInMainHand(paper);
	}
	
	public ItemStack getNBTItemStack() {
		return armorStand.getEquipment().getItemInMainHand();
	}
	
	public NBTTag getNBTTag() {
		return NBTUtilities.getNBTTag(getNBTItemStack());
	}
	
	public void setNBTTag(NBTTag nbtTag) {
		NBTUtilities.setNBTTagCompound(getNBTItemStack(), nbtTag.getNBTTagCompound());
	}
}
