package essentials.utilitiesvr.nbt;

import java.util.Set;

import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;

public class NBTUtilities_v1_14 implements NBTTag {
	public static NBTTagCompound getNBTTagCompound(ItemStack itemstack) {
		return CraftItemStack.asNMSCopy(itemstack).getTag();
	}
	
	/**
	 * 
	 * @param itemstack
	 * @param NBTTagCompound nbtTagCompound
	 */
	public static void setNBTTagCompound(ItemStack itemstack, Object nbtTagCompound) {
		if(!(nbtTagCompound instanceof NBTTagCompound)) return;
		CraftItemStack.asNMSCopy(itemstack).setTag((NBTTagCompound) nbtTagCompound);
	}
	
	public static NBTTagCompound createNBTTagCompound() {
		return new NBTTagCompound();
	}
	
	public static NBTTagList createNBTTagList() {
		return new NBTTagList();
	}
	
	private NBTTagCompound nbtTagCompound;
	
	public NBTUtilities_v1_14(ItemStack itemStack) {
		nbtTagCompound = getNBTTagCompound(itemStack);
	}
	
	@Override
	public void set(String key, Object value) {
		if(!(value instanceof NBTBase)) return;
		nbtTagCompound.set(key, (NBTBase) value);
	}

	@Override
	public boolean getBoolean(String key) {
		return nbtTagCompound.getBoolean(key);
	}
	
	@Override
	public void setBoolean(String key, boolean value) {
		nbtTagCompound.setBoolean(key, value);
	}

	@Override
	public byte getByte(String key) {
		return nbtTagCompound.getByte(key);
	}
	
	@Override
	public void setByte(String key, byte value) {
		nbtTagCompound.setByte(key, value);
	}

	@Override
	public NBTTagCompound getCompound(String key) {
		return nbtTagCompound.getCompound(key);
	}

	@Override
	public double getDouble(String key) {
		return nbtTagCompound.getDouble(key);
	}
	
	@Override
	public void setDouble(String key, double value) {
		nbtTagCompound.setDouble(key, value);
	}

	@Override
	public float getFloat(String key) {
		return nbtTagCompound.getFloat(key);
	}
	
	@Override
	public void setFloat(String key, float value) {
		nbtTagCompound.setFloat(key, value);
	}

	@Override
	public int getInt(String key) {
		return nbtTagCompound.getInt(key);
	}
	
	@Override
	public void setInt(String key, int value) {
		nbtTagCompound.setInt(key, value);
	}

	@Override
	public Set<String> getKeys() {
		return nbtTagCompound.getKeys();
	}

	@Override
	public NBTTagList getList(String key, int number) {
		return nbtTagCompound.getList(key, number);
	}

	@Override
	public long getLong(String key) {
		return nbtTagCompound.getLong(key);
	}
	
	@Override
	public void setLong(String key, long value) {
		nbtTagCompound.setLong(key, value);
	}

	@Override
	public short getShort(String key) {
		return nbtTagCompound.getShort(key);
	}
	
	@Override
	public void setShort(String key, short value) {
		nbtTagCompound.setShort(key, value);
	}

	@Override
	public String getString(String key) {
		return nbtTagCompound.getString(key);
	}
	
	@Override
	public void setString(String key, String value) {
		nbtTagCompound.setString(key, value);
	}

	@Override
	public byte getTypeID(String key) {
		return nbtTagCompound.getTypeId();
	}

	@Override
	public byte[] getByteArray(String key) {
		return nbtTagCompound.getByteArray(key);
	}
	
	@Override
	public void setByteArray(String key, byte[] value) {
		nbtTagCompound.setByteArray(key, value);
	}

	@Override
	public int[] getIntArray(String key) {
		return nbtTagCompound.getIntArray(key);
	}
	
	@Override
	public void setIntArray(String key, int[] value) {
		nbtTagCompound.setIntArray(key, value);
	}

	@Override
	public long[] getLongArray(String key) {
		return nbtTagCompound.getLongArray(key);
	}

	@Override
	public void setToItemStack(ItemStack itemStack) {
		setNBTTagCompound(itemStack, nbtTagCompound);
	}

	@Override
	public NBTTagCompound getNBTTagCompound() {
		return nbtTagCompound;
	}
	
//	public void test() {
//		Player p = (Player) s;
//		Location loc = p.getTargetBlock((HashSet<Byte>) null, 0).getLocation();
//		Location loc2 = p.getLocation();
//		Block b = p.getWorld().getBlockAt(loc);
//		
//		Block b2 = p.getWorld().getBlockAt(p.getLocation());
//		b2.setType(b.getType());
//		
//		CraftWorld cw = (CraftWorld) p.getWorld();
//		TileEntity tileEntity = cw.getTileEntityAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
//		TileEntity tileEntity2 = cw.getTileEntityAt(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
//		
//		NBTTagCompound ntc = new NBTTagCompound();
//		NBTTagCompound ntc2 = new NBTTagCompound();
//		tileEntity.b(ntc);
//		ntc2 = (NBTTagCompound) ntc.clone();
//		ntc2.setInt("x", loc2.getBlockX());
//		ntc2.setInt("y", loc2.getBlockY());
//		ntc2.setInt("z", loc2.getBlockZ());
//		tileEntity2.a(ntc2);
//		tileEntity2.update();
//	}
}
