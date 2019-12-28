package happecoder;

	import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onLoad() {
		super.onLoad();
		getLogger().info("i'm loading");
	}

	@Override
	public void onEnable() {
		super.onEnable();
		getLogger().info("i'm ok");
	}

	@Override
	public void onDisable() {
		super.onDisable();
		getLogger().info("i'm left");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Builder builder = new Builder(sender);
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
		return false;
	}
}
