package essentials.modules.holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class HologramLine {
	private ArmorStand armorStand;
	
	public HologramLine(Location location) {
		spawn(location);
	}
	
	public HologramLine() {}
	
	public HologramLine(ArmorStand armorStand) {
		this.armorStand = armorStand;
	}
	
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
		
		ItemStack paper = new ItemStack(Material.PAPER);
		armorStand.getEquipment().setChestplate(paper);
		
		NBTTag nbtTag = getNBTTag();
		nbtTag.setBoolean("isHologram", true);
		nbtTag.setInt("position", 0);
		setNBTTag(nbtTag);
	}
	
	public void setText(String text) {
		armorStand.setCustomName(text);
	}
	
	public void setPosition(int position) {
		NBTTag nbtTag = getNBTTag();
		nbtTag.setInt("position", position);
		setNBTTag(nbtTag);
	}
	
	public int getPosition() {
		NBTTag nbtTag = getNBTTag();
		return nbtTag.getInt("position");
	}
	
	public void destroy() {
		armorStand.remove();
	}
	
	public ItemStack getNBTItemStack() {
		return armorStand.getEquipment().getChestplate();
	}
	
	public NBTTag getNBTTag() {
		ItemStack itemStack = getNBTItemStack();
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag != null) return nbtTag;
		return NBTUtilities.createNBTTag();
	}
	
	public void setNBTTag(NBTTag nbtTag) {
		NBTUtilities.setNBTTagCompound(getNBTItemStack(), nbtTag.getNBTTagCompound());
	}
	
	public static boolean isHologramLine(ArmorStand armorStand) {
		HologramLine hologramLine = new HologramLine(armorStand);
		NBTTag nbtTag = hologramLine.getNBTTag();
		return nbtTag.getBoolean("isHologram");
	}
	
	public static boolean isHologramStartLine(ArmorStand armorStand) {
		HologramLine hologramLine = new HologramLine(armorStand);
		NBTTag nbtTag = hologramLine.getNBTTag();
		if(!nbtTag.getBoolean("isHologram")) return false;
		return nbtTag.getInt("position") == 0;
	}
}
