package io.github.bugcreatorltq;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;

import com.mojang.datafixers.util.Pair;

/**
 * SF Express
 * 
 * @author lain
 *
 */
public class SF {

	// save sendItem info
	private static HashMap<Player, Pair<Player, ItemStack>> sfTruck = new HashMap<Player, Pair<Player, ItemStack>>();

	// get sendItem info
	private static String getItemInfo(ItemStack sendItem) {
		return " " + sendItem.getType() + " x " + sendItem.getAmount();
	}

	/**
	 * send item to target form source
	 * 
	 * @param srouce
	 * @param target
	 */
	public static void send(Player source, Player target) {
		// check not same one
		if (source.equals(target)) {
			source.sendMessage("you can't send to youself!");
			return;
		}
		// get player inventory
		PlayerInventory inventory = (PlayerInventory) source.getInventory();
		// get item form player hand
		ItemStack sendItem = inventory.getItemInMainHand();
		// check item not null
		if (sendItem.getAmount() == 0) {
			source.sendMessage("you must take what you want to send in your hand");
			return;
		}
		// check target queue
		if (sfTruck.get(target) == null) {
			// save sendItem info
			sfTruck.put(target, new Pair<Player, ItemStack>(source, sendItem.clone()));
			return;
		}
		// delete item
		sendItem.setType(Material.AIR);
		// send message
		target.sendMessage(source.getName() + " want give you" + getItemInfo(sendItem)
				+ "\nEnter /accept to accept \nEnter /ignore to ignore");
	}

	public static void accept(Player target, boolean flag) {
		// get Source-itemStack Pair
		Pair<Player, ItemStack> pair = sfTruck.get(target);
		if (pair == null) {
			return;
		}
		Player source = pair.getFirst();
		// get sendItem
		ItemStack sendItem = pair.getSecond();
		if (flag) {
			// accept
			target.getInventory().addItem(sendItem);
			// send message
			source.sendMessage("you give " + target.getName() + getItemInfo(sendItem));
			target.sendMessage(source.getName() + " give you" + getItemInfo(sendItem));
		} else {
			// ignore
			source.sendMessage("you are ignored");
			// turn back sendItem
			source.getInventory().addItem(sendItem);
		}
		// remove info
		sfTruck.remove(target);
	}

}
