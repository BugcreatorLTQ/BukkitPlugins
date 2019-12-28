package happecoder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Builder {

	private Location loc;
	// Block Type
	private Material type;
	// sender
	CommandSender sender;

	public Builder(CommandSender sender) {
		if (sender instanceof Player) {
			this.sender = sender;
			loc = ((Player) sender).getLocation();
			type = Material.STONE;
		} else {
			new Exception("src not player");
		}
	}

	public void setBlock(Location location, Material type, boolean flag) {
		location.getBlock().setType(flag ? type : Material.AIR);
	}

	public void setType(Material type) {
		this.type = type;
	}

	public Builder add(int x, int y, int z) {
		return add(new Vector(x, y, z));
	}

	public Builder add(Vector v) {
		Builder builder = new Builder(sender);
		builder.loc = loc.add(v);
		return builder;
	}

	protected abstract class Basic {

		public void build(boolean flag) {
			Location temp = loc.clone();
			building(flag);
			loc = temp.clone();
		}

		protected abstract void building(boolean flag);

	}

	/**
	 * build one array
	 * 
	 * @author lain
	 *
	 */
	public class Array extends Basic {

		protected Vector size;
		protected Vector step;

		public Array(Vector size) {
			this.size = size.clone();
			step = size.clone();
			step.divide(Vector.getMaximum(size, size.clone().multiply(-1)));
		}

		public Array(int x, int y, int z) {
			this(new Vector(x, y, z));
		}

		@Override
		protected void building(boolean flag) {
			sender.sendMessage("step is " + step.toString());
			for (int i = 0; i != size.getBlockX(); i += step.getBlockX()) {
				for (int j = 0; j != size.getBlockY(); j += step.getBlockY()) {
					for (int k = 0; k != size.getBlockZ(); k += step.getBlockZ()) {
						setBlock(loc.clone().add(i, j, k), type, flag);
					}
				}
			}
		}
	}

	/**
	 * Test Class
	 * 
	 * @author lain
	 *
	 */
	public class Test extends Basic {

		@Override
		protected void building(boolean flag) {
			if (flag) {
				new Array(2, 3, 4).build(flag);
				return;
			}
		}
	}
}
