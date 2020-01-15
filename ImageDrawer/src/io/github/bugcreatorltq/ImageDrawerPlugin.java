package io.github.bugcreatorltq;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ImageDrawerPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		ImageDrawer.init();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("draw")) {
			ImageDrawer.draw(sender, args[0]);
		}
		if (command.getName().equalsIgnoreCase("colors")) {
			ImageDrawer.drawColors(sender);
		}
		return true;
	}

}
