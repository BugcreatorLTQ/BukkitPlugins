package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("send")) {
			if (sender instanceof Player) {
				if (args.length != 1) {
					sender.sendMessage("arguments error!");
					return false;
				}
				Player target = Bukkit.getPlayer(args[0]);
				SF.send((Player) sender, target);
				return true;
			}
		}
		if (command.getName().equalsIgnoreCase("accept")) {
			if (sender instanceof Player) {
				SF.accept((Player) sender, true);
				return true;
			}
		}
		if (command.getName().equalsIgnoreCase("ignore")) {
			if (sender instanceof Player) {
				SF.accept((Player) sender, false);
				return true;
			}
		}
		return false;
	}

}
