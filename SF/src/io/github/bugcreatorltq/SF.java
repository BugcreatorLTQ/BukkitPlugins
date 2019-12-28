package io.github.bugcreatorltq;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;

import com.mojang.datafixers.util.Pair;

public class SF {

	// save gift info
	private static HashMap<Player, Pair<Player, ItemStack>> sfTruck = new HashMap<Player, Pair<Player, ItemStack>>();

	private static String getItemInfo(ItemStack sendItem) {
		return " " + sendItem.getType() + " x " + sendItem.getAmount();
	}

	public static void send(Player srouce, Player target) {
		PlayerInventory inventory = (PlayerInventory) srouce.getInventory();
		ItemStack sendItem = inventory.getItemInMainHand();
		if (sendItem.getAmount()==0) {
			srouce.sendMessage("you must take what you want to send in your hand");
			return;
		}
		sfTruck.put(target, new Pair<Player, ItemStack>(srouce, sendItem.clone()));
		// delete item
		sendItem.setType(Material.AIR);
		target.sendMessage(srouce.getName() + " want give you" + getItemInfo(sendItem)
				+ "\nEnter /accept to accept \nEnter /ignore to ignore");
	}

	public static void accept(Player target, boolean flag) {
		// get Source-itemStack Pair
		Pair<Player, ItemStack> pair = sfTruck.get(target);
		if (pair == null) {
			return;
		}
		Player srouce = pair.getFirst();
		ItemStack sendItem = pair.getSecond();
		if (flag) {
			srouce.sendMessage("you give " + target.getName() + getItemInfo(sendItem));
			// send item
			target.getInventory().addItem(sendItem);
			target.sendMessage(srouce.getName() + " give you" + getItemInfo(sendItem));
		} else {
			srouce.sendMessage("you are ignored");
			srouce.getInventory().addItem(sendItem);
		}
		// remove info
		sfTruck.remove(target);
	}

}
