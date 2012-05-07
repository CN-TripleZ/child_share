package com.ling.image;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;

import com.ling.child_share.model.Image;

public class ImageBuilder{

	private int descWidth = 300;
	
	private List<Image> images;
	
	protected ImageBuilder(List<Image> images) {
		this.images = images;
	}

	public void build() {
		if (images == null || images.size() == 0) 
			return;
		BufferedImage[] ImageResult = new BufferedImage[images.size()];
		BufferedImage[] imageBuff = new BufferedImage[images.size()];
		int[][] ImageArrays = new int[images.size()][];
		int[][] newImageArrays = new int[images.size()][];
		for (int i = 0; i < images.size(); i++) {
			File iFile = new File(images.get(i).getImg_path());
			try {
				//读取源图片信息
				imageBuff[i] = ImageIO.read(iFile);;
				int width = imageBuff[i].getWidth();
				int height = imageBuff[i].getHeight();
				ImageArrays[i] = new int[width * height];
				ImageArrays[i] = imageBuff[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
				
				int nw = width + descWidth;
				BufferedImage ImageNew = new BufferedImage(nw, height, BufferedImage.TYPE_INT_RGB);		
				if (i % 2 == 0) {
					//填充原图
					ImageNew.setRGB(0, 0, width, height, ImageArrays[i], 0, width);		

					clearTextArea(width, 0, descWidth, height, ImageNew);
					drawLine(width + 13, 0, width + 13, height * 2 / 3, ImageNew);
					drawDate(width + 23, 25, images.get(i).getUpload_time(), ImageNew);					
					drawText(images.get(i), width + 23, 50, ImageNew);
				} else {
					//填充原图
					ImageNew.setRGB(descWidth, 0, width, height, ImageArrays[i], 0, width);	

					clearTextArea(0, 0, descWidth, height, ImageNew);
					drawLine(descWidth - 13, 0, descWidth - 13, height * 2 / 3, ImageNew);
					drawDate(0, 25, images.get(i).getUpload_time(), ImageNew);					
					drawText(images.get(i), 0, 50, ImageNew);
				}
				ImageResult[i] = ImageNew;
				
				//cache新图片信息
				newImageArrays[i] = new int[nw * height];
				newImageArrays[i] = ImageNew.getRGB(0, 0, nw, height, newImageArrays[i], 0, nw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//图片拼接
		try {
			Integer[] maxArea = calImageMaxArea(ImageResult);
			int w = maxArea[0];
			int h = maxArea[1];

			BufferedImage ImageNew = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			int nh = 0;
			for (int i = 0; i < ImageResult.length; i++) {
				ImageNew.setRGB(0, nh, ImageResult[i].getWidth(), ImageResult[i].getHeight(), newImageArrays[i], 0, ImageResult[i].getWidth());
				nh += ImageResult[i].getHeight();
			}

			File outFile = new File("E:\\photo\\jeffreyzhang\\aa.jpg");
			ImageIO.write(ImageNew, "jpg", outFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void clearTextArea(int x, int y, int w, int h, BufferedImage imageBuf) {
		Graphics2D g = imageBuf.createGraphics();
		g.setBackground(Color.white);
		g.clearRect(x, y, w, h);
		g.dispose();
	}

	private void drawLine(int x, int y, int x1, int y1, BufferedImage imageBuf) {
		Graphics2D g;
		g = imageBuf.createGraphics();
		g.setColor(Color.BLUE);
		BasicStroke stroke = new BasicStroke(8);
		g.setStroke(stroke);
		Line2D line = new Line2D.Float(x, y, x1, y1);
		g.draw(line);
		g.dispose();
	}

	private void drawDate(int x, int y, Date date, BufferedImage imageBuf) {
		Graphics2D g;
		g = imageBuf.createGraphics();	
		g.setColor(Color.BLACK);
		Font f = new Font("黑体", Font.BOLD, 20);
		g.setFont(f);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String dateStr = df.format(date);
		g.drawString(dateStr, x, y);
		g.dispose();
	}

	private Integer[] calImageMaxArea(BufferedImage[] imageResult) {
		int w = 0;
		int h = 0;
		for (int i = 0; i < imageResult.length; i++) {
			w = w > imageResult[i].getWidth() ? w : imageResult[i].getWidth();

			h += imageResult[i].getHeight();
		}
		return new Integer[]{w,h};
	}

	private void drawText(Image image, int startX, int startY, BufferedImage imageBuff) {
		Graphics2D g = imageBuff.createGraphics();
		g.setColor(Color.black);

		int textWidth = descWidth - 23;
		FontRenderContext frc = g.getFontRenderContext();
		Hashtable map = new Hashtable();
		Font f = new Font("宋体", Font.BOLD, 20);
		map.put(TextAttribute.FONT, f);

		AttributedCharacterIterator text = new AttributedString(image.getDescription(), map).getIterator();
		
        int paragraphStart = text.getBeginIndex();
        int paragraphEnd = text.getEndIndex();

        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(text, frc);
        float drawPosY = startY;
        while (lineMeasurer.getPosition() < paragraphEnd) {
            TextLayout layout = lineMeasurer.nextLayout(textWidth);
            drawPosY += layout.getAscent();
            
            float drawPosX;
            if (layout.isLeftToRight()) {
                drawPosX =  startX;
            }
            else {
                drawPosX = descWidth - layout.getAdvance();
            }
            layout.draw(g, drawPosX, drawPosY);
            drawPosY += layout.getDescent() + layout.getLeading();
        }
	}
	public static void main(String[] args) {
		List<Image> images = new ArrayList<Image>();
		Image image = new Image();
		image.setDescription("    期待你的快乐成长，， 期待你的快乐成长，， 期待你的快乐成长，， 期待你的快乐成长，， 期待你的快乐成长，，");
		image.setImg_path("E:\\photo\\jeffreyzhang\\pic1.jpg");
		image.setUpload_time(new Date());
		images.add(image);
		
		image = new Image();
		image.setDescription("   今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，今天笑了，");
		image.setImg_path("E:\\photo\\jeffreyzhang\\pic6.jpg");
		image.setUpload_time(new Date());
		images.add(image);
		
		image = new Image();
		image.setDescription("   你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好");
		image.setImg_path("E:\\photo\\jeffreyzhang\\pic3.jpg");
		image.setUpload_time(new Date());
		images.add(image);
		ImageBuilder builder = new ImageBuilder(images);
		builder.build();
	}
}
