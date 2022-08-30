package cbm.v1_19_R1.utilitiesvr.nbt;

import java.util.Set;

import org.bukkit.inventory.ItemStack;

import cbm.utilitiesvr.nbt.NBTTag;
import cbm.utilitiesvr.nbt.NBTUtilities;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

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
		nbtTagCompound.a(key, (NBTBase) value);
	}

	@Override
	public boolean getBoolean(String key) {
		return nbtTagCompound.q(key);
	}

	@Override
	public void setBoolean(String key, boolean value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public byte getByte(String key) {
		return nbtTagCompound.d(key);
	}

	@Override
	public void setByte(String key, byte value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public NBTTagCompound getCompound(String key) {
		return nbtTagCompound.p(key);
	}
	
	@Override
	public NBTTag getCompoundAsNBTTag(String key) {
		return new NBTTag_Impl(getCompound(key));
	}

	@Override
	public double getDouble(String key) {
		return nbtTagCompound.k(key);
	}

	@Override
	public void setDouble(String key, double value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public float getFloat(String key) {
		return nbtTagCompound.j(key);
	}

	@Override
	public void setFloat(String key, float value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public int getInt(String key) {
		return nbtTagCompound.h(key);
	}

	@Override
	public void setInt(String key, int value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public Set<String> getKeys() {
		return nbtTagCompound.d();
	}

	@Override
	public NBTTagList getList(String key, int number) {
		return nbtTagCompound.c(key, number);
	}

	@Override
	public long getLong(String key) {
		return nbtTagCompound.i(key);
	}

	@Override
	public void setLong(String key, long value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public short getShort(String key) {
		return nbtTagCompound.g(key);
	}

	@Override
	public void setShort(String key, short value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public String getString(String key) {
		return nbtTagCompound.l(key);
	}

	@Override
	public void setString(String key, String value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public byte getTypeID(String key) {
		return nbtTagCompound.a();
	}

	@Override
	public byte[] getByteArray(String key) {
		return nbtTagCompound.m(key);
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public int[] getIntArray(String key) {
		return nbtTagCompound.n(key);
	}

	@Override
	public void setIntArray(String key, int[] value) {
		nbtTagCompound.a(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		return nbtTagCompound.o(key);
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
		return nbtTagCompound.c(key);
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
		nbtTagCompound.r(key);
	}
}
