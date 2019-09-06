package essentials.utilitiesvr.nbt;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public interface NBTTag {
	/**
	 * @param key
	 * @param value instanceof NBTBase
	 */
	public void set(String key, Object value);
	
	public boolean getBoolean(String key);
	
	public void setBoolean(String key, boolean value);
	
	public byte getByte(String key);
	
	public void setByte(String key, byte value);
	
	/**
	 * 
	 * @param key
	 * @return NBTTagCompound
	 */
	public Object getCompound(String key);
	
	public double getDouble(String key);
	
	public void setDouble(String key, double value);
	
	public float getFloat(String key);
	
	public void setFloat(String key, float value);
	
	public int getInt(String key);
	
	public void setInt(String key, int value);
	
	public Set<String> getKeys();
	
	/**
	 * 
	 * @param key
	 * @param number
	 * @return NBTTagList
	 */
	public List<?> getList(String key, int number);
	
	public long getLong(String key);
	
	public void setLong(String key, long value);
	
	public short getShort(String key);
	
	public void setShort(String key, short value);
	
	public String getString(String key);
	
	public void setString(String key, String value);
	
	public byte getTypeID(String key);
	
	public byte[] getByteArray(String key);
	
	public void setByteArray(String key, byte[] value);
	
	public int[] getIntArray(String key);
	
	public void setIntArray(String key, int[] value);
	
	public long[] getLongArray(String key);
	
	public void setToItemStack(ItemStack itemStack);
	
	/**
	 * 
	 * @return NBTTagCompound
	 */
	public Object getNBTTagCompound();
}
