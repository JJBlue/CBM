package cbm.utilitiesvr.nbt;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import cbm.versions.VersionDependency;
import cbm.versions.minecraft.MinecraftVersions;
import components.json.JSONObject;

public class NBTUtilities {
	public final static VersionDependency<NBTUtilities_Interface> version_dependency = new VersionDependency<>();
	
	private NBTUtilities() {}

	/**
	 * @param itemstack
	 * @return NBTTagCompound
	 */
	public static Object getNBTTagCompound(ItemStack itemstack) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.getNBTTagCompound(itemstack);
		return NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	public static NBTTag getNBTTag(ItemStack itemstack) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());

		if(vd != null)
			return vd.getNBTTag(itemstack);
		return new NBTUtilitiesReflections(itemstack);
	}

	/**
	 * @param ItemStack      itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			vd.setNBTTagCompound(itemstack, nbtTagCompound);
		else
			NBTUtilitiesReflections.getNBTTagCompound(itemstack);
	}

	/**
	 * NBTTagCompound
	 */
	public static Object createNBTTagCompound() {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.createNBTTagCompound();
		return NBTUtilitiesReflections.createNBTTagCompound();
	}
	
	public static NBTTag createNBTTag() {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.createNBTTag();
		return NBTUtilitiesReflections.createNBTTag();
	}

	/**
	 * @return NBTTagList
	 */
	public static List<?> createNBTTagList() {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.createNBTTagList();
		return NBTUtilitiesReflections.createNBTTagList();
	}
	
	public static Object createNBTBase(Object value) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.createNBTBase(value);
		return NBTUtilitiesReflections.createNBTBase(value);
	}
	
	/**
	 * 
	 * @param nbtbase instanceof NBTBase
	 * @return
	 */
	public static Object getValue(Object nbtbase) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.getValue(nbtbase);
		return NBTUtilitiesReflections.getValue(nbtbase);
	}
	
	public static Object parse(String s) {
		NBTUtilities_Interface vd = version_dependency.get(MinecraftVersions.getMinecraftVersionExact());
		
		if(vd != null)
			return vd.parse(s);
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
