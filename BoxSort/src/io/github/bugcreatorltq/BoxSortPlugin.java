package io.github.bugcreatorltq;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BoxSortPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new BoxSort(), this);
	}
}
