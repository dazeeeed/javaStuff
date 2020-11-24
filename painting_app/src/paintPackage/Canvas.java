package paintPackage;

import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;


import java.awt.*;

public class Canvas extends JPanel {
	public ArrayList<Line> lines = new ArrayList<Line>();
	public ArrayList<Point> points = new ArrayList<Point>();
	boolean isLoaded = false;
	BufferedImage image;
	Color color;
	
	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this);
	
		for(Line line: lines)
			line.draw(g2);
		for(Point point: points)
			point.draw(g2);
	}
		
}
