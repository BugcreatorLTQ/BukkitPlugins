package io.github.bugcreatorltq;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Vector3D {

	private int x, y, z;
	private static Location location = null;

	public static void setLocation(Location location) {
		Vector3D.location = location;
	};

	public Vector3D(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3D(Location location) {
		this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		Vector3D.setLocation(location);
	}

	public Vector3D() {
		this(0, 0, 0);
	}

	public Vector3D(Vector vec) {
		this(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
	}

	public Vector3D setX(int x) {
		this.x = x;
		return this;
	}

	public Vector3D setY(int y) {
		this.y = y;
		return this;
	}

	public Vector3D setZ(int z) {
		this.z = z;
		return this;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Vector3D clone() {
		return new Vector3D(x, y, z);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

	public void build(Material type, boolean flag) {
		try {
			location.setX(x);
			location.setY(y);
			location.setZ(z);
			location.getBlock().setType(flag ? type : Material.AIR);
		} catch (Exception e) {
			System.err.println(e.toString());
			new Exception("未初始化Location，检查是否已使用setLocation函数设置Location");
		}
	}

	public Vector3D add(int x, int y, int z) {
		Vector3D result = clone();
		result.x += x;
		result.y += y;
		result.z += z;
		return result;
	}

	public Vector3D add(Vector3D vector3d) {
		return add(vector3d.x, vector3d.y, vector3d.z);
	}

	public Vector3D subtract(int x, int y, int z) {
		Vector3D result = clone();
		result.x -= x;
		result.y -= y;
		result.z -= z;
		return result;
	}

	public Vector3D subtract(Vector3D vector3d) {
		return subtract(vector3d.x, vector3d.y, vector3d.z);
	}

	public Vector3D abs() {
		Vector3D result = clone();
		result.x = x > 0 ? x : -x;
		result.y = y > 0 ? y : -y;
		result.z = z > 0 ? z : -z;
		return result;
	}

	public Vector3D divide(Vector3D vector3d) {
		Vector3D result = clone();
		if (vector3d.x != 0)
			result.x /= vector3d.x;
		if (vector3d.y != 0)
			result.y /= vector3d.y;
		if (vector3d.z != 0)
			result.z /= vector3d.z;
		return result;
	}

	public Vector3D divide(int x, int y, int z) {
		return divide(new Vector3D(x, y, z));
	}

	public Vector3D divide(int num) {
		return divide(num, num, num);
	}

	public Vector3D multiply(Vector3D vector3d) {
		Vector3D result = clone();
		result.x *= vector3d.x;
		result.y *= vector3d.y;
		result.z *= vector3d.z;
		return result;
	}

	public Vector3D multiply(int x, int y, int z) {
		return multiply(new Vector3D(x, y, z));
	}

	public Vector3D multiply(int num) {
		return multiply(num, num, num);
	}

	public Vector3D max(int x, int y, int z) {
		Vector3D result = clone();
		result.x = result.x > x ? result.x : x;
		result.y = result.y > y ? result.y : y;
		result.z = result.z > z ? result.z : z;
		return result;
	}

	public Vector3D max(Vector3D vector3d) {
		return max(vector3d.x, vector3d.y, vector3d.z);
	}

	public Vector3D min(int x, int y, int z) {
		Vector3D result = clone();
		result.x = result.x < x ? result.x : x;
		result.y = result.y < y ? result.y : y;
		result.z = result.z < z ? result.z : z;
		return result;
	}

	public Vector3D min(Vector3D vector3d) {
		return min(vector3d.x, vector3d.y, vector3d.z);
	}

}
