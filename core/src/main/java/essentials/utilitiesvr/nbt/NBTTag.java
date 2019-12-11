package essentials.utilitiesvr.nbt;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public interface NBTTag {
	boolean hasNBT();
	
	/**
	 * @param key
	 * @param value instanceof NBTBase
	 */
	void set(String key, Object value);

	boolean getBoolean(String key);

	void setBoolean(String key, boolean value);

	byte getByte(String key);

	void setByte(String key, byte value);

	/**
	 * @param key
	 * @return NBTTagCompound
	 */
	Object getCompound(String key);

	NBTTag getCompoundAsNBTTag(String key);
	
	double getDouble(String key);

	void setDouble(String key, double value);

	float getFloat(String key);

	void setFloat(String key, float value);

	int getInt(String key);

	void setInt(String key, int value);

	Set<String> getKeys();

	/**
	 * @param key
	 * @param number
	 * @return NBTTagList
	 */
	List<?> getList(String key, int number);

	long getLong(String key);

	void setLong(String key, long value);

	short getShort(String key);

	void setShort(String key, short value);

	String getString(String key);

	void setString(String key, String value);

	byte getTypeID(String key);

	byte[] getByteArray(String key);

	void setByteArray(String key, byte[] value);

	int[] getIntArray(String key);

	void setIntArray(String key, int[] value);

	long[] getLongArray(String key);

	void setToItemStack(ItemStack itemStack);

	/**
	 * @return NBTTagCompound
	 */
	Object getNBTTagCompound();
	
	Object get(String key);

	Object getValue(String key);

	void remove(String key);
}
