package io.github.bugcreatorltq;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.datafixers.util.Pair;

public class ImageDrawer {

	private static Material colors[][][] = null;
	private static LinkedList<Pair<Material, Index>> color = null;

	private static int getColorSize() {
		return color.size();
	}

	private static void setColor(Index index, Material color) {
		colors[index.getR()][index.getG()][index.getB()] = color;
	}

	private static Material getColor(Index index) {
		return colors[index.getR()][index.getG()][index.getB()];
	}

	private static void checkSafeAndSet(Queue<Index> queue, Index index, int r, int g, int b) {
		Material current = getColor(index);
		int size = getColorSize();
		r += index.getR();
		g += index.getG();
		b += index.getB();
		if (r >= 0 && r < size && g >= 0 && g < size && b >= 0 && b < size) {
			index = new Index(r, g, b);
			if (getColor(index) == Material.AIR) {
				setColor(index, current);
				queue.offer(index);
			}
		}
	}

	public static void init() {
		Queue<Index> queue = new LinkedList<Index>();
		color = new LinkedList<Pair<Material, Index>>();
//		color.add(new Pair<Material, Index>(Material.WHITE_WOOL, new Index(145, 145, 145)));
		color.add(new Pair<Material, Index>(Material.WHITE_WOOL, new Index(245, 245, 245)));
		color.add(new Pair<Material, Index>(Material.ORANGE_WOOL, new Index(134, 58, 1)));
		color.add(new Pair<Material, Index>(Material.LIGHT_BLUE_WOOL, new Index(31, 101, 127)));
		color.add(new Pair<Material, Index>(Material.MAGENTA_WOOL, new Index(105, 33, 99)));
		color.add(new Pair<Material, Index>(Material.YELLOW_WOOL, new Index(149, 122, 26)));
		color.add(new Pair<Material, Index>(Material.LIME_WOOL, new Index(73, 116, 16)));
		color.add(new Pair<Material, Index>(Material.PINK_WOOL, new Index(145, 84, 102)));
		color.add(new Pair<Material, Index>(Material.GRAY_WOOL, new Index(34, 34, 34)));
		color.add(new Pair<Material, Index>(Material.LIGHT_GRAY_WOOL, new Index(76, 76, 76)));
		color.add(new Pair<Material, Index>(Material.CYAN_WOOL, new Index(12, 79, 84)));
		color.add(new Pair<Material, Index>(Material.PURPLE_WOOL, new Index(67, 21, 98)));
		color.add(new Pair<Material, Index>(Material.BLUE_WOOL, new Index(28, 30, 88)));
		color.add(new Pair<Material, Index>(Material.BROWN_WOOL, new Index(59, 36, 20)));
		color.add(new Pair<Material, Index>(Material.GREEN_WOOL, new Index(46, 58, 19)));
		color.add(new Pair<Material, Index>(Material.RED_WOOL, new Index(100, 25, 21)));
		color.add(new Pair<Material, Index>(Material.BLACK_WOOL, new Index(8, 9, 12)));
//		Material.TERRACOTTA
		color.add(new Pair<Material, Index>(Material.WHITE_TERRACOTTA, new Index(124, 105, 95)));
		color.add(new Pair<Material, Index>(Material.ORANGE_TERRACOTTA, new Index(97, 50, 23)));
		color.add(new Pair<Material, Index>(Material.LIGHT_BLUE_TERRACOTTA, new Index(88, 51, 64)));
		color.add(new Pair<Material, Index>(Material.MAGENTA_TERRACOTTA, new Index(68, 65, 82)));
		color.add(new Pair<Material, Index>(Material.YELLOW_TERRACOTTA, new Index(110, 78, 21)));
		color.add(new Pair<Material, Index>(Material.LIME_TERRACOTTA, new Index(62, 69, 31)));
		color.add(new Pair<Material, Index>(Material.PINK_TERRACOTTA, new Index(96, 46, 47)));
		color.add(new Pair<Material, Index>(Material.GRAY_TERRACOTTA, new Index(34, 24, 21)));
		color.add(new Pair<Material, Index>(Material.LIGHT_GRAY_TERRACOTTA, new Index(82, 65, 59)));
		color.add(new Pair<Material, Index>(Material.CYAN_TERRACOTTA, new Index(52, 55, 55)));
		color.add(new Pair<Material, Index>(Material.PURPLE_TERRACOTTA, new Index(70, 41, 51)));
		color.add(new Pair<Material, Index>(Material.BLUE_TERRACOTTA, new Index(46, 35, 55)));
		color.add(new Pair<Material, Index>(Material.BROWN_TERRACOTTA, new Index(45, 29, 21)));
		color.add(new Pair<Material, Index>(Material.GREEN_TERRACOTTA, new Index(44, 49, 24)));
		color.add(new Pair<Material, Index>(Material.RED_TERRACOTTA, new Index(86, 37, 28)));
		color.add(new Pair<Material, Index>(Material.BLACK_TERRACOTTA, new Index(22, 13, 9)));
		color.add(new Pair<Material, Index>(Material.TERRACOTTA, new Index(89, 55, 40)));
		for (Pair<Material, Index> pair : color) {
			Index index = pair.getSecond();
			((RGB) index).toIndex(getColorSize());
		}
		int size = getColorSize();
		colors = new Material[size][size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					colors[i][j][k] = Material.AIR;
				}
			}
		}
		for (Pair<Material, Index> pair : color) {
			queue.offer(pair.getSecond());
			setColor(pair.getSecond(), pair.getFirst());
		}

		while (!queue.isEmpty()) {
			Index index = queue.poll();
			checkSafeAndSet(queue, index, 1, 0, 0);
			checkSafeAndSet(queue, index, 0, 1, 0);
			checkSafeAndSet(queue, index, 0, 0, 1);
			checkSafeAndSet(queue, index, -1, 0, 0);
			checkSafeAndSet(queue, index, 0, -1, 0);
			checkSafeAndSet(queue, index, 0, 0, -1);
		}

	}

	public static void draw(CommandSender sender, String imageUrl) {
		try {
			if (colors == null) {
				throw new Exception("colors 不能为空　检查是否初始化");
			}
			sender.sendMessage("正在读取...");
			BufferedImage image = readImage(new URL(imageUrl));
			if (image == null) {
				return;
			}
			sender.sendMessage("width is " + image.getWidth());
			sender.sendMessage("height is " + image.getHeight());
			image = scale(image, 150);
			sender.sendMessage("缩放至(" + image.getWidth() + "," + image.getHeight() + ")");
			sender.sendMessage("开始绘制");
			drawing(sender, image);
			sender.sendMessage("绘制结束");
		} catch (IOException e) {
			sender.sendMessage("检查图片路径是否正确");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void saveImage(BufferedImage image, String name) throws IOException {
		File file = new File("image/" + name + ".jpg");
		file.createNewFile();
		ImageIO.write(image, "jpg", file);
	}

	static BufferedImage readImage(URL url) throws MalformedURLException, IOException {
		return ImageIO.read(url);
	}

	static BufferedImage readImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	static void working(BufferedImage image, RGB.Type type) {
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				RGB rgb = new RGB(image.getRGB(i, j));
				switch (type) {
				case Limit:
					rgb.toLimit();
					break;
				case Gry:
					rgb.toGry();
					break;
				case Dark:
					rgb.toDark(0.5);
					break;
				}
				image.setRGB(i, j, rgb.toInt());
			}
		}
	}

	static BufferedImage scale(BufferedImage image, int height) {
		int width = image.getWidth() * height / image.getHeight();
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = result.getGraphics();
		g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, width, height, null);
		g.dispose();
		return result;
	}

	static void drawing(CommandSender sender, BufferedImage image) {
		if (image.getWidth() > 1000) {
			sender.sendMessage("图片尺寸过大");
		}
		Location location = ((Player) sender).getLocation();
		location.add(image.getWidth() / 2, 0, -image.getWidth() / 2);
		location.setY(250);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				RGB rgb = new RGB(image.getRGB(i, j));
				Index index = rgb.toIndex(getColorSize());
				location.clone().add(0, -j, i).getBlock().setType(getColor(index));
			}
		}
	}

	public static void drawColors(CommandSender sender) {
		int size = getColorSize();
		Location location = ((Player) sender).getLocation();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				for (int k = 0; k < size; k++) {
					location.clone().add(i * 2, j * 2, k * 2).getBlock().setType(getColor(new Index(i, j, k)));
				}
			}
		}
	}
}
