package cbm.utilitiesvr.nbt;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface NBTUtilities_Interface {
	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public Object getNBTTagCompound(ItemStack itemstack);

	public NBTTag getNBTTag(ItemStack itemstack);

	/**
	 * @param ItemStack      itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound);

	/**
	 * NBTTagCompound
	 */
	public Object createNBTTagCompound();
	
	public NBTTag createNBTTag();

	/**
	 * @return NBTTagList
	 */
	public List<?> createNBTTagList();
	
	public Object createNBTBase(Object value);
	
	/**
	 * 
	 * @param nbtbase instanceof NBTBase
	 * @return
	 */
	public Object getValue(Object nbtbase);
	
	public Object parse(String s);
}
