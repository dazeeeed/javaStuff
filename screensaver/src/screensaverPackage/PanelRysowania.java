package screensaverPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class PanelRysowania extends JPanel {

	private static final long serialVersionUID = 1L;
	ArrayList<Prostokat> prostakaty = new ArrayList<Prostokat>();

	protected Timer timer1, timer2;
	private int dt=10;
	private int V_MAX=5; //default to 9 (for speed +-10), optimal 5
	private boolean isDone = false;

	public PanelRysowania() {
		this.setPreferredSize( this.getPreferredSize());
		this.setBackground(Color.DARK_GRAY);
		timer1= new Timer(dt,null);
		timer1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<prostakaty.size(); i++) {
					if(prostakaty.get(i).getX()+prostakaty.get(i).getWidth()>=getBounds().width || prostakaty.get(i).getX()<=0)  //some will bug on borders (SOLUTION: change frame size)
						prostakaty.get(i).setVx( prostakaty.get(i).getVx()*(-1) );
					if(prostakaty.get(i).getY()+(prostakaty.get(i).getHeight()) >=getBounds().height || prostakaty.get(i).getY()<=0)
						prostakaty.get(i).setVy( prostakaty.get(i).getVy()*(-1) );
					prostakaty.get(i).setX( (prostakaty.get(i).getX()+prostakaty.get(i).getVx()) );
					prostakaty.get(i).setY( (prostakaty.get(i).getY()+prostakaty.get(i).getVy()) );
					
					
					repaint();
				}
				
				if(isDone==false) {
					for(int j=1; j<=5; j++) {
						for(int i=0; i< prostakaty.size(); i++) {
							if(prostakaty.get(i).getShowImage()==true) {
								try {
									prostakaty.get(i).image[j-1] = ImageIO.read( new File("pic"+j+".png"));
									
								}
								catch(IOException ex) {
									System.out.println(ex.getMessage());
								}
							}
						}
					}
					isDone=true;
				}	
			}
    	 
        });
		timer1.start();
		timer2= new Timer(1000,null);
		timer2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e0) {
				for(int i=0; i<prostakaty.size(); i++) {
					if(prostakaty.get(i).getShowImage()==true) {
						prostakaty.get(i).selectedImage= (prostakaty.get(i).selectedImage + 1) % 5 ;
					}
				}
			}
		});
		timer2.start();
		
	}
	
	public void dodajLosowyProstokat(){
		Random r = new Random();
		
		Prostokat p = new Prostokat();
		p.setX(r.nextInt(550));
		p.setY(r.nextInt(550));
		p.setWidth(r.nextInt(80));
		p.setHeight(r.nextInt(80));
		p.setColor(new Color(r.nextInt(255), r.nextInt(255),
				r.nextInt(255), r.nextInt(255)));
		p.setVx( (r.nextInt(V_MAX)+1)*(r.nextBoolean() ? -1 : 1 ));
		p.setVy( (r.nextInt(V_MAX)+1)*(r.nextBoolean() ? -1 : 1 ));
		p.setShowImage(r.nextBoolean());
		prostakaty.add(p);		
	}
	
	public void dodajProstokat(int x, int y, int width, int height, Color c){
		Prostokat p = new Prostokat();
		Random r = new Random();
		p.setX(x);
		p.setY(y);
		p.setWidth(width);
		p.setHeight(height);
		p.setColor(c);
		p.setVx( (r.nextInt(V_MAX)+1)*(r.nextBoolean() ? -1 : 1 ));
		p.setVy( (r.nextInt(V_MAX)+1)*(r.nextBoolean() ? -1 : 1 ));
		p.setShowImage(r.nextBoolean());
		prostakaty.add(p);		
		
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Prostokat pr : prostakaty) {
			pr.paint(g);
		}

	}
	
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}
}
