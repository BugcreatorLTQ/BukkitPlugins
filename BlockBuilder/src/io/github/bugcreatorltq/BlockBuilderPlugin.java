package io.github.bugcreatorltq;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockBuilderPlugin extends JavaPlugin {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		BlockBuilder builder = new BlockBuilder(sender);
		if (command.getName().equalsIgnoreCase("test")) {
			builder.new Test().build(true);
		}
		if (command.getName().equalsIgnoreCase("array")) {
			builder.new Array(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]))
					.build(args[3].equalsIgnoreCase("t"));
		}
		if (command.getName().equalsIgnoreCase("cup")) {
			builder.new Cup(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]))
					.build(args[3].equalsIgnoreCase("t"));
		}
		if (command.getName().equalsIgnoreCase("box")) {
			builder.new Box(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]))
					.build(args[3].equalsIgnoreCase("t"));
		}
		if (command.getName().equalsIgnoreCase("line")) {
			builder.new Line(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2]))
					.build(args[3].equalsIgnoreCase("t"));
		}
		if (command.getName().equalsIgnoreCase("flat")) {
			builder.new Flat(new Vector3D(Integer.valueOf(args[0]), Integer.valueOf(args[1]), Integer.valueOf(args[2])),
					new Vector3D(Integer.valueOf(args[3]), Integer.valueOf(args[4]), Integer.valueOf(args[5])))
							.build(args[6].equalsIgnoreCase("t"));
		}
		return false;
	}
}
