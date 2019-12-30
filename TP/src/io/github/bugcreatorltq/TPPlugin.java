package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TPPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new TP(),this);
		TP.setPlugin(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase(TP.send)) {
			if (sender instanceof Player) {
				if (args.length == 0) {
					TP.tpUI((Player) sender);
				} else if (args.length == 1) {
					Player target = Bukkit.getPlayer(args[0]);
					if (target.isOnline()) {
						TP.tp((Player) sender, target);
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
