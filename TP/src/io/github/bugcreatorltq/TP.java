package io.github.bugcreatorltq;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 传送
 * 
 * @author lain
 *
 */
public class TP implements Listener {

	private static HashMap<String, String> tpMap = new HashMap<String, String>();
	private static int boxSize = 4 * 9;
	private static Plugin plugin;
	public static String send = "tpa", accept = "tp_accept", ignore = "tp_ignore", ui = "传送法阵";
	
	public static void setPlugin(Plugin plugin) {
		TP.plugin = plugin;
	}
	
	/**
	 * 获取发送者玩家
	 * @param target 目标玩家
	 * @return
	 */
	private static Player getSourcePlayer(Player target) {
		if(target.isOnline()) {
			String src_name = tpMap.get(target.getName());
			if(src_name != null) {
				return Bukkit.getPlayer(src_name);
			}
		}
		return null;
	}
	
	/**
	 * source 传送到 target
	 * 
	 * @param source
	 * @param target
	 */
	public static void tp(Player source, Player target) {
		if (source.isOnline() && target.isOnline()) {
			// 检查请求人与接收人是否是一人
			if(source.equals(target)) {
				source.sendMessage("传自己传个锤子");
				return;
			}
			// 获取目标当前请求人
			Player src = getSourcePlayer(target);
			// 避免重复传送
			if (src == null || src != source) {
				// 发送提示信息
				target.sendMessage(source.getName() + "想要传送到你这里\n输入 /" + accept + " 接受\n输入 /" + ignore + " 拒绝");
				// 覆盖别人传送
				tpMap.put(target.getName(), source.getName());
				// 30s后取消
				new BukkitRunnable() {
					@Override
					public void run() {
						Player src = getSourcePlayer(target);
						if (src == null || src != source) {
							return;
						}
						source.sendMessage(target.getName() + "很长时间没有接受你的传送请求,已取消传送请求");
						target.sendMessage("你很长时间没有接受" + source.getName() + "的传送请求,已取消传送请求");
						tpMap.remove(target.getName());
					}
				}.runTaskLater(plugin, 30 * 20L);
			} else {
				source.sendMessage("你已向该玩家发出过传送请求");
			}
		}
	}

	/**
	 * 接收传送请求
	 * 
	 * @param flag
	 * @param target
	 */
	public static void accept(boolean flag, Player target) {
		// 获取传送请求者
		Player source = getSourcePlayer(target);
		// 删除请求
		tpMap.remove(target.getName());
		// 检查在线
		if (source.isOnline() && target.isOnline()) {
			if (flag) {
				source.sendMessage("正在传送...");
				// 传送
				source.teleport(target.getLocation());
				source.sendMessage("传送完成");
			} else {
				source.sendMessage("对方拒绝了你的请求");
			}
		}
	}

	/**
	 * 创建TP界面
	 */
	public static void tpUI(Player source) {
		Inventory tp_ui = Bukkit.createInventory(null, boxSize, ui);
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player player : players) {
			if (player.equals(source)) {
				continue;
			}
			ItemStack skull = new ItemStack(Material.EGG);
			ItemMeta im = skull.getItemMeta();
			im.setDisplayName(player.getName());
			skull.setItemMeta(im);
			tp_ui.addItem(skull);
		}
		source.closeInventory();
		source.openInventory(tp_ui);
	}

	// 添加容器监听
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getRawSlot()<0 || e.getRawSlot()>e.getInventory().getSize() || e.getInventory()==null)
	        return;
		if (e.getInventory().getSize() == boxSize) {
			// 禁止拿物品
			e.setCancelled(true);
			// 获取目标
			Player target = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
			// 请求传送
			tp((Player)e.getWhoClicked(), target);
		}
	}

}
