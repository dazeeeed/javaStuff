package polygonPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PolygonDrawer extends JFrame implements ActionListener {
	static final int WIDTH = 700;
	static final int HEIGHT = 500;
	static final int PANEL_NUMBER = 5;
	static final int SLIDER_INIT = 10;
	JSlider slider;
	JLabel XtextFieldLabel, YtextFieldLabel;
	ArrayList<JTextField> posXFieldArray, posYFieldArray;
	ArrayList<Integer> posX, posY;
	JButton colorBGChooser, colorLNChooser;
	JRadioButton radioButton1, radioButton2;
	JPanel[] panel;
	Color color1, color2;
	int R=100, linewidth=5;
	JMenuItem menuItem1px, menuItem5px, menuItem10px, menuCredit;
	boolean regular = true; 
	
	public class Canv extends JPanel{
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setColor(color2);
	        g2.setStroke(new BasicStroke((float) linewidth));
	        for(int i=0; i<slider.getValue()-1; i++) {
				g2.drawLine(getWidth()/2+ posX.get(i), getHeight()/2+posY.get(i), getWidth()/2+posX.get(i+1), getHeight()/2+posY.get(i+1));
			}	
			g2.drawLine(getWidth()/2+ posX.get(0), getHeight()/2+posY.get(0), getWidth()/2+posX.get(slider.getValue()-1), getHeight()/2+posY.get(slider.getValue()-1));
	        }
		}
	Canv canv;

	public PolygonDrawer() throws HeadlessException {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(WIDTH,HEIGHT);
		this.getContentPane().setBackground(Color.lightGray);
		this.setLayout(new BorderLayout());
		
		panel = new JPanel[PANEL_NUMBER];
		for(int i=0; i<PANEL_NUMBER; i++) {
			panel[i]=new JPanel();
		}
		canv = new Canv();
//====================================================================================
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("Line width");
		JMenu menu2 = new JMenu("Credits");
		
		menuItem1px = new JMenuItem("1px");
		menuItem5px = new JMenuItem("5px");
		menuItem10px = new JMenuItem("10px");
		menuItem1px.addActionListener(this);
		menuItem5px.addActionListener(this);
		menuItem10px.addActionListener(this);
		menuCredit = new JMenuItem("Open");	
		menuCredit.addActionListener(this);
		menu1.add(menuItem1px);
		menu1.add(menuItem5px);
		menu1.add(menuItem10px);
		menu2.add(menuCredit);
		menuBar.add(menu1);
		menuBar.add(menu2);
		this.setJMenuBar(menuBar);
//====================================================================================			
		this.add(panel[0], BorderLayout.PAGE_START);
		this.add(panel[1], BorderLayout.PAGE_END);
		this.add(panel[2], BorderLayout.WEST);
		this.add(panel[3], BorderLayout.EAST);
		//this.add(panel[4], BorderLayout.CENTER);
		this.add(canv, BorderLayout.CENTER);
//====================================================================================	  		
		JLabel sliderLabel = new JLabel("No. of vertices");
		slider = new JSlider(JSlider.HORIZONTAL, 3, 33, SLIDER_INIT);
		JButton draw = new JButton("Draw");
		
		panel[0].setLayout(new FlowLayout());
		panel[0].add(sliderLabel);
		panel[0].add(slider);
		panel[0].add(draw);
	    slider.setMajorTickSpacing(10); // draw the minor tick marks (between the tick labels)
	    slider.setMinorTickSpacing(2); 	// draw the tick marks
	    slider.setPaintTicks(true); 	// draw the tick mark labels
	    slider.setPaintLabels(true);
	    slider.addChangeListener(new SliderChangeListener());
//====================================================================================	    
	    colorBGChooser = new JButton("BG color");
	    colorLNChooser = new JButton("LN color");
	    colorBGChooser.addActionListener(this);
	    colorLNChooser.addActionListener(this);
	    panel[1].add(colorBGChooser);
	    panel[1].add(colorLNChooser);
//====================================================================================	      
	    JLabel radioButtonLabel = new JLabel("PolygonDrawer");
	    radioButton1 = new JRadioButton("Regular");
	    radioButton1.addActionListener(this);
	    radioButton1.setActionCommand("0");
	    radioButton1.setSelected(true);
	    radioButton2 = new JRadioButton("Random");
	    radioButton2.addActionListener(this);
	    radioButton2.setActionCommand("1");
	    panel[2].setLayout(new BoxLayout(panel[2], BoxLayout.Y_AXIS));
	    panel[2].add(radioButtonLabel);
	    panel[2].add(radioButton1);
	    panel[2].add(radioButton2);

		ButtonGroup group = new ButtonGroup();
		group.add(radioButton1);
		group.add(radioButton2);

//====================================================================================	  
		XtextFieldLabel = new JLabel("X pos.");
		YtextFieldLabel = new JLabel("Y pos.");
		panel[3].setLayout(new GridLayout(1+SLIDER_INIT, 2));
		//sliderText = new JTextField( String.format("%d     ", slider.getValue()) );
		posXFieldArray = new ArrayList<JTextField>();
		posYFieldArray = new ArrayList<JTextField>();
		
		panel[3].add(XtextFieldLabel);
		panel[3].add(YtextFieldLabel);
		posX = new ArrayList<Integer>();
		posY = new ArrayList<Integer>();
		for(int i=0; i<SLIDER_INIT; i++) {
			posX.add( (int) (R*Math.cos( (Math.PI/2 + 2*Math.PI*i)/SLIDER_INIT )) );
			posY.add( (int) (R*Math.sin( (Math.PI/2 + 2*Math.PI*i)/SLIDER_INIT )) );
			posXFieldArray.add(new JTextField(Integer.toString(posX.get(i))) );
			posYFieldArray.add(new JTextField(Integer.toString(posY.get(i))) );
			
			panel[3].add(posXFieldArray.get(i));
			panel[3].add(posYFieldArray.get(i));
		}
		
		
//====================================================================================		
		panel[4].setBackground(Color.white);
	}
	
//====================================================================================	
//====================================================================================	
	public class SliderChangeListener implements ChangeListener{
		
		@Override
		public void stateChanged(ChangeEvent arg0) {
			panel[3].removeAll();
			
			panel[3].setLayout(new GridLayout( 1+slider.getValue() , 2));
			panel[3].add(XtextFieldLabel);
			panel[3].add(YtextFieldLabel);
			posXFieldArray.clear();
			posYFieldArray.clear();
			if(regular==true) {
				for(int i=0; i<slider.getValue(); i++) {
				
					posX.add(i, (int) (R*Math.cos( (Math.PI/2 + 2*Math.PI*i)/slider.getValue() )) );
					posY.add(i, (int) (R*Math.sin( (Math.PI/2 + 2*Math.PI*i)/slider.getValue() )) );
					posXFieldArray.add(new JTextField(Integer.toString(posX.get(i))));
					posYFieldArray.add(new JTextField(Integer.toString(posY.get(i))));
				
					panel[3].add(posXFieldArray.get(i) );
					panel[3].add(posYFieldArray.get(i) );
					
				
					//System.out.printf("X %d  Y %d\n", posX.get(i), posY.get(i));
					//System.out.println(posXFieldArray.get(i).getText());									
				}
			}
			else {
				for(int i=0; i<slider.getValue(); i++) {
					
					posX.add(i, (int) (1.3*R*Math.cos(2*Math.PI*Math.random())) );
					posY.add(i, (int) (1.3*R*Math.sin(2*Math.PI*Math.random())) );
					posXFieldArray.add(new JTextField(Integer.toString(posX.get(i))));
					posYFieldArray.add(new JTextField(Integer.toString(posY.get(i))));
				
					panel[3].add(posXFieldArray.get(i) );
					panel[3].add(posYFieldArray.get(i) );
					
				
					//System.out.printf("X %d  Y %d\n", posX.get(i), posY.get(i));
					//System.out.println(posXFieldArray.get(i).getText());									
				}
			}
			panel[3].repaint();
			panel[3].updateUI();
			canv.repaint();
			
		}
		
	}
//====================================================================================	
//====================================================================================
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		 if (arg0.getSource()==colorBGChooser) {
			color1 = JColorChooser.showDialog(this, "Select a color", Color.black);
			canv.setBackground(color1);
		 }
		 else if(arg0.getSource()==colorLNChooser) {
			 color2 = JColorChooser.showDialog(this, "Select a color", Color.black);
		 }
		 else if(arg0.getSource()==menuItem1px) {
			 linewidth=1;
		 }
		 else if(arg0.getSource()==menuItem5px) {
			 linewidth=5;
		 }
		 else if(arg0.getSource()==menuItem10px) {
			 linewidth=10;
		 }
		 else if(arg0.getActionCommand()== "0")
			 regular = true;
		 else if(arg0.getActionCommand()== "1")
			 regular = false;
		 else if(arg0.getSource() == menuCredit) {
			 JFrame credit = new JFrame();
			 credit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			 credit.setSize(WIDTH/3,HEIGHT/3);
			 JLabel author = new JLabel("Project created by Krzysztof Palmi");
			 credit.add(author);
			 credit.setVisible(true);
		 }
		 canv.repaint();
		
	}
//====================================================================================	
//====================================================================================	

	public static void main(String[] args) {
		PolygonDrawer frame = new PolygonDrawer();
		frame.setVisible(true);

	}
	
}
