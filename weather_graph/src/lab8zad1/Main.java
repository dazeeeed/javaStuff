package lab8zad1;

import javax.swing.SwingUtilities;

public class Main {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ChartConstructor chart = new ChartConstructor();
				chart.setVisible(true);
			}
		});
		

	}

}
