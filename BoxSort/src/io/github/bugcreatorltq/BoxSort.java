package io.github.bugcreatorltq;

import java.util.Comparator;
import java.util.LinkedList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BoxSort implements Listener {

	// 排序物品堆数组
	private LinkedList<ItemStack> sort(ItemStack[] content, int start, int end) {
		LinkedList<ItemStack> items = new LinkedList<ItemStack>();
		for (int i = start; i < end; i++) {
			if (content[i] != null) {
				items.add(content[i]);
			}
		}
		items.sort(new Comparator<ItemStack>() {

			@Override
			public int compare(ItemStack arg0, ItemStack arg1) {
				return arg1.getType().ordinal() - arg0.getType().ordinal();
			}
		});
		return items;
	}

	// 排序容器
	private void sort(Inventory inventory) {
		ItemStack[] content = inventory.getContents().clone();
		inventory.clear();
		for (ItemStack item : sort(content, 0, content.length)) {
			inventory.addItem(item);
		}
	}

	// 排序玩家背包
	private void sortPlayer(Inventory inventory) {

		ItemStack[] content = inventory.getContents().clone();
		inventory.clear();
		for (ItemStack item : sort(content, 0, 9)) {
			inventory.addItem(item);
		}
		int index = 9;
		for (ItemStack item : sort(content, 9, content.length)) {
			inventory.setItem(index++, item);
		}
	}

	@EventHandler
	public void onOpenBox(InventoryOpenEvent e) {
		Inventory inventory = e.getInventory();
		if (inventory.getType().equals(InventoryType.CHEST)) {
			sort(inventory);
			sortPlayer(e.getPlayer().getInventory());
		}
	}

}
