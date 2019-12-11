package essentials.modules.holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import essentials.utilities.NBTUtilities;
import essentials.utilitiesvr.nbt.NBTTag;

public class HologramLine {
	private ArmorStand armorStand;
	
	public HologramLine(Location location, String ID) {
		spawn(location, ID);
	}
	
	public HologramLine() {}
	
	public HologramLine(ArmorStand armorStand) {
		this.armorStand = armorStand;
	}
	
	public void spawn(Location location, String ID) {
		if(armorStand != null) return;
		
		World world = location.getWorld();
		armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
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
		setNBTItemStack(paper);
		
		NBTTag nbtTag = getNBTTag();
		nbtTag.setBoolean("isHologram", true);
		nbtTag.setInt("position", 0);
		nbtTag.setString("HologramID", ID);
		setNBTTag(nbtTag);
	}
	
	public void setText(String text) {
		armorStand.setCustomName(text);
	}
	
	public String getText() {
		return armorStand.getCustomName();
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
	
	public void setID(String ID) {
		NBTTag nbtTag = getNBTTag();
		nbtTag.setString("HologramID", ID);
		setNBTTag(nbtTag);
	}
	
	public String getID() {
		NBTTag nbtTag = getNBTTag();
		return nbtTag.getString("HologramID");
	}
	
	public void destroy() {
		armorStand.remove();
	}
	
	public void setNBTItemStack(ItemStack itemStack) {
		armorStand.getEquipment().setChestplate(itemStack);
	}
	
	public ItemStack getNBTItemStack() {
		return armorStand.getEquipment().getChestplate();
	}
	
	public NBTTag getNBTTag() {
		ItemStack itemStack = getNBTItemStack();
		if(itemStack == null) return null;
		NBTTag nbtTag = NBTUtilities.getNBTTag(itemStack);
		if(nbtTag != null) return nbtTag;
		return NBTUtilities.createNBTTag();
	}
	
	public void setNBTTag(NBTTag nbtTag) {
		ItemStack itemStack = getNBTItemStack();
		NBTUtilities.setNBTTagCompound(itemStack, nbtTag.getNBTTagCompound());
		setNBTItemStack(itemStack);
	}
	
	public void teleport(Location location) {
		armorStand.teleport(location);
	}
	
	public boolean isHologramLine() {
		NBTTag nbtTag = getNBTTag();
		return nbtTag.getBoolean("isHologram");
	}
	
	public static boolean isHologramLine(ArmorStand armorStand) {
		HologramLine hologramLine = new HologramLine(armorStand);
		return hologramLine.isHologramLine();
	}
	
	public static boolean isHologramStartLine(ArmorStand armorStand) {
		HologramLine hologramLine = new HologramLine(armorStand);
		NBTTag nbtTag = hologramLine.getNBTTag();
		if(!nbtTag.getBoolean("isHologram")) return false;
		return nbtTag.getInt("position") == 0;
	}
}
