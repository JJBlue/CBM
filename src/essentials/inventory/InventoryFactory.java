package essentials.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import essentials.inventory.runnables.RunnableInventoryClick;
import essentials.inventory.runnables.RunnableInventoryClose;
import essentials.inventory.runnables.RunnableInventoryDrag;
import essentials.inventory.runnables.RunnableInventoryMove;
import essentials.inventory.runnables.RunnableInventoryOpen;

public class InventoryFactory {
	protected Inventory inventory;
	protected int currentPage = 0;
	protected boolean deleteOnExit = true;
	
	protected RunnableInventoryClose onClose;
	protected RunnableInventoryOpen onOpen;
	
	protected RunnableInventoryClick onClick;
	protected RunnableInventoryDrag onDrag;
	protected RunnableInventoryMove onMove;
	
	protected Map<Integer, InventoryPage> pages = Collections.synchronizedMap(new HashMap<>());
	
	public InventoryFactory(Inventory inventory) {
		this.inventory = inventory;
		InventoryManager.add(this);
	}
	
	public InventoryFactory(int size, String title) {
		inventory = Bukkit.createInventory(null, size, title);
		InventoryManager.add(this);
	}
	
	public InventoryPage createFirstPage() {
		InventoryPage page = new InventoryPage(inventory);
		addInventoryPage(page);
		return page;
	}
	
	public void addInventoryPage(InventoryPage page) {
		synchronized (pages) {
			for(int i = 0; i < Integer.MAX_VALUE; i++) {
				if(!pages.containsKey(i)) {
					pages.put(i, page);
					break;
				}
			}
		}
	}
	
	public void addInventoryPage(int pageNumber, InventoryPage page) {
		pages.put(pageNumber, page);
	}
	
	public void removeInventoryPage(InventoryPage page) {
		synchronized (pages) {
			int posDelete = -1;
			
			for(int pos : pages.keySet()) {
				if(page.equals(pages.get(pos))) {
					posDelete = pos;
					break;
				}
			}
			
			if(posDelete > -1)
				pages.remove(posDelete);
		}
	}
	
	public void setInventoryPage(InventoryPage page) {
		page.fill(inventory);
	}
	
	public InventoryPage getCurrentInventoryPage() {
		return pages.get(currentPage);
	}
	
	public void refreshPage() {
		InventoryPage page = getCurrentInventoryPage();
		if(page == null) return;
		
		page.fill(inventory);
	}
	
	public void cleanRefreshPage() {
		inventory.clear();
		refreshPage();
	}
	
	public void next() {
		if(pages.containsKey(currentPage + 1))
			currentPage++;
		else
			currentPage = 0;
		
		cleanRefreshPage();
	}
	
	public void previous() {
		if(pages.containsKey(currentPage - 1))
			currentPage--;
		else {
			while(pages.containsKey(currentPage + 1))
				currentPage++;
		}
		
		cleanRefreshPage();
	}
	
	public boolean isDeleteOnExit() {
		return deleteOnExit;
	}
	
	public void setDeleteOnExit() {
		deleteOnExit = true;
	}
	
	public void setDeleteOnExit(boolean value) {
		deleteOnExit = value;
	}
	
	public void delete() {
		setDeleteOnExit();
		close();
	}
	
	public void close() {
		for(HumanEntity entity : new LinkedList<>(inventory.getViewers()))
			entity.closeInventory();
	}
	
	public void setPage(int pageNumber) {
		currentPage = pageNumber;
		refreshPage();
	}
	
	public InventoryPage getPage(int pageNumber) {
		return pages.get(pageNumber);
	}
	
	public void openInventory(Player player) {
		refreshPage();
		player.openInventory(inventory);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public Map<Integer, InventoryPage> getPages() {
		return pages;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public RunnableInventoryClose getOnClose() {
		return onClose;
	}

	public void setOnClose(RunnableInventoryClose onClose) {
		this.onClose = onClose;
	}

	public RunnableInventoryOpen getOnOpen() {
		return onOpen;
	}

	public void setOnOpen(RunnableInventoryOpen onOpen) {
		this.onOpen = onOpen;
	}

	public RunnableInventoryClick getOnClick() {
		return onClick;
	}

	public void setOnClick(RunnableInventoryClick onClick) {
		this.onClick = onClick;
	}
	
	public void callOnClick(InventoryClickEvent event, InventoryItem item) {
		if(onClick != null)
			onClick.run(event, item);
	}

	public RunnableInventoryDrag getOnDrag() {
		return onDrag;
	}

	public void setOnDrag(RunnableInventoryDrag onDrag) {
		this.onDrag = onDrag;
	}
	
	public void callOnDrag(InventoryDragEvent event, InventoryItem item) {
		if(onDrag != null)
			onDrag.run(event, item);
	}

	public RunnableInventoryMove getOnMove() {
		return onMove;
	}

	public void setOnMove(RunnableInventoryMove onMove) {
		this.onMove = onMove;
	}
	
	public void callOnMove(InventoryMoveItemEvent event, InventoryItem item) {
		if(onMove != null)
			onMove.run(event, item);
	}
}
