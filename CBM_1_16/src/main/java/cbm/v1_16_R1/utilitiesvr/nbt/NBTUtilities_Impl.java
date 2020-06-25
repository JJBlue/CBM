package cbm.v1_16_R1.utilitiesvr.nbt;

import java.util.List;

import org.bukkit.craftbukkit.v1_16_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import cbm.utilitiesvr.nbt.NBTTag;
import cbm.utilitiesvr.nbt.NBTUtilities_Interface;
import net.minecraft.server.v1_16_R1.MojangsonParser;
import net.minecraft.server.v1_16_R1.NBTTagByte;
import net.minecraft.server.v1_16_R1.NBTTagByteArray;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagDouble;
import net.minecraft.server.v1_16_R1.NBTTagFloat;
import net.minecraft.server.v1_16_R1.NBTTagInt;
import net.minecraft.server.v1_16_R1.NBTTagIntArray;
import net.minecraft.server.v1_16_R1.NBTTagList;
import net.minecraft.server.v1_16_R1.NBTTagLong;
import net.minecraft.server.v1_16_R1.NBTTagLongArray;
import net.minecraft.server.v1_16_R1.NBTTagShort;
import net.minecraft.server.v1_16_R1.NBTTagString;

public class NBTUtilities_Impl implements NBTUtilities_Interface {
	/**
	 * 
	 * @return NBTTagCompound
	 */	
	@Override
	public Object getNBTTagCompound(ItemStack itemstack) {
		return CraftItemStack.asNMSCopy(itemstack).getTag();
	}
	
	public NBTTagCompound getNbtTagCompound(Entity entity) {
		return ((CraftEntity) entity).getHandle().save(new NBTTagCompound());
	}
	
	/**
	 * 
	 * @param s as json
	 * @return NBTTagCompound
	 */
	@Override
	public Object parse(String s) {
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
	@Override
	public void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		if (!(nbtTagCompound instanceof NBTTagCompound)) 
			throw new IllegalArgumentException();
		
		net.minecraft.server.v1_16_R1.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		is.setTag((NBTTagCompound) nbtTagCompound);
		itemstack.setItemMeta(CraftItemStack.getItemMeta(is));
	}
	
	/**
	 * 
	 * @return NBTTagCompound
	 */
	@Override
	public Object createNBTTagCompound() {
		return new NBTTagCompound();
	}
	
	@Override
	public NBTTag createNBTTag() {
		return new NBTTag_Impl(new NBTTagCompound());
	}

	/**
	 * 
	 * @return NBTTagList
	 */
	@Override
	public List<?> createNBTTagList() {
		return new NBTTagList();
	}
	
	/**
	 * 
	 * @return NBTBase
	 */
	@Override
	public Object createNBTBase(Object value) {
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
	
	@Override
	public Object getValue(Object base) {
		if(base instanceof NBTTagByte)
			return ((NBTTagByte) base).asByte();
		else if(base instanceof NBTTagCompound)
			return new NBTTag_Impl(base);
		else if(base instanceof NBTTagList)
			return base;
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

	@Override
	public NBTTag getNBTTag(ItemStack itemstack) {
		return new NBTTag_Impl(itemstack);
	}
}
