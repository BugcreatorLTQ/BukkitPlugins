package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TPPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new TP(), this);
		TP.setPlugin(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(TP.tpa)) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					TP.tpUI((Player) sender, true);
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					// 检查玩家合法性
					if (target instanceof Player == false) {
						sender.sendMessage("你只能发送给在线玩家");
						return false;
					}
					if (target.isOnline()) {
						TP.tp((Player) sender, target, true);
					}
					return true;
				}
			}
		}
		if (command.getName().equalsIgnoreCase(TP.tpahere)) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					TP.tpUI((Player) sender, false);
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target instanceof Player == false) {
						sender.sendMessage("你只能发送给在线玩家");
						return false;
					}
					if (target.isOnline()) {
						TP.tp((Player) sender, target, false);
					}
					return true;
				}
			}
		}
		if (command.getName().equalsIgnoreCase(TP.accept)) {
			if (sender instanceof Player) {
				TP.accept(true, (Player) sender);
				return true;
			}
		}
		if (command.getName().equalsIgnoreCase(TP.ignore)) {
			if (sender instanceof Player) {
				TP.accept(false, (Player) sender);
				return true;
			}
		}
		return false;
	}

}
