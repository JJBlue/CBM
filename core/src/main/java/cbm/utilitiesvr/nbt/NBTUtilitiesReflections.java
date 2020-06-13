package cbm.utilitiesvr.nbt;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cbm.versions.minecraft.ReflectionsUtilities;
import components.reflection.ConstructorReflection;
import components.reflection.MethodReflection;

public class NBTUtilitiesReflections implements NBTTag {
	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		try {
			Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
			Object mcItemStack = MethodReflection.callStaticMethod(craftItemStack, "asNMSCopy", itemstack);
			return MethodReflection.callMethod(mcItemStack, "getTag");
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		try {
			Class<?> nbtTagCompoundClass = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound");
			if (!nbtTagCompoundClass.isAssignableFrom(nbtTagCompound.getClass())) 
				throw new IllegalArgumentException();

			Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
			Object mcItemStack = MethodReflection.callStaticMethod(craftItemStack, "asNMSCopy", itemstack);
			MethodReflection.callMethod(mcItemStack, "setTag", nbtTagCompound);
			itemstack.setItemMeta((ItemMeta) MethodReflection.callStaticMethod(craftItemStack, "getItemMeta", mcItemStack));
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static Object parse(String s) {
		try {
			Class<?> mojangparser = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".MojangsonParser");
			return MethodReflection.callStaticMethod(mojangparser, "parse", s);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object createNBTTagCompound() {
		try {
			Class<?> nbt = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound");
			return ConstructorReflection.createObject(nbt);
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static NBTTag createNBTTag() {
		return new NBTUtilitiesReflections(createNBTTagCompound());
	}

	public static List<?> createNBTTagList() {
		try {
			Class<?> nbt = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagList");
			return (List<?>) ConstructorReflection.createObject(nbt);
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param value
	 * @return NBTBase
	 */
	public static Object createNBTBase(Object value) {
		try {
			if(value instanceof Byte)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByte"), "a", value);
			else if(value instanceof byte[])
				return ConstructorReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByteArray"), value);
			else if(value instanceof Double)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagDouble"), "a", value);
			else if(value instanceof Float)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagFloat"), "a", value);
			else if(value instanceof Integer)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagInt"), "a", value);
			else if(value instanceof int[])
				return ConstructorReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagIntArray"), value);
			else if(value instanceof Long)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLong"), "a", value);
			else if(value instanceof long[])
				return ConstructorReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLongArray"), value);
			else if(value instanceof Short)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagShort"), "a", value);
			else if(value instanceof String)
				return MethodReflection.callStaticMethod(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagString"), "a", value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getValue(Object nbtbase) {
		Class<?> nbtBaseClass = nbtbase.getClass();
		
		try {
			if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByte").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asByte");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound").isAssignableFrom(nbtBaseClass))
				return new NBTUtilitiesReflections(nbtbase);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagList").isAssignableFrom(nbtBaseClass))
				return nbtbase;
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByteArray").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "getBytes");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagDouble").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asDouble");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagFloat").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asFloat");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagInt").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asInt");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagIntArray").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "getInts");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLong").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asLong");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLongArray").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "getLongs");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagShort").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asShort");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagString").isAssignableFrom(nbtBaseClass))
				return MethodReflection.callMethod(nbtbase, "asString");
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------
	private Object nbtTagCompound;

	public NBTUtilitiesReflections(ItemStack itemStack) {
		nbtTagCompound = getNBTTagCompound(itemStack);
		if(nbtTagCompound == null)
			nbtTagCompound = createNBTTagCompound();
	}
	
	public boolean hasNBT() {
		return nbtTagCompound != null;
	}
	
	public NBTUtilitiesReflections(Object nbtTagCompound) {
		try {
			Class<?> nbtBase = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound");
			if (!nbtBase.isAssignableFrom(nbtTagCompound.getClass()))
				throw new IllegalArgumentException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		this.nbtTagCompound = nbtTagCompound;
	}

	@Override
	public void set(String key, Object value) {
		try {
			Class<?> nbtBase = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTBase");
			if (!nbtBase.isAssignableFrom(value.getClass()))
				throw new IllegalArgumentException();
			MethodReflection.callMethod(nbtTagCompound, "set", key, value);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean getBoolean(String key) {
		try {
			return (boolean) MethodReflection.callMethod(nbtTagCompound, "getBoolean", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setBoolean(String key, boolean value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setBoolean", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte getByte(String key) {
		try {
			return (byte) MethodReflection.callMethod(nbtTagCompound, "getByte", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setByte(String key, byte value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setByte", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getCompound(String key) {
		try {
			return MethodReflection.callMethod(nbtTagCompound, "getCompound", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NBTTag getCompoundAsNBTTag(String key) {
		return new NBTUtilitiesReflections(getCompound(key));
	}
	
	@Override
	public double getDouble(String key) {
		try {
			return (double) MethodReflection.callMethod(nbtTagCompound, "getDouble", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setDouble(String key, double value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setDouble", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float getFloat(String key) {
		try {
			return (float) MethodReflection.callMethod(nbtTagCompound, "getFloat", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setFloat(String key, float value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setFloat", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getInt(String key) {
		try {
			return (int) MethodReflection.callMethod(nbtTagCompound, "getInt", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setInt(String key, int value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setInt", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getKeys() {
		try {
			return (Set<String>) MethodReflection.callMethod(nbtTagCompound, "getKeys");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getList(String key, int number) {
		try {
			return (List<?>) MethodReflection.callMethod(nbtTagCompound, "getList", key, number);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getLong(String key) {
		try {
			return (int) MethodReflection.callMethod(nbtTagCompound, "getLong", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setLong(String key, long value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setLong", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public short getShort(String key) {
		try {
			return (short) MethodReflection.callMethod(nbtTagCompound, "getShort", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setShort(String key, short value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setShort", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getString(String key) {
		try {
			return (String) MethodReflection.callMethod(nbtTagCompound, "getString", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setString(String key, String value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setString", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte getTypeID(String key) {
		try {
			return (byte) MethodReflection.callMethod(nbtTagCompound, "getTypeID", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public byte[] getByteArray(String key) {
		try {
			return (byte[]) MethodReflection.callMethod(nbtTagCompound, "getByteArray", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setByteArray", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int[] getIntArray(String key) {
		try {
			return (int[]) MethodReflection.callMethod(nbtTagCompound, "getIntArray", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setIntArray(String key, int[] value) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "setIntArray", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long[] getLongArray(String key) {
		try {
			return (long[]) MethodReflection.callMethod(nbtTagCompound, "getLongArray", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setToItemStack(ItemStack itemStack) {
		setNBTTagCompound(itemStack, nbtTagCompound);
	}

	@Override
	public Object getNBTTagCompound() {
		return nbtTagCompound;
	}

	@Override
	public Object get(String key) {
		try {
			return MethodReflection.callMethod(nbtTagCompound, "get", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object getValue(String key) {
		Object nbtbase = get(key);
		Class<?> nbtBaseClass = nbtbase.getClass();
		
		try {
			if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByte").isAssignableFrom(nbtBaseClass))
				return getByte(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound").isAssignableFrom(nbtBaseClass))
				return new NBTUtilitiesReflections(nbtbase);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagList").isAssignableFrom(nbtBaseClass))
				return nbtbase;
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByteArray").isAssignableFrom(nbtBaseClass))
				return getByteArray(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagDouble").isAssignableFrom(nbtBaseClass))
				return getDouble(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagFloat").isAssignableFrom(nbtBaseClass))
				return getFloat(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagInt").isAssignableFrom(nbtBaseClass))
				return getInt(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagIntArray").isAssignableFrom(nbtBaseClass))
				return getIntArray(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLong").isAssignableFrom(nbtBaseClass))
				return getLong(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLongArray").isAssignableFrom(nbtBaseClass))
				return getLongArray(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagShort").isAssignableFrom(nbtBaseClass))
				return getShort(key);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagString").isAssignableFrom(nbtBaseClass))
				return getString(key);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(String key) {
		try {
			MethodReflection.callMethod(nbtTagCompound, "remove", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
