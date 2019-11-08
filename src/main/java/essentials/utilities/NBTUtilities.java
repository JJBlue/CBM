package essentials.utilities;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import essentials.utilitiesvr.ReflectionsUtilities;
import essentials.utilitiesvr.nbt.NBTTag;
import essentials.utilitiesvr.nbt.NBTUtilitiesReflections;
import essentials.utilitiesvr.nbt.NBTUtilities_v1_14;

public class NBTUtilities {
	private NBTUtilities() {}

	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		if (ReflectionsUtilities.getPackageVersionName().equalsIgnoreCase("v1_14_R1"))
			return NBTUtilities_v1_14.getNBTTagCompound(itemstack);
		return NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	public static NBTTag getNBTTag(ItemStack itemstack) {
		if (ReflectionsUtilities.getPackageVersionName().equalsIgnoreCase("v1_14_R1"))
			return new NBTUtilities_v1_14(itemstack);
		return new NBTUtilitiesReflections(itemstack);
	}

	/**
	 * @param ItemStack      itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				NBTUtilities_v1_14.setNBTTagCompound(itemstack, nbtTagCompound);
				return;
		}
		NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	/**
	 * NBTTagCompound
	 */
	public static Object createNBTTagCompound() {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.createNBTTagCompound();
		}
		return NBTUtilitiesReflections.createNBTTagCompound();
	}
	
	public static NBTTag createNBTTag() {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.createNBTTag();
		}
		return NBTUtilitiesReflections.createNBTTag();
	}

	/**
	 * @return NBTTagList
	 */
	public static List<?> createNBTTagList() {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.createNBTTagList();
		}
		return NBTUtilitiesReflections.createNBTTagList();
	}
	
	public static Object createNBTBase(Object value) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.createNBTBase(value);
		}
		return NBTUtilitiesReflections.createNBTBase(value);
	}
	
	/**
	 * 
	 * @param nbtbase instanceof NBTBase
	 * @return
	 */
	public static Object getValue(Object nbtbase) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.getValue(nbtbase);
		}
		return NBTUtilitiesReflections.getValue(nbtbase);
	}
	
	public static Object parse(String s) {
		switch (ReflectionsUtilities.getPackageVersionName()) {
			case "v1_14_R1":
				return NBTUtilities_v1_14.parse(s);
		}
		return NBTUtilitiesReflections.parse(s);
	}
}
