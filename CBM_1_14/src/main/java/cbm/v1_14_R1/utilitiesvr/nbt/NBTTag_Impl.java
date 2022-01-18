package cbm.v1_14_R1.utilitiesvr.nbt;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

import cbm.utilitiesvr.nbt.NBTTag;
import cbm.utilitiesvr.nbt.NBTUtilities;
import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagByte;
import net.minecraft.server.v1_14_R1.NBTTagByteArray;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagDouble;
import net.minecraft.server.v1_14_R1.NBTTagFloat;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagIntArray;
import net.minecraft.server.v1_14_R1.NBTTagList;
import net.minecraft.server.v1_14_R1.NBTTagLong;
import net.minecraft.server.v1_14_R1.NBTTagLongArray;
import net.minecraft.server.v1_14_R1.NBTTagShort;
import net.minecraft.server.v1_14_R1.NBTTagString;

public class NBTTag_Impl implements NBTTag {
	private NBTTagCompound nbtTagCompound;

	public NBTTag_Impl(ItemStack itemStack) {
		nbtTagCompound = (NBTTagCompound) NBTUtilities.getNBTTagCompound(itemStack);
		if(nbtTagCompound == null)
			nbtTagCompound = (NBTTagCompound) NBTUtilities.createNBTTagCompound();
	}
	
	public NBTTag_Impl(Object nbtTagCompound) {
		if(!(nbtTagCompound instanceof NBTTagCompound))
			throw new IllegalArgumentException();
		
		this.nbtTagCompound = (NBTTagCompound) nbtTagCompound;
	}
	
	@Override
	public boolean hasNBT() {
		return nbtTagCompound != null;
	}

	@Override
	public void set(String key, Object value) {
		if (!(value instanceof NBTBase))
			throw new IllegalArgumentException();
		nbtTagCompound.set(key, (NBTBase) value);
	}

	@Override
	public boolean getBoolean(String key) {
		return nbtTagCompound.getBoolean(key);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		nbtTagCompound.setBoolean(key, value);
	}

	@Override
	public byte getByte(String key) {
		return nbtTagCompound.getByte(key);
	}

	@Override
	public void setByte(String key, byte value) {
		nbtTagCompound.setByte(key, value);
	}

	@Override
	public NBTTagCompound getCompound(String key) {
		return nbtTagCompound.getCompound(key);
	}
	
	@Override
	public NBTTag getCompoundAsNBTTag(String key) {
		return new NBTTag_Impl(getCompound(key));
	}

	@Override
	public double getDouble(String key) {
		return nbtTagCompound.getDouble(key);
	}

	@Override
	public void setDouble(String key, double value) {
		nbtTagCompound.setDouble(key, value);
	}

	@Override
	public float getFloat(String key) {
		return nbtTagCompound.getFloat(key);
	}

	@Override
	public void setFloat(String key, float value) {
		nbtTagCompound.setFloat(key, value);
	}

	@Override
	public int getInt(String key) {
		return nbtTagCompound.getInt(key);
	}

	@Override
	public void setInt(String key, int value) {
		nbtTagCompound.setInt(key, value);
	}

	@Override
	public Set<String> getKeys() {
		return nbtTagCompound.getKeys();
	}

	@Override
	public NBTTagList getList(String key, int number) {
		return nbtTagCompound.getList(key, number);
	}

	@Override
	public long getLong(String key) {
		return nbtTagCompound.getLong(key);
	}

	@Override
	public void setLong(String key, long value) {
		nbtTagCompound.setLong(key, value);
	}

	@Override
	public short getShort(String key) {
		return nbtTagCompound.getShort(key);
	}

	@Override
	public void setShort(String key, short value) {
		nbtTagCompound.setShort(key, value);
	}

	@Override
	public String getString(String key) {
		return nbtTagCompound.getString(key);
	}

	@Override
	public void setString(String key, String value) {
		nbtTagCompound.setString(key, value);
	}

	@Override
	public byte getTypeID(String key) {
		return nbtTagCompound.getTypeId();
	}

	@Override
	public byte[] getByteArray(String key) {
		return nbtTagCompound.getByteArray(key);
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		nbtTagCompound.setByteArray(key, value);
	}

	@Override
	public int[] getIntArray(String key) {
		return nbtTagCompound.getIntArray(key);
	}

	@Override
	public void setIntArray(String key, int[] value) {
		nbtTagCompound.setIntArray(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		return nbtTagCompound.getLongArray(key);
	}

	@Override
	public void setToItemStack(ItemStack itemStack) {
		NBTUtilities.setNBTTagCompound(itemStack, nbtTagCompound);
	}

	@Override
	public NBTTagCompound getNBTTagCompound() {
		return nbtTagCompound;
	}
	
	@Override
	public NBTBase get(String key) {
		return nbtTagCompound.get(key);
	}
	
	@Override
	public Object getValue(String key) {
		NBTBase base = get(key);
		
		if(base instanceof NBTTagByte)
			return getByte(key);
		else if(base instanceof NBTTagCompound)
			return new NBTTag_Impl(base);
		else if(base instanceof NBTTagList)
			return base;
		else if(base instanceof NBTTagByteArray)
			return getByteArray(key);
		else if(base instanceof NBTTagDouble)
			return getDouble(key);
		else if(base instanceof NBTTagFloat)
			return getFloat(key);
		else if(base instanceof NBTTagInt)
			return getInt(key);
		else if(base instanceof NBTTagIntArray)
			return getIntArray(key);
		else if(base instanceof NBTTagLong)
			return getLong(key);
		else if(base instanceof NBTTagLongArray)
			return getLongArray(key);
		else if(base instanceof NBTTagShort)
			return getShort(key);
		else if(base instanceof NBTTagString)
			return getString(key);
		return null;
	}
	
	@Override
	public void remove(String key) {
		nbtTagCompound.remove(key);
	}
}
