package paintPackage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	static final int WIDTH=700, HEIGHT=500;
	JMenuItem menuItem1px, menuItem5px, menuItem10px, menuItem50px, menuCredit, menuClear, menuSave, menuLoad;
	JPanel panel_up, panel_down;
	JButton colorBGChooser, colorLNChooser;
	public Color colorBG=Color.white, colorLN=Color.BLACK;
	JRadioButton pencil, rubber, line, curve;  
	Canvas canv;
	boolean onCanvas, isLoaded=false;
	int selectedDrawing=1, moduloTwo=0, linewidth=1;
	
    public Main() throws HeadlessException {
    	this.setSize(WIDTH,HEIGHT);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	this.setLayout(new BorderLayout());
    //---------------------------------------------------------------------------------------
    	JMenuBar menuBar = new JMenuBar();
    	JMenu menu1 = new JMenu("Line width");
    	JMenu menu2 = new JMenu("Credits");
    	JMenu options = new JMenu("Options");
    	
    	menuClear = new JMenuItem("Clear drawing");
    	menuClear.addActionListener(this);
    	menuSave = new JMenuItem("Save to file");
    	menuSave.addActionListener(this);
    	menuLoad = new JMenuItem("Load from file");
    	menuLoad.addActionListener(this);
    	menuItem1px = new JMenuItem("1px");
    	menuItem5px = new JMenuItem("5px");
    	menuItem10px = new JMenuItem("10px");
    	menuItem50px = new JMenuItem("50px");
    	menuItem1px.addActionListener(this);
    	menuItem5px.addActionListener(this);
    	menuItem10px.addActionListener(this);
    	menuItem50px.addActionListener(this);
    	menuCredit = new JMenuItem("Open");	
    	menuCredit.addActionListener(this);
    	options.add(menuClear);
    	options.add(menuSave);
    	options.add(menuLoad);
    	menu1.add(menuItem1px);
    	menu1.add(menuItem5px);
    	menu1.add(menuItem10px);
    	menu1.add(menuItem50px);
    	menu2.add(menuCredit);
    	menuBar.add(options);
    	menuBar.add(menu1);
    	menuBar.add(menu2);
    	this.setJMenuBar(menuBar);
    //---------------------------------------------------------------------------------------			    	
    	panel_up = new JPanel();
    	this.add(panel_up, BorderLayout.PAGE_START);
    	ButtonGroup buttonGroup = new ButtonGroup();
    	pencil = new JRadioButton("Pencil");
    	pencil.setActionCommand("1");
    	pencil.setSelected(true);
    	pencil.addActionListener(this);
    	rubber = new JRadioButton("Rubber");
    	rubber.setActionCommand("2");
    	rubber.addActionListener(this);
    	line = new JRadioButton("Line");
    	line.setActionCommand("3");
    	line.addActionListener(this);
    	curve = new JRadioButton("Curve");
    	curve.setActionCommand("4");
    	curve.addActionListener(this);
    	buttonGroup.add(pencil);
    	buttonGroup.add(rubber);
    	buttonGroup.add(line);
    	buttonGroup.add(curve);
    	panel_up.add(pencil);
    	panel_up.add(rubber);
    	panel_up.add(line);
    	panel_up.add(curve);
    
    //---------------------------------------------------------------------------------------
    	panel_down = new JPanel();
    	this.add(panel_down, BorderLayout.PAGE_END);
    	colorBGChooser = new JButton("BG color");
 	    colorLNChooser = new JButton("LN color");
 	    colorBGChooser.addActionListener(this);
 	    colorLNChooser.addActionListener(this);
 	    panel_down.add(colorBGChooser);
 	    panel_down.add(colorLNChooser);
 	    
 	//---------------------------------------------------------------------------------------
 	    canv = new Canvas();
 	    this.add(canv);
 	    canv.addMouseListener(this);
 	    canv.addMouseMotionListener(this);
 	    canv.setBackground(colorBG);
 	     	     
 	    
 	    
    }
    //---------------------------------------------------------------------------------------
    public Main(GraphicsConfiguration gc) {
        super(gc);
        this.setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public Main(String title) throws HeadlessException {
        super(title);
        this.setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public Main(String title, GraphicsConfiguration gc) {
        super(title, gc);
        this.setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    //---------------------------------------------------------------------------------------   
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==menuItem1px) {
			linewidth = 1;
		}
		else if(e.getSource()==menuItem5px) {
			linewidth = 5;
		}
		else if(e.getSource()==menuItem10px) {
			linewidth = 10;
		}
		else if(e.getSource()==menuItem50px) {
			linewidth = 50;						//Stroke chyba nie lubi duzych rozmiarow
		}
		else if(e.getSource() == menuCredit) {
			 JFrame credit = new JFrame();
			 credit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			 credit.setSize(WIDTH/3,HEIGHT/3);
			 JLabel author = new JLabel("Project created by Krzysztof Palmi");
			 credit.add(author);
			 credit.setVisible(true);
		}
		else if(e.getSource() == menuClear) {
			canv.lines.clear();
			canv.points.clear();
			canv.isLoaded = false;
			canv.image= null;
			canv.repaint();
		}
		else if(e.getSource() == menuSave) {
			BufferedImage image = new BufferedImage(canv.getWidth(), canv.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = image.createGraphics();
			canv.paintAll(g2d);
										
			File outputfile = new File("saved.png");
										
			try {
				ImageIO.write(image, "png", outputfile);
				JFrame saved = new JFrame("Warning!");
				saved.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				saved.setSize(WIDTH/4, HEIGHT/4);
				JLabel label = new JLabel("Saved successfully.");
				saved.add(label);
				saved.setVisible(true);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		else if(e.getSource() == menuLoad) {
			canv.image = null; //Tworzenie obiektu BufferedImage
 
			File inputFile = new File("saved.png");  //Tworzenie pliku wejœciowego
								
			//Odczytywanie pliku wejœciowego 
			try {
				canv.image = ImageIO.read(inputFile);
				canv.isLoaded = true;
			} catch(IOException ex) {
				System.out.println(ex.getMessage());
			}
			canv.repaint();					
		}
		else if (e.getSource()==colorBGChooser) {
			colorBG = JColorChooser.showDialog(this, "Select a color", Color.black);
			canv.setBackground(colorBG);
		}
		else if(e.getSource()==colorLNChooser) {
			colorLN = JColorChooser.showDialog(this, "Select a color", Color.black);
		}
		else if(e.getActionCommand() == "1") {
			selectedDrawing = 1;
			
		}
		else if(e.getActionCommand() == "2") {
			selectedDrawing = 2;
		}
		else if(e.getActionCommand() == "3") {
			selectedDrawing = 3;
			
		}
		else if(e.getActionCommand() == "4") {
			selectedDrawing = 4;
			canv.lines.add(new Line());
		}
		
	}	
	//---------------------------------------------------------------------------------------

	@Override
	public void mouseClicked(MouseEvent e) {
		if(selectedDrawing == 1) {
			// do nothing
		}
		else if(selectedDrawing == 2) {
			// do nothing
		}
		else if(selectedDrawing == 3) {
			if(moduloTwo % 2 == 0) {
				canv.lines.add(new Line());
				canv.lines.get( canv.lines.size()-1 ).addPoint(e.getX(), e.getY());
				moduloTwo++;
			}
			else if(moduloTwo % 2 == 1) {
				canv.lines.get( canv.lines.size()-1 ).addPoint(e.getX(), e.getY());
				moduloTwo++;
			}
			canv.lines.get( canv.lines.size()-1 ).linewidth = linewidth;
			canv.lines.get( canv.lines.size()-1 ).color = colorLN;
		}
		else if(selectedDrawing == 4) {
		 	canv.lines.get( canv.lines.size()-1 ).addPoint(e.getX(), e.getY());
		 	canv.lines.get( canv.lines.size()-1 ).linewidth = linewidth;
			canv.lines.get( canv.lines.size()-1 ).color = colorLN;
		}
		canv.repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		onCanvas = true;
		//System.out.println(onCanvas);
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		onCanvas = false;
		//System.out.println(onCanvas);
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedDrawing == 1) {
			canv.points.add(new Point(e.getX(), e.getY(), colorLN ));
		}
		else if(selectedDrawing == 2) {
			canv.points.add(new Point(e.getX(), e.getY(), colorBG )); //adds points with background color
		}
		canv.repaint();
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.printf("MOUSE MOVED X: %d Y: %d\n", e.getX(), e.getY());
		
	}
	//---------------------------------------------------------------------------------------
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.setVisible(true);
	}




	
}
