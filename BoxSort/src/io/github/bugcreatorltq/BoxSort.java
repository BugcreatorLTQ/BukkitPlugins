package io.github.bugcreatorltq;

import java.util.Comparator;
import java.util.LinkedList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BoxSort implements Listener {

	@EventHandler
	public void onCloseBox(InventoryCloseEvent e) {
		Inventory inventory = e.getInventory();
		ItemStack[] content = inventory.getContents();
		LinkedList<ItemStack> items = new LinkedList<ItemStack>();
		for (ItemStack item : content) {
			if (item == null) {
				continue;
			}
			items.add(item);
		}
		items.sort(new Comparator<ItemStack>() {
			@Override
			public int compare(ItemStack arg0, ItemStack arg1) {
				return arg0.getType().ordinal() - arg1.getType().ordinal();
			}
		});
		inventory.clear();
		for(ItemStack item : items) {
			inventory.addItem(item);
		}
	}

}
