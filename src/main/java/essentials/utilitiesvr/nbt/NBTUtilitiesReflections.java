package essentials.utilitiesvr.nbt;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import components.reflections.SimpleReflection;
import essentials.utilitiesvr.ReflectionsUtilities;

public class NBTUtilitiesReflections implements NBTTag {
	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		try {
			Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
			Object mcItemStack = SimpleReflection.callStaticMethod(craftItemStack, "asNMSCopy", itemstack);
			return SimpleReflection.callMethod(mcItemStack, "getTag");
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
			Object mcItemStack = SimpleReflection.callStaticMethod(craftItemStack, "asNMSCopy", itemstack);
			SimpleReflection.callMethod(mcItemStack, "setTag", nbtTagCompound);
			itemstack.setItemMeta((ItemMeta) SimpleReflection.callStaticMethod(craftItemStack, "getItemMeta", mcItemStack));
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static Object createNBTTagCompound() {
		try {
			Class<?> nbt = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound");
			return SimpleReflection.createObject(nbt);
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
			return (List<?>) SimpleReflection.createObject(nbt);
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
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByte"), value);
			else if(value instanceof byte[])
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByteArray"), value);
			else if(value instanceof Double)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagDouble"), value);
			else if(value instanceof Float)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagFloat"), value);
			else if(value instanceof Integer)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagInt"), value);
			else if(value instanceof int[])
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagIntArray"), value);
			else if(value instanceof Long)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLong"), value);
			else if(value instanceof long[])
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLongArray"), value);
			else if(value instanceof Short)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagShort"), value);
			else if(value instanceof String)
				return SimpleReflection.createObject(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagString"), value);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getValue(Object nbtbase) {
		Class<?> nbtBaseClass = nbtbase.getClass();
		
		try {
			if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByte").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asByte");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagCompound").isAssignableFrom(nbtBaseClass))
				return new NBTUtilitiesReflections(nbtbase);
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagList").isAssignableFrom(nbtBaseClass))
				return (List<?>) nbtbase;
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagByteArray").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "getBytes");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagDouble").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asDouble");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagFloat").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asFloat");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagInt").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asInt");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagIntArray").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "getInts");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLong").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asLong");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagLongArray").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "getLongs");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagShort").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asShort");
			else if(Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagString").isAssignableFrom(nbtBaseClass))
				return SimpleReflection.callMethod(nbtbase, "asString");
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
			SimpleReflection.callMethod(nbtTagCompound, "set", key, value);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean getBoolean(String key) {
		try {
			return (boolean) SimpleReflection.callMethod(nbtTagCompound, "getBoolean", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void setBoolean(String key, boolean value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setBoolean", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte getByte(String key) {
		try {
			return (byte) SimpleReflection.callMethod(nbtTagCompound, "getByte", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setByte(String key, byte value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setByte", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getCompound(String key) {
		try {
			return SimpleReflection.callMethod(nbtTagCompound, "getCompound", key);
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
			return (double) SimpleReflection.callMethod(nbtTagCompound, "getDouble", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setDouble(String key, double value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setDouble", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public float getFloat(String key) {
		try {
			return (float) SimpleReflection.callMethod(nbtTagCompound, "getFloat", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setFloat(String key, float value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setFloat", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getInt(String key) {
		try {
			return (int) SimpleReflection.callMethod(nbtTagCompound, "getInt", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setInt(String key, int value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setInt", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getKeys() {
		try {
			return (Set<String>) SimpleReflection.callMethod(nbtTagCompound, "getKeys");
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<?> getList(String key, int number) {
		try {
			return (List<?>) SimpleReflection.callMethod(nbtTagCompound, "getList", key, number);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getLong(String key) {
		try {
			return (int) SimpleReflection.callMethod(nbtTagCompound, "getLong", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setLong(String key, long value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setLong", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public short getShort(String key) {
		try {
			return (short) SimpleReflection.callMethod(nbtTagCompound, "getShort", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void setShort(String key, short value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setShort", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getString(String key) {
		try {
			return (String) SimpleReflection.callMethod(nbtTagCompound, "getString", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setString(String key, String value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setString", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte getTypeID(String key) {
		try {
			return (byte) SimpleReflection.callMethod(nbtTagCompound, "getTypeID", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public byte[] getByteArray(String key) {
		try {
			return (byte[]) SimpleReflection.callMethod(nbtTagCompound, "getByteArray", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setByteArray(String key, byte[] value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setByteArray", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int[] getIntArray(String key) {
		try {
			return (int[]) SimpleReflection.callMethod(nbtTagCompound, "getIntArray", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setIntArray(String key, int[] value) {
		try {
			SimpleReflection.callMethod(nbtTagCompound, "setIntArray", key, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public long[] getLongArray(String key) {
		try {
			return (long[]) SimpleReflection.callMethod(nbtTagCompound, "getLongArray", key);
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
			return SimpleReflection.callMethod(nbtTagCompound, "get", key);
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
				return (List<?>) nbtbase;
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
			SimpleReflection.callMethod(nbtTagCompound, "remove", key);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
