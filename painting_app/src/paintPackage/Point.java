package paintPackage;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Point {
	Color color;
	int x,y;
	public Point(int x1, int y1, Color some_color) {
		x=x1;
		y=y1;
		color = some_color;
	}
	public void draw(Graphics2D g2d) {
		g2d.setColor( color );
		g2d.fillOval(x, y, 3, 3);
		
	}


}
