package paintPackage;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Line {

    private ArrayList<Integer> xList;
    private ArrayList<Integer> yList;
    int linewidth=1;
    Color color;
    
    public Line() {
       xList = new ArrayList<Integer>();
       yList = new ArrayList<Integer>();
    }
  
    public void addPoint(int x, int y) {
       xList.add(x);
       yList.add(y);
    }
  
    public void draw(Graphics2D g2d) {
    	g2d.setStroke(new BasicStroke((float) linewidth));
    	g2d.setColor(color);
        for (int i = 0; i < xList.size() - 1; ++i) 
        {
        	g2d.drawLine(xList.get(i), yList.get(i), xList.get(i + 1), yList.get(i + 1));
        }
    }

}
