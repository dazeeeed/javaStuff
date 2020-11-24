package screensaverPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;



public class Prostokat {

    private int xPos = 50;
	private int yPos = 50;
    private int width = 20;
    private int height = 20;
    private Color color = Color.BLACK;
    private int vx=0, vy=0;
    private boolean showImage = false;
    protected BufferedImage[] image = new BufferedImage[5];
    protected int selectedImage=0;
    
    public int getX() {
		return xPos;
	}

	public void setX(int xPos) {
		this.xPos = xPos;
	}

    public void setY(int yPos){
        this.yPos = yPos;
    }

    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    } 

    public int getHeight(){
        return height;
    }
    public int getVx() {
    	return vx;
    }
    public void setVx(int Vx) {
    	this.vx=Vx;
    }
    public int getVy() {
    	return vy;
    }
    public void setVy(int Vy) {
    	this.vy=Vy;
    }

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

    public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean getShowImage() {
		return showImage;
	}
	
	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}
	
	public void setSelectedImage(int selectedimage) {
		this.selectedImage = selectedimage;
	}
	
	public void paint(Graphics g){
		if(showImage==false) {
			g.setColor(getColor());
        	g.fillRect(xPos,yPos,getWidth(),getHeight());
		}
		else {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(image[selectedImage], xPos, yPos,null);
		}
    }
}
