package essentials.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

import essentials.inventory.runnables.RunnableInventoryClick;
import essentials.inventory.runnables.RunnableInventoryDrag;
import essentials.inventory.runnables.RunnableInventoryMove;

public class InventoryItem {
	protected ItemStack itemStack;
	protected RunnableInventoryClick onClick;
	protected RunnableInventoryDrag onDrag;
	protected RunnableInventoryMove onMove;
	
	public InventoryItem(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
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
		InventoryItem item = new InventoryItem(itemStack.clone());
		item.setOnClick(onClick);
		item.setOnDrag(onDrag);
		item.setOnMove(onMove);
		return item;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof InventoryItem) {
			InventoryItem equalsItem = (InventoryItem) obj;
			
			if(super.equals(equalsItem)) return true;
			return itemStack.equals(equalsItem.getItemStack());
		}
		
		if(obj instanceof ItemStack) {
			return itemStack.equals(obj);
		}
		
		return false;
	}
}
