package timerPackage;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Zad1 extends JFrame {

	private static final long serialVersionUID = 1L;
	JButton button = new JButton("T³o pod napisem oraz tytu³ zacznie siê zmieniaæ przez 5 sekund");
	JProgressBar progressBar = new JProgressBar(0,20);
	JLabel etykieta = new JLabel("Napis");
	String title;
	int i=0;
	
	Timer timer;
	
	public Zad1() throws HeadlessException {
		this.setSize(400,400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));		
		progressBar.setValue(0);
		etykieta.setOpaque(true);
		title= "Title";
		this.add(button);
		this.add(progressBar);
		this.add(etykieta);
		this.setTitle(title);
		
		timer = new Timer(1000,null);
		timer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				i++;
				progressBar.setValue(i);
				if(i==20)
					System.exit(0);
			}
		});
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				SwingWorker<String,Integer> worker = new SwingWorker<String, Integer>(){
					@Override
	            	protected void process(List<Integer> dane) {
						for(Integer some_int : dane) {
							etykieta.setBackground( new Color(some_int, some_int % 100, (some_int % 23 *10)) );
							title="New "+title;
							setTitle(title);
							etykieta.setForeground( new Color((some_int % 13) * 20, some_int % 100, (some_int % 30)* 5) );
						}
	            	}
					
					
					@Override
					protected String doInBackground() throws Exception {
						for(int j=0; j<10; j++) {
							Thread.sleep(500);
							publish( (int) (Math.random()*250) );
						}
						
						return "Ukoñczono";
					}
					@Override
					protected void done() {
						try {
							etykieta.setText(get());
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					
					
				};
				worker.execute();
				
			}
			
		});
		
		
		
		
		
		timer.start();
		
		
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Zad1 frame = new Zad1();
				frame.setVisible(true);
			}
		});

	}

}
