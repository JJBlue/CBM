package essentials.utilities.inventory;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import essentials.utilities.inventory.runnables.RunnableInventoryClick;
import essentials.utilities.inventory.runnables.RunnableInventoryDrag;
import essentials.utilities.inventory.runnables.RunnableInventoryMove;

import java.util.List;
/**
 * 	Adding a InventoryItem to a normal Inventory, it will be converted from Bukkit to a normal ItemStack.
 * 	Will you add this to a normal Inventory. You must create at first a InventoryFactory with the normal Inventory
 */
public class InventoryItem extends ItemStack {
	protected RunnableInventoryClick onClick;
	protected RunnableInventoryDrag onDrag;
	protected RunnableInventoryMove onMove;
	
	public InventoryItem(ItemStack itemStack) {
		super(itemStack);
	}
	
	public InventoryItem(final Material type) {
        super(type, 1);
    }
	
    public InventoryItem(final Material type, final int amount) {
    	super(type, amount);
    }

    public void setDisplayName(String displayName) {
    	ItemMeta meta = this.getItemMeta();
    	if(meta == null) return;
    	meta.setDisplayName(displayName);
    	this.setItemMeta(meta);
    }
    
    public String getDisplayName() {
    	ItemMeta meta = this.getItemMeta();
		return meta.getDisplayName();
	}
    
    public void setLore(List<String> lore) {
    	ItemMeta meta = this.getItemMeta();
    	meta.setLore(lore);
    	this.setItemMeta(meta);
    }
    
	public RunnableInventoryClick getOnClick() {
		return onClick;
	}

	public void setOnClick(RunnableInventoryClick onClick) {
		this.onClick = onClick;
	}

	public void callOnClick(InventoryClickEvent event) {
		if(onClick != null)
			onClick.run(event, this);
	}
	
	public RunnableInventoryDrag getOnDrag() {
		return onDrag;
	}

	public void setOnDrag(RunnableInventoryDrag onDrag) {
		this.onDrag = onDrag;
	}
	
	public void callOnDrag(InventoryDragEvent event) {
		if(onDrag != null)
			onDrag.run(event, this);
	}

	public RunnableInventoryMove getOnMove() {
		return onMove;
	}

	public void setOnMove(RunnableInventoryMove onMove) {
		this.onMove = onMove;
	}
	
	public void callOnMove(InventoryMoveItemEvent event) {
		if(onMove != null)
			onMove.run(event, this);
	}
	
	public InventoryItem clone() {
		InventoryItem item = new InventoryItem(super.clone());
		item.setOnClick(onClick);
		item.setOnDrag(onDrag);
		item.setOnMove(onMove);
		return item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ItemStack)
			return super.equals(obj);
		return false;
	}
}
