package io.github.bugcreatorltq;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.bugcreatorltq.RGB.Type;

public class ImageDrawer {

	public static void draw(CommandSender sender, String imageUrl) {
		try {
			sender.sendMessage("正在读取...");
			BufferedImage image = readImage(new URL(imageUrl));
			if (image == null) {
				return;
			}
			sender.sendMessage("width is " + image.getWidth());
			sender.sendMessage("height is " + image.getHeight());
			image = scale(image, 100);
			sender.sendMessage("缩放至(" + image.getWidth() + "," + image.getHeight() + ")");
			sender.sendMessage("开始灰度化");
			working(image, Type.Gry);
			sender.sendMessage("开始绘制");
			drawing(sender, image);
			sender.sendMessage("绘制结束");
		} catch (IOException e) {
			sender.sendMessage("检查图片路径是否正确");
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
		LinkedList<Material> colors = new LinkedList<Material>();
		colors.add(Material.BLACK_WOOL);
		colors.add(Material.GRAY_WOOL);
		colors.add(Material.LIGHT_GRAY_TERRACOTTA);
		colors.add(Material.LIGHT_GRAY_WOOL);
		colors.add(Material.WHITE_TERRACOTTA);
		colors.add(Material.WHITE_WOOL);
		if (image.getHeight() > 200) {
			sender.sendMessage("图片尺寸过大");
		}
		Location location = ((Player) sender).getLocation();
		location.add(20, 0, 0);
		location.setY(200);
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				RGB rgb = new RGB(image.getRGB(i, j));
				location.clone().add(0, -j, i).getBlock().setType(colors.get(rgb.getB() * colors.size() / 255));
			}
		}
	}
}
