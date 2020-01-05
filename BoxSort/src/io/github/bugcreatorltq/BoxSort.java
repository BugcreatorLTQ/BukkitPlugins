package io.github.bugcreatorltq;

import java.util.Comparator;
import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
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
				return arg0.getType().ordinal() - arg1.getType().ordinal();
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
	public void sortPlayer(Inventory inventory) {
		ItemStack[] content = inventory.getContents().clone();
		inventory.clear();
		// 背包
		for (ItemStack item : sort(content, 9, content.length - 5)) {
			inventory.addItem(item);
		}
		ItemStack[] temp = inventory.getContents().clone();
		inventory.clear();
		// 工具栏
		for (ItemStack item : sort(content, 0, 9)) {
			inventory.addItem(item);
		}
		// 背包栏
		for (int i = 0; i < 3 * 9; i++) {
			inventory.setItem(i + 9, temp[i]);
		}
		// 装备栏
		for (int i = content.length - 5; i < content.length; i++) {
			inventory.setItem(i, content[i]);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!e.getInventory().getType().equals(InventoryType.CHEST)) {
			return;
		}
		if (e.getClick() != ClickType.MIDDLE) {
			return;
		}
		Inventory inventory = e.getClickedInventory();
		if (inventory.getType().equals(InventoryType.CHEST)) {
			sort(inventory);
		} else if (inventory.getType().equals(InventoryType.PLAYER)) {
			sortPlayer(inventory);
		} else {
			return;
		}
		Player player = Bukkit.getPlayer(e.getWhoClicked().getName());
		player.closeInventory();
		player.openInventory(e.getInventory());
	}

}
