package screensaverPackage;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainClass {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JFrame f = new JFrame("Prostokaty");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				PanelRysowania panel = new PanelRysowania();
				
				panel.dodajProstokat(100, 100, 80, 160, Color.BLUE);
				
				for (int i = 1; i<20 ; i++) panel.dodajLosowyProstokat();
				
				f.add(panel);
				f.setSize(600, 600);
				f.setVisible(true);
				
			}
		});

	}

}

