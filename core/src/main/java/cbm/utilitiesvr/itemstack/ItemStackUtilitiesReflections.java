package cbm.utilitiesvr.itemstack;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import cbm.versions.minecraft.ReflectionsUtilities;
import components.reflection.MethodReflection;

public class ItemStackUtilitiesReflections {
	public static Object craftItemStackAsNMSCopy(ItemStack itemstack) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classy = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
		return MethodReflection.callStaticMethod(classy, "asNMSCopy", itemstack);
	}
}
