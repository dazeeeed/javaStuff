package lab8zad1;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartConstructor extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH=600, HEIGHT=600;
	private JMenuBar menuBar;
	private JMenu menuData;
	private JMenuItem menuLoad;
	private JFileChooser fileChooser;
	private FileNameExtensionFilter extensionFilter;
	InputStreamReader isr;
	String[] strings; 
	String line;
	StringTokenizer st;
	ArrayList<Double> data;
	String[] str;
	XYSeries dataSet1, dataSet2;
	XYSeriesCollection xySeriesCollection;
	XYDataset xyDataset;
	XYSeries[] dataSet;
	JFreeChart lineGraph;
	ChartFrame frame;
	ChartPanel chartPanel;
	private Timer timer;
	boolean sthChanged = false;
	
	public ChartConstructor() throws HeadlessException{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation( (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-this.getWidth()) /2), 
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-this.getHeight()) /2);
		
		//---MENU---
		menuBar = new JMenuBar();
		menuData = new JMenu("Data");
		menuLoad = new JMenuItem("Load from txt");
		menuLoad.addActionListener(this);
		menuData.add(menuLoad);
		menuBar.add(menuData);
		this.setJMenuBar(menuBar);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		extensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setFileFilter(extensionFilter);
		
		dataSet1= new XYSeries("seria 1");
		dataSet2= new XYSeries("seria 2");
			    
		dataSet1.add(0.1, 4.4); 
		dataSet1.add(0.5, 12.4); 
		dataSet2.add(0.1, 5.5); 
		dataSet2.add(0.5, 7.6);

		xySeriesCollection = new XYSeriesCollection(dataSet1);
		xySeriesCollection.addSeries(dataSet2);

		xyDataset = xySeriesCollection;

		lineGraph = ChartFactory.createXYLineChart(
			"Tytul wykresu",  			    // Title 
			"Tytul osi X",              	// X-Axis label 
			"Tytul osi Y",         		    // Y-Axis label 
			xyDataset,                      // Dataset 
			PlotOrientation.VERTICAL,       //Plot orientation 
			true,                           //show legend 
			true,                           // Show tooltips 
			false                           //url show 
			); 

		frame = new ChartFrame("", lineGraph);
		frame.pack(); 
		frame.setVisible(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		chartPanel = new ChartPanel(lineGraph);
		this.add(chartPanel);
		this.setVisible(true);       		        
		sthChanged = false;
		timer = new Timer(100,null);
		timer.start();
		timer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(sthChanged == true) {
					returnConstructor().remove(chartPanel);

					frame = new ChartFrame("", lineGraph);
					frame.pack(); 
					frame.setVisible(false);

					chartPanel = new ChartPanel(lineGraph);
					returnConstructor().getContentPane().add(chartPanel);
					returnConstructor().revalidate();
					sthChanged = false;
				}
			}
		});	
	}
	public ChartConstructor returnConstructor() {
		return this;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menuLoad) {
			BufferedReader br;
			fileChooser.showOpenDialog(this);
			data = new ArrayList<Double>();
			try {
				br = new BufferedReader(new FileReader(fileChooser.getSelectedFile().getName()),
						(int) fileChooser.getSelectedFile().length());	//Creates buffered reader with size of file
				line = br.readLine();
				st = new StringTokenizer(line);
				strings = new String[st.countTokens()];
				for(int i=0; i< strings.length; i++) {		//String Labels work for sure
					strings[i] = st.nextToken();
				}
				
				str = new String[strings.length];
				while((line = br.readLine()) != null) {
					//System.out.println(line);
					str = line.split("\t");
					for(int i=0; i<strings.length; i++) {
						data.add(Double.parseDouble(str[i]) );
						//System.out.printf("%s, ", str[i]);				//TODO take care of situation where there is no data - fill with 0?
					}
					//System.out.println();
					
				}
				br.close();
				
			} catch (IOException e1) {
				//System.exit(1);
				e1.fillInStackTrace();
			} 
			
			dataSet = new XYSeries[strings.length];
			for(int i=0; i<strings.length-1; i++) {
				dataSet[i] = new XYSeries(strings[i+1]);
				
				for(int j=0; j<data.size(); j=j+strings.length) {
					dataSet[i].add(data.get(j), data.get(j+i+1));
				}
			}
									
			xySeriesCollection = new XYSeriesCollection(dataSet[0]);
			for(int i=1; i<strings.length-1; i++) {
				xySeriesCollection.addSeries(dataSet[i]);
			}
			xyDataset = xySeriesCollection;
			lineGraph = ChartFactory.createXYLineChart(
					"Chart",  						// Title 
					strings[0],              		// X-Axis label 
					"",         		   			// Y-Axis label 
					xyDataset,                      // Dataset 
					PlotOrientation.VERTICAL,       //Plot orientation 
					true,                           //show legend 
					true,                           // Show tooltips 
					false                           //url show 
					);
			sthChanged = true;
		}
		
	}

}
