package cbm.utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtilies {
	private ItemUtilies() {}

	public static Damageable getDamageAble(ItemStack itemStack) {
		if (itemStack == null) return null;
		if (itemStack.getItemMeta() instanceof Damageable)
			return (Damageable) itemStack.getItemMeta();
		return null;
	}

	public static int getMaxDurability(ItemStack itemStack) {
		if (itemStack == null) return 0;
		return itemStack.getType().getMaxDurability();
	}

	public static void setDurability(ItemStack itemStack, int value) {
		Damageable damageable = getDamageAble(itemStack);
		if (damageable == null) return;
		damageable.setDamage(value);
		itemStack.setItemMeta((ItemMeta) damageable);
	}
}
