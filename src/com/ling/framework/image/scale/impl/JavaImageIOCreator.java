package com.ling.framework.image.scale.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ling.framework.image.scale.IThumbnailCreator;
import com.ling.framework.image.scale.ImageRuntimeException;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 使用javax image io生成缩略图
 */
public class JavaImageIOCreator implements IThumbnailCreator {
	private int width;
	private int height;
	private Image img;
	private String srcFile;
	private String destFile;

	public JavaImageIOCreator(String sourcefile, String targetFile) {
		File file = new File(sourcefile); // 读入文件
		this.srcFile  = file.getName();
		this.destFile = targetFile;
		try {
			img = javax.imageio.ImageIO.read(file);
		} catch (IOException e) {
			//ignore
		} // 构造Image对象
		width  = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}

	public void resize(int w, int h) {
		int target_w, target_h; // 目标宽高
		int x = 0, y = 0; // 缩略图在背景的座标
		x = y = 0;
		target_w = w;
		target_h = h;

		/* 计算目标宽高 */
		if (width / height > w / h) { // 原图长:上下补白
			target_w = w;
			target_h = (int) (target_w * height / width);
			x = 0;
			y = (int) (h - target_h) / 2;
		}

		if (width / height < w / h) { // 原图高:左右补白
			target_h = h;
			target_w = (int) (target_h * width / height);
			y = 0;
			x = (int) (w - target_w) / 2;
		}

		BufferedImage _image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = _image.getGraphics();
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, _image.getWidth(), _image.getHeight());
		graphics.drawImage(img, x, y, target_w, target_h, null); // 绘制缩小后的图
		FileOutputStream out;
		try {
			out = new FileOutputStream(destFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(_image);
			out.close();
		} catch (FileNotFoundException e) {
			throw new ImageRuntimeException(srcFile, "生成缩略图");
		} catch (ImageFormatException e) {
			throw new ImageRuntimeException(srcFile, "生成缩略图");
		} catch (IOException e) {
			throw new ImageRuntimeException(srcFile, "生成缩略图");
		}
	}

	public static void main(String args[]) {
		JavaImageIOCreator creator = new JavaImageIOCreator("I:/car/22758338a6836a3496ddd807.jpg", "e:/2.jpg");
		creator.resize(200, 200);
	}
}