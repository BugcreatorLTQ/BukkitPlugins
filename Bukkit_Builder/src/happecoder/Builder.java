package happecoder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Builder {

	private Location builderLocation;
	// Block Type
	private Material type;
	// sender
	CommandSender sender;

	public Builder(CommandSender sender) {
		if (sender instanceof Player) {
			this.sender = sender;
			builderLocation = ((Player) sender).getLocation();
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

	protected abstract class Basic {

		public void build(boolean flag) {
			Location temp = builderLocation.clone();
			building(flag);
			builderLocation = temp.clone();
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
						setBlock(builderLocation.clone().add(i, j, k), type, flag);
					}
				}
			}
		}
	}

	public class Cup extends Basic {

		protected Vector size, step;

		public Cup(int x, int y, int z) {
			this(new Vector(x, y, z));
		}

		public Cup(Vector size) {
			this.size = size.clone();
			step = size.clone().divide(Vector.getMaximum(size, size.clone().multiply(-1)));
		}

		@Override
		protected void building(boolean flag) {
			new Array(size.clone().setY(step.getBlockY())).build(flag);
			new Array(size.clone().setX(step.getBlockX())).build(flag);
			new Array(size.clone().setZ(step.getBlockZ())).build(flag);
			builderLocation.add(size.clone().setY(0));
			new Array(size.clone().setX(step.getBlockX())).build(flag);
			new Array(size.clone().setZ(step.getBlockZ())).build(flag);
		}

	}

	public class Box extends Basic {

		protected Vector size, step;

		public Box(int x, int y, int z) {
			this(new Vector(x, y, z));
		}

		public Box(Vector size) {
			this.size = size.clone();
			step = size.clone().divide(Vector.getMaximum(size, size.clone().multiply(-1)));
		}

		@Override
		protected void building(boolean flag) {
			new Cup(size).build(flag);
			builderLocation.add(0, size.getBlockY(), 0);
			new Array(size.clone().setY(-step.getBlockY())).build(flag);
		}

	}

	public class Line extends Basic {

		@Override
		protected void building(boolean flag) {

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
