package essentials.utilitiesvr.itemstack;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import components.reflections.SimpleReflection;
import essentials.utilities.minecraft.ReflectionsUtilities;

public class ItemStackUtilitiesReflections {
	public static Object craftItemStackAsNMSCopy(ItemStack itemstack) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> classy = Class.forName("org.bukkit.craftbukkit." + ReflectionsUtilities.getPackageVersionName() + ".inventory.CraftItemStack");
		return SimpleReflection.callStaticMethod(classy, "asNMSCopy", itemstack);
	}
}
