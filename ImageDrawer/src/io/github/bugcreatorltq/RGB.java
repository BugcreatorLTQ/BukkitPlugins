package io.github.bugcreatorltq;

public class RGB {

	public enum Type {
		Limit, Gry, Dark
	}

	private int a;
	protected int r;
	protected int g;
	protected int b;

	RGB(int a, int r, int g, int b) {
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	RGB(int num) {
		a = num >> 8 * 3;
		r = num >> 8 * 2;
		g = num >> 8 * 1;
		b = num >> 8 * 0;
		a &= 255;
		r &= 255;
		g &= 255;
		b &= 255;
	}

	public int getA() {
		return a;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int toInt() {
		return a << 8 * 3 | r << 8 * 2 | g << 8 | b;
	}

	public RGB toLimit() {
		if (r + g + b > 255 * 3 / 2) {
			r = g = b = 255;
		} else {
			r = g = b = 0;
		}
		return this;
	}

	public RGB toGry() {
		r = g = b = (r + g + b) / 3;
		return this;
	}

	public RGB toDark(double scale) {
		r *= scale;
		g *= scale;
		b *= scale;
		return this;
	}

	public RGB clone() {
		return new RGB(toInt());
	}

	@Override
	public String toString() {
		return "(" + a + "," + r + "," + g + "," + b + ")";
	}

	public Index toIndex(int size) {
		r = r * size / 256;
		g = g * size / 256;
		b = b * size / 256;
		return new Index(r, g, b);
	}

}