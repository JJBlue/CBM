package essentials.utilities;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import components.json.JSONObject;
import essentials.utilities.minecraft.MinecraftVersions;
import essentials.utilitiesvr.nbt.NBTTag;
import essentials.utilitiesvr.nbt.NBTUtilitiesReflections;
import essentials.utilitiesvr.nbt.NBTUtilities_v1_14;
import essentials.utilitiesvr.nbt.NBTUtilities_v1_15;

public class NBTUtilities {
	private NBTUtilities() {}

	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.getNBTTagCompound(itemstack);
			case v1_15_R1:
				return NBTUtilities_v1_15.getNBTTagCompound(itemstack);
		}
		
		return NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	public static NBTTag getNBTTag(ItemStack itemstack) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return new NBTUtilities_v1_14(itemstack);
			case v1_15_R1:
				return new NBTUtilities_v1_15(itemstack);
		}
		
		return new NBTUtilitiesReflections(itemstack);
	}

	/**
	 * @param ItemStack      itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				NBTUtilities_v1_14.setNBTTagCompound(itemstack, nbtTagCompound);
				return;
			case v1_15_R1:
				NBTUtilities_v1_15.setNBTTagCompound(itemstack, nbtTagCompound);
				return;
		}
		NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	/**
	 * NBTTagCompound
	 */
	public static Object createNBTTagCompound() {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.createNBTTagCompound();
			case v1_15_R1:
				return NBTUtilities_v1_15.createNBTTagCompound();
		}
		return NBTUtilitiesReflections.createNBTTagCompound();
	}
	
	public static NBTTag createNBTTag() {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.createNBTTag();
			case v1_15_R1:
				return NBTUtilities_v1_15.createNBTTag();
		}
		return NBTUtilitiesReflections.createNBTTag();
	}

	/**
	 * @return NBTTagList
	 */
	public static List<?> createNBTTagList() {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.createNBTTagList();
			case v1_15_R1:
				return NBTUtilities_v1_15.createNBTTagList();
		}
		return NBTUtilitiesReflections.createNBTTagList();
	}
	
	public static Object createNBTBase(Object value) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.createNBTBase(value);
			case v1_15_R1:
				return NBTUtilities_v1_15.createNBTBase(value);
		}
		return NBTUtilitiesReflections.createNBTBase(value);
	}
	
	/**
	 * 
	 * @param nbtbase instanceof NBTBase
	 * @return
	 */
	public static Object getValue(Object nbtbase) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.getValue(nbtbase);
			case v1_15_R1:
				return NBTUtilities_v1_15.getValue(nbtbase);
		}
		return NBTUtilitiesReflections.getValue(nbtbase);
	}
	
	public static Object parse(String s) {
		switch (MinecraftVersions.getMinecraftVersionExact()) {
			case v1_14_R1:
				return NBTUtilities_v1_14.parse(s);
			case v1_15_R1:
				return NBTUtilities_v1_15.parse(s);
		}
		return NBTUtilitiesReflections.parse(s);
	}
	
	public static String nbtToJson(NBTTag nbt) {
		return nbtToJsonObject(nbt).toJSONString();
	}
	
	public static JSONObject nbtToJsonObject(NBTTag nbt) {
		JSONObject json = new JSONObject();
		
		for(String key : nbt.getKeys()) {
			Object value = nbt.getValue(key);
			
			if(value instanceof NBTTag) {
				JSONObject obj = nbtToJsonObject(nbt);
				json.add(key, obj);
			} else {
				json.add(key, value);
			}
		}
		
		return json;
	}
}
