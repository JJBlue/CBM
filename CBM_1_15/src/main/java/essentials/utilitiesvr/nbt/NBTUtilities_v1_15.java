package essentials.utilitiesvr.nbt;

import java.util.List;
import java.util.Set;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTBase;
import net.minecraft.server.v1_15_R1.NBTTagByte;
import net.minecraft.server.v1_15_R1.NBTTagByteArray;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagDouble;
import net.minecraft.server.v1_15_R1.NBTTagFloat;
import net.minecraft.server.v1_15_R1.NBTTagInt;
import net.minecraft.server.v1_15_R1.NBTTagIntArray;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.NBTTagLong;
import net.minecraft.server.v1_15_R1.NBTTagLongArray;
import net.minecraft.server.v1_15_R1.NBTTagShort;
import net.minecraft.server.v1_15_R1.NBTTagString;

public class NBTUtilities_v1_15 implements NBTTag {
	/**
	 * 
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		return CraftItemStack.asNMSCopy(itemstack).getTag();
	}
	
	public static NBTTagCompound getNbtTagCompound(Entity entity) {
		return ((CraftEntity) entity).getHandle().save(new NBTTagCompound());
	}
	
	/**
	 * 
	 * @param s as json
	 * @return NBTTagCompound
	 */
	public static Object parse(String s) {
		try {
			return MojangsonParser.parse(s);
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		if (!(nbtTagCompound instanceof NBTTagCompound)) 
			throw new IllegalArgumentException();
		
		net.minecraft.server.v1_15_R1.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		is.setTag((NBTTagCompound) nbtTagCompound);
		itemstack.setItemMeta(CraftItemStack.getItemMeta(is));
	}
	
	/**
	 * 
	 * @return NBTTagCompound
	 */
	public static Object createNBTTagCompound() {
		return new NBTTagCompound();
	}
	
	public static NBTTag createNBTTag() {
		return new NBTUtilities_v1_15(new NBTTagCompound());
	}

	/**
	 * 
	 * @return NBTTagList
	 */
	public static List<?> createNBTTagList() {
		return new NBTTagList();
	}
	
	/**
	 * 
	 * @return NBTBase
	 */
	public static Object createNBTBase(Object value) {
		if(value instanceof Byte)
			return NBTTagByte.a((byte) value);
		else if(value instanceof byte[])
			return new NBTTagByteArray((byte[]) value);
		else if(value instanceof Double)
			return NBTTagDouble.a((double) value);
		else if(value instanceof Float)
			return NBTTagFloat.a((float) value);
		else if(value instanceof Integer)
			return NBTTagInt.a((int) value);
		else if(value instanceof int[])
			return new NBTTagIntArray((int[]) value);
		else if(value instanceof Long)
			return NBTTagLong.a((long) value);
		else if(value instanceof long[])
			return new NBTTagLongArray((long[]) value);
		else if(value instanceof Short)
			return NBTTagShort.a((short) value);
		else if(value instanceof String)
			return NBTTagString.a((String) value);
		return null;
	}
	
	public static Object getValue(Object base) {
		if(base instanceof NBTTagByte)
			return ((NBTTagByte) base).asByte();
		else if(base instanceof NBTTagCompound)
			return new NBTUtilities_v1_15(base);
		else if(base instanceof NBTTagList)
			return (List<?>) base;
		else if(base instanceof NBTTagByteArray)
			return ((NBTTagByteArray) base).getBytes();
		else if(base instanceof NBTTagDouble)
			return ((NBTTagDouble) base).asDouble();
		else if(base instanceof NBTTagFloat)
			return ((NBTTagFloat) base).asFloat();
		else if(base instanceof NBTTagInt)
			return ((NBTTagInt) base).asInt();
		else if(base instanceof NBTTagIntArray)
			return ((NBTTagIntArray) base).getInts();
		else if(base instanceof NBTTagLong)
			return ((NBTTagLong) base).asLong();
		else if(base instanceof NBTTagLongArray)
			return ((NBTTagLongArray) base).getLongs();
		else if(base instanceof NBTTagShort)
			return ((NBTTagShort) base).asShort();
		else if(base instanceof NBTTagString)
			return ((NBTTagString) base).asString();
		return null;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private NBTTagCompound nbtTagCompound;

	public NBTUtilities_v1_15(ItemStack itemStack) {
		nbtTagCompound = (NBTTagCompound) getNBTTagCompound(itemStack);
		if(nbtTagCompound == null)
			nbtTagCompound = (NBTTagCompound) createNBTTagCompound();
	}
	
	public NBTUtilities_v1_15(Object nbtTagCompound) {
		if(!(nbtTagCompound instanceof NBTTagCompound))
			throw new IllegalArgumentException();
		
		this.nbtTagCompound = (NBTTagCompound) nbtTagCompound;
	}
	
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
		return new NBTUtilities_v1_15(getCompound(key));
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
		setNBTTagCompound(itemStack, nbtTagCompound);
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
			return new NBTUtilities_v1_15(base);
		else if(base instanceof NBTTagList)
			return (List<?>) base;
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
