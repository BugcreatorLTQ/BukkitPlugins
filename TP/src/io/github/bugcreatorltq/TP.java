package io.github.bugcreatorltq;

import java.util.Collection;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.mojang.datafixers.util.Pair;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * 传送
 * 
 * @author lain
 *
 */
public class TP implements Listener {

	private static HashMap<String, Pair<String, Boolean>> tpMap = new HashMap<String, Pair<String, Boolean>>();
	private static int boxSize = 4 * 9;
	private static Plugin plugin;
	public static String tpa = "tpa", tpahere = "tpahere", accept = "tpaccept", ignore = "tpaignore", ui = "传送法阵";

	public static void setPlugin(Plugin plugin) {
		TP.plugin = plugin;
	}

	/**
	 * 获取发送者玩家
	 * 
	 * @param target 目标玩家
	 * @return
	 */
	private static Player getSourcePlayer(Player target) {
		if (target.isOnline()) {
			Pair<String, Boolean> pair = tpMap.get(target.getName());
			if (pair != null) {
				return Bukkit.getPlayer(pair.getFirst());
			}
		}
		return null;
	}

	private static Boolean getDire(Player target) {
		if (target.isOnline()) {
			Pair<String, Boolean> pair = tpMap.get(target.getName());
			if (pair != null) {
				return pair.getSecond();
			}
		}
		return null;
	}

	// 发送信息
	private static void sendMessage(Player target) {
		TextComponent _accept = new TextComponent("点我接受\n");
		_accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/" + accept));
		_accept.setColor(ChatColor.GREEN);
		TextComponent _ignore = new TextComponent("点我拒绝");
		_ignore.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/" + ignore));
		_ignore.setColor(ChatColor.RED);
		_accept.addExtra(_ignore);
		target.spigot().sendMessage(_accept);
	}

	/**
	 * source 传送到 target
	 * 
	 * @param source
	 * @param target
	 */
	public static void tp(Player source, Player target, boolean dire) {
		if (source.isOnline() && target.isOnline()) {
			// 检查请求人与接收人是否是一人
			if (source.equals(target)) {
				source.sendMessage("传自己很好玩?");
				return;
			}
			// 获取目标当前请求人
			Player src = getSourcePlayer(target);
			// 避免重复传送
			if (src == null || src != source) {
				// 发送提示信息
				source.sendMessage("请求已发送");
				target.sendMessage(source.getName() + (dire ? "想要传送到你这里" : "邀请你传送到他那里"));
				sendMessage(target);
				// 覆盖别人传送
				tpMap.put(target.getName(), new Pair<String, Boolean>(source.getName(), dire));
				// 一段时间未接受后取消
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
						cancel();
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
		if (tpMap.get(target.getName()) == null) {
			target.sendMessage("没有需要处理的信息");
			return;
		}
		// 获取传送请求者
		Player source = getSourcePlayer(target);
		// 获取方向
		boolean dire = getDire(target);
		// 删除请求
		tpMap.remove(target.getName());
		// 检查在线
		if (source.isOnline() && target.isOnline()) {
			if (flag) {
				target.sendMessage("你同意了" + source.getName() + "的请求");
				source.sendMessage(target.getName() + "同意了你的请求");
				source.sendMessage("正在传送...");
				// 传送
				if (dire) {
					source.teleport(target.getLocation());
				} else {
					target.teleport(source.getLocation());
				}
				source.sendMessage("传送完成");
			} else {
				target.sendMessage("你拒绝了" + source.getName() + "的请求");
				source.sendMessage(target.getName() + "拒绝了你的请求");
			}
		}
	}

	/**
	 * 创建TP界面
	 */
	public static void tpUI(Player source, boolean dire) {
		Inventory tp_ui = Bukkit.createInventory(null, boxSize, ui);
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player player : players) {
			if (player.equals(source)) {
				continue;
			}
			ItemStack skull = new ItemStack(dire ? Material.EGG : Material.BONE);
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
		if (e.getRawSlot() < 0 || e.getRawSlot() > e.getInventory().getSize() || e.getInventory() == null)
			return;
		Material type = e.getCurrentItem().getType();
		if (type.equals(Material.EGG) || type.equals(Material.BONE)) {
			boolean dire = type == Material.EGG;
			if (e.getInventory().getSize() == boxSize) {
				// 禁止拿物品
				e.setCancelled(true);
				// 获取目标
				Player target = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName());
				// 请求传送
				tp((Player) e.getWhoClicked(), target, dire);
			}
		}
	}

	// 清除玩家请求
	private void clear(Player player) {
		// 清除目标为自己的请求
		if(tpMap.get(player.getName())!=null) {
			getSourcePlayer(player).sendMessage(player.getName()+"已下线 取消你对该玩家的传送请求");
			tpMap.remove(player.getName());
		}
		// 清除请求人为自己的请求
		for (Player target : Bukkit.getOnlinePlayers()) {
			Player source = getSourcePlayer(target);
			if(source != null && source==player) {
				target.sendMessage(player.getName()+"已下线 取消该玩家对你的传送请求");
				tpMap.remove(target.getName());
			}
		}
	}
	
	// 添加玩家上线监听
	@EventHandler
	public void Player(PlayerJoinEvent player) {
		clear(player.getPlayer());
	}
	
	// 添加玩家下线监听
	@EventHandler
	public void onPlayerExit(PlayerQuitEvent player) {
		clear(player.getPlayer());
	}

}
