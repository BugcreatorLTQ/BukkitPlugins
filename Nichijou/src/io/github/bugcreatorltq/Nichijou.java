package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

/**
 * 日常类 (name from Animation <<Nichijou>>) 用于处理
 * 
 * @author lain
 *
 */
public class Nichijou implements Listener {

	/**
	 * 向所有玩家发送信息
	 * 
	 * @param message
	 */
	public void sendMessageToEveryone(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	/**
	 * 玩家上床时通知其他玩家
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerEnterBad(PlayerBedEnterEvent e) {
		// 获取上床的玩家
		Player player = e.getPlayer();
		// 获取当前时间
		long time = player.getWorld().getTime();
		// 判断是否是夜晚
		if (time < 13000) {
			return;
		}
		sendMessageToEveryone(player.getName() + "喊你睡觉啦~");
	}

	/**
	 * 测试
	 * @param player
	 */
	public static void test(Player player) {
	}
}
