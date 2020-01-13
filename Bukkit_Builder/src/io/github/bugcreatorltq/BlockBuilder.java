package io.github.bugcreatorltq;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockBuilder {

	private Vector3D builder;
	// Block Type
	private Material type;
	// sender
	CommandSender sender;

	public BlockBuilder(CommandSender sender) {
		if (sender instanceof Player) {
			this.sender = sender;
			builder = new Vector3D(((Player) sender).getLocation());
			type = Material.STONE;
		} else {
			new Exception("只有玩家才能使用这个命令");
		}
	}

	public void setType(Material type) {
		this.type = type;
	}

	protected abstract class Basic {

		public void build(boolean flag) {
			Vector3D temp = builder;
			building(flag);
			builder = temp;
		}

		public abstract void building(boolean flag);

	}

	public class Array extends Basic {

		private Vector3D size;

		public Array(Vector3D size) {
			this.size = size;
		}

		public Array(int x, int y, int z) {
			this(new Vector3D(x, y, z));
		}

		@Override
		public void building(boolean flag) {
			// fix builder location
			builder = builder.subtract(size.abs().subtract(size).divide(2));
			// make size to absolute
			size = size.abs();
			// building
			for (int i = 0; i < size.getX(); i++) {
				for (int j = 0; j < size.getY(); j++) {
					for (int k = 0; k < size.getZ(); k++) {
						builder.add(i, j, k).build(type, flag);
					}
				}
			}

		}
	}

	public class Cup extends Basic {

		private Vector3D size;

		public Cup(Vector3D size) {
			this.size = size;
		}

		public Cup(int x, int y, int z) {
			this(new Vector3D(x, y, z));
		}

		@Override
		public void building(boolean flag) {
			Vector3D step = size.divide(size.abs());
			new Array(size.clone().setY(step.getY())).build(flag);
			new Array(size.clone().setX(step.getX())).build(flag);
			new Array(size.clone().setZ(step.getZ())).build(flag);
			builder = builder.add(size.clone().setY(0));
			new Array(-step.getX(), size.getY(), -size.getZ()).build(flag);
			new Array(-size.getX(), size.getY(), -step.getZ()).build(flag);
		}

	}
}
