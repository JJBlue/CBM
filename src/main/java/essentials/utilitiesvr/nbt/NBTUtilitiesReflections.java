package essentials.utilitiesvr.nbt;

import components.reflections.SimpleReflection;
import essentials.utilitiesvr.ReflectionsUtilities;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

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
			if (!nbtTagCompoundClass.isAssignableFrom(nbtTagCompound.getClass())) return;

			Class<?> craftItemStack = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
			Object mcItemStack = SimpleReflection.callStaticMethod(craftItemStack, "asNMSCopy", itemstack);
			SimpleReflection.callMethod(mcItemStack, "setTag", nbtTagCompound);
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

	public static Object createNBTTagList() {
		try {
			Class<?> nbt = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTTagList");
			return SimpleReflection.createObject(nbt);
		} catch (InstantiationException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Object nbtTagCompound;

	public NBTUtilitiesReflections(ItemStack itemStack) {
		nbtTagCompound = getNBTTagCompound(itemStack);
	}

	@Override
	public void set(String key, Object value) {
		try {
			Class<?> nbtBase = Class.forName("net.minecraft.server." + ReflectionsUtilities.getPackageVersionName() + ".NBTBase");
			if (!nbtBase.isAssignableFrom(value.getClass())) return;
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

}
