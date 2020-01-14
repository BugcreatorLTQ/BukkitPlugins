package io.github.bugcreatorltq;

import java.util.Random;

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
			type = ((Player) sender).getInventory().getItemInOffHand().getType();
			type = type.isBlock() ? type : Material.STONE;
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

	public class Box extends Basic {

		Vector3D size;

		public Box(Vector3D size) {
			this.size = size;
		}

		public Box(int x, int y, int z) {
			this(new Vector3D(x, y, z));
		}

		@Override
		public void building(boolean flag) {
			new Cup(size).build(flag);
			builder = builder.add(0, size.getY(), 0);
			new Array(size.setY(-size.divide(size.abs()).getY())).build(flag);
		}
	}

	public class Line extends Basic {

		Vector3D target;

		public Line(Vector3D target) {
			this.target = target;
		}

		public Line(int x, int y, int z) {
			this(new Vector3D(x, y, z));
		}

		@Override
		public void building(boolean flag) {
			double x = target.getX(), y = target.getY(), z = target.getZ();
			int max = Math.abs(target.max()) > Math.abs(target.min()) ? Math.abs(target.max()) : Math.abs(target.min());
			x /= max;
			y /= max;
			z /= max;
			for (int i = 0; i < max; i++) {
				builder.add((int) (x * i), (int) (y * i), (int) (z * i)).build(type, flag);
			}
		}

	}

	public class Flat extends Basic {

		Vector3D A, B;

		public Flat(Vector3D A, Vector3D B) {
			this.A = A;
			this.B = B;
		}

		@Override
		public void building(boolean flag) {
			new Line(A).build(flag);
			new Line(B).build(flag);
			builder = builder.add(A).subtract(A.divide(A.abs()));
			new Line(B.subtract(A)).build(flag);
		}

	}

	public class Test extends Basic {

		@Override
		public void building(boolean flag) {
			Random random = new Random();
			int size = 10;
			for (int i = 0; i < size; i++) {
				Vector3D rand = new Vector3D(random.nextInt(100) - 50, random.nextInt(40) - 20,
						random.nextInt(100) - 50);
				new Line(rand).build(flag);
				builder = builder.add(rand);
			}
		}

	}
}
