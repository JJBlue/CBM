package cbm.v1_18_R1.utilitiesvr.nbt;

import java.util.List;

import org.bukkit.craftbukkit.v1_18_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import cbm.utilitiesvr.nbt.NBTTag;
import cbm.utilitiesvr.nbt.NBTUtilities_Interface;
import net.minecraft.nbt.MojangsonParser;
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

public class NBTUtilities_Impl implements NBTUtilities_Interface {
	/**
	 * 
	 * @return NBTTagCompound
	 */	
	@Override
	public Object getNBTTagCompound(ItemStack itemstack) {
		return CraftItemStack.asNMSCopy(itemstack).s();
	}
	
	public NBTTagCompound getNbtTagCompound(Entity entity) {
		return ((CraftEntity) entity).getHandle().f(new NBTTagCompound());
	}
	
	/**
	 * 
	 * @param s as json
	 * @return NBTTagCompound
	 */
	@Override
	public Object parse(String s) {
		try {
			return MojangsonParser.a(s);
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
		
		net.minecraft.world.item.ItemStack is = CraftItemStack.asNMSCopy(itemstack);
		is.b((NBTTagCompound) nbtTagCompound);
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
			return ((NBTTagByte) base).a();
		else if(base instanceof NBTTagCompound)
			return new NBTTag_Impl(base);
		else if(base instanceof NBTTagList)
			return base;
		else if(base instanceof NBTTagByteArray)
			return ((NBTTagByteArray) base).d();
		else if(base instanceof NBTTagDouble)
			return ((NBTTagDouble) base).i();
		else if(base instanceof NBTTagFloat)
			return ((NBTTagFloat) base).j();
		else if(base instanceof NBTTagInt)
			return ((NBTTagInt) base).f();
		else if(base instanceof NBTTagIntArray)
			return ((NBTTagIntArray) base).f();
		else if(base instanceof NBTTagLong)
			return ((NBTTagLong) base).e();
		else if(base instanceof NBTTagLongArray)
			return ((NBTTagLongArray) base).f();
		else if(base instanceof NBTTagShort)
			return ((NBTTagShort) base).g();
		else if(base instanceof NBTTagString)
			return ((NBTTagString) base).e_();
		return null;
	}

	@Override
	public NBTTag getNBTTag(ItemStack itemstack) {
		return new NBTTag_Impl(itemstack);
	}
}
