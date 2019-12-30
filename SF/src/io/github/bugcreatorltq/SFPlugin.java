package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SFPlugin extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// 发送快递
		if (command.getName().equalsIgnoreCase("send")) {
			if (sender instanceof Player) {
				if (args.length != 1) {
					sender.sendMessage("参数错误!");
					return false;
				}
				Player target = Bukkit.getPlayer(args[0]);
				// 检查是否在线
				if(!target.isOnline()) {
					sender.sendMessage("玩家已离线");
					return true;
				}
				SF.send((Player) sender, target, this);
				return true;
			}
		}
		// 接收快递
		if (command.getName().equalsIgnoreCase(SF.yes)) {
			if (sender instanceof Player) {
				SF.accept((Player) sender, true);
				return true;
			}
		}
		// 拒绝快递
		if (command.getName().equalsIgnoreCase(SF.no)) {
			if (sender instanceof Player) {
				SF.accept((Player) sender, false);
				return true;
			}
		}
		return false;
	}

}
