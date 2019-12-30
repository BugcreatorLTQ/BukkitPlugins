package io.github.bugcreatorltq;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.datafixers.util.Pair;

/**
 * 顺丰快递
 * 
 * @author lain
 *
 */
public class SF {

	public static String send = "sf_send", accept = "sf_accept", ignore = "sf_ignore";

	// 用于储存快递信息
	private static HashMap<String, Pair<String, ItemStack>> sfTruck = new HashMap<String, Pair<String, ItemStack>>();

	// 获取物品信息
	private static String getItemInfo(ItemStack sendItem) {
		return " " + sendItem.getType().name() + " x " + sendItem.getAmount();
	}

	/**
	 * source 发给 target
	 * 
	 * @param srouce
	 * @param target
	 */
	public static void send(Player source, Player target, SFPlugin plugin) {
		// 检查接受者是否在线
		if (!target.isOnline()) {
			source.sendMessage(target.getName() + "未在线");
		}
		// 检查发送者和接受者是否是一个人
		if (source.equals(target)) {
			source.sendMessage("你给自己送个锤子");
			return;
		}
		// 获取玩家物品栏
		PlayerInventory inventory = (PlayerInventory) source.getInventory();
		// 获取手上物品
		ItemStack sendItem = inventory.getItemInOffHand();
		// 检查手上是否有东西
		if (sendItem.getAmount() == 0) {
			source.sendMessage("你必须把你想发送的东西放到你的副手上!");
			return;
		}
		// 检查接收对象是否有未签收的快递
		if (sfTruck.get(target.getName()) == null) {
			// 储存快递信息
			sfTruck.put(target.getName(), new Pair<String, ItemStack>(source.getName(), sendItem.clone()));
			// 30s后没有领取则取消快递
			new BukkitRunnable() {
				@Override
				public void run() {
					Pair<String, ItemStack> pair = sfTruck.get(target.getName());
					// 检查是否已经签收
					if (pair == null) {
						return;
					}
					// 检查是否是同一批货物
					if (pair.getFirst().equals(source.getName()) && pair.getSecond().equals(sendItem)) {
						// 检查发送玩家在线
						if (source.isOnline()) {
							// 发送提示信息
							source.sendMessage(target.getName() + "长时间未接收,已取消快递派送");
						}
						// 检查接收玩家在线
						if (target.isOnline()) {
							// 发送提示信息
							target.sendMessage("你长时间未接收" + source.getName() + "的快递,已取消派送");
						}
						// 取消派送
						sfTruck.remove(target.getName());
					}
				}
			}.runTaskLater(plugin, 30 * 20L);
		} else {
			// 发送提示信息
			source.sendMessage(target.getName() + "有一个未签收的快递,在他签收之前你不能给他送东西");
			target.sendMessage("你有一个未签收的快递,请尽快领取\n输入/" + accept + "接收\n输入/" + ignore + "拒绝");
			return;
		}
		// 发送提示信息
		target.sendMessage(
				source.getName() + " 想给你" + getItemInfo(sendItem) + "\n输入 /" + accept + " 接受 \n输入 /" + ignore + " 拒绝");
	}

	public static void accept(Player target, boolean flag) {
		// 获取信息
		Pair<String, ItemStack> pair = sfTruck.get(target.getName());
		// 删除快递信息
		sfTruck.remove(target.getName());
		if (pair == null) {
			return;
		}
		Player source = Bukkit.getPlayer(pair.getFirst());
		//
		ItemStack sendItem = pair.getSecond();
		// 检查发送者是否离线
		if (!source.isOnline()) {
			target.sendMessage("玩家" + source.getName() + "已离线");
			return;
		}
		// 判断接收/拒绝
		if (flag) {
			// 检查发送者手上物品有没有变化
			if (!source.getInventory().getItemInOffHand().equals(sendItem)) {
				source.sendMessage(target.getName() + "打算接收你的快递,但是你手上的物品发生了变化");
				target.sendMessage(source.getName() + "发送的物品发送了变化,取消发送");
				return;
			}
			// 删除发送者手上物块
			source.getInventory().getItemInOffHand().setType(Material.AIR);
			// 物块发给接收者
			target.getInventory().addItem(sendItem);
			// 发送提示信息
			source.sendMessage(target.getName() + "签收了你的快递");
			target.sendMessage("你签收了" + source.getName() + "的快递");

		} else {
			// 拒绝
			source.sendMessage(target.getName() + "拒绝了你的快递");
		}

	}

}
