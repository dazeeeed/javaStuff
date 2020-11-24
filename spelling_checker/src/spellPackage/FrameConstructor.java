package spellPackage;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class FrameConstructor extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH=600, HEIGHT=600;
	private JMenuBar menuBar;
	private JMenu fileOptions, textOptions;
	private JMenuItem menuLoad, menuSave;
	private JCheckBoxMenuItem menuBold, menuCursive; 
	private JButton check;
	JEditorPane editorPane;
	//JScrollPane editorScrollPane;
	JFileChooser fileChooser;
	FileNameExtensionFilter extensionFilter;
	InputStreamReader isr;
	OutputStreamWriter osw;
	char[] charArray;
	String fileContent, fileContentReplaced, fileContentWithoutSpaces;
	private int someUselessInt=110;
	boolean wasEdited=false;
	String temp;
	
	String[] lettersToChange = {"ó", "u", "U", "¿", "¯", "rz", "Rz", "ch", "Ch", "h", "H"};
	
	
	
	public FrameConstructor() throws HeadlessException{
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setLocation( (int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-this.getWidth()) /2), 
				(int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-this.getHeight()) /2);
		
		menuBar = new JMenuBar();
		fileOptions = new JMenu("FILE");
		textOptions = new JMenu("TEXT");
		menuLoad = new JMenuItem("Load");
		menuLoad.addActionListener(this);
		menuSave = new JMenuItem("Save - bez polskich znaków");
		menuSave.addActionListener(this);
		menuBold = new JCheckBoxMenuItem("Bold");
		menuBold.addActionListener(this);
		menuCursive = new JCheckBoxMenuItem("Cursive");
		menuCursive.addActionListener(this);
		
		menuBar.add(fileOptions);
		menuBar.add(textOptions);
		fileOptions.add(menuLoad);
		fileOptions.add(menuSave);
		textOptions.add(menuBold);
		textOptions.add(menuCursive);		
		this.setJMenuBar(menuBar);
		
		//---JEditorPane--------------------------------------------------------------------------------------
		editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		//editorScrollPane = new JScrollPane();
		//editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		extensionFilter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setFileFilter(extensionFilter);

		this.add(editorPane, BorderLayout.CENTER);
		//---JButton------------------------------------------------------------------------------------------
		check = new JButton("CHECK");
		check.addActionListener(this);
		this.add(check, BorderLayout.PAGE_END);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menuLoad) {
			fileChooser.showOpenDialog(this);
			try {
				isr = new InputStreamReader( new FileInputStream(fileChooser.getSelectedFile().getName()), Charset.forName("UTF-8").newDecoder() );
				charArray = new char[(int) fileChooser.getSelectedFile().length()];
				isr.read( charArray );
				fileContent = new String(charArray);
				fileContentReplaced = fileContent;
				
				for(int i=0; i< lettersToChange.length; i++) {
					fileContentReplaced = fileContentReplaced.replace(lettersToChange[i], "?");
				}
				editorPane.setText( fileContentReplaced );
				isr.close();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				System.out.println("coœ nie pyk³o");
				//e1.printStackTrace();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("coœ nie pyk³o");
				e1.printStackTrace();
				
			}
		}
		else if(e.getSource() == menuSave) {
			fileChooser.showOpenDialog(this); //TODO saving for normal (polish) characters also
			try {
				osw = new OutputStreamWriter( new FileOutputStream(fileChooser.getSelectedFile().getName()), Charset.forName("UTF-8").newEncoder() );
				if(wasEdited == false) {
					osw.write( editorPane.getText(0, editorPane.getText().length()-110) );
				}
				else {
					osw.write( editorPane.getText(0, editorPane.getText().length()+1-someUselessInt) );
				}
				osw.close();
				
			} catch (BadLocationException | IOException e1) {
				System.out.println("coœ nie pyk³o");
				//e1.printStackTrace();
			}
			
			
		}
		else if(e.getSource() == menuBold ) {
			if(menuBold.getState() == true) { //bold on
				if(menuCursive.getState() == true) { //bold on, italic on
					editorPane.setText("<html><b><i>"+ editorPane.getText() +"</i></b></html>");
					someUselessInt=129;
				}
				else { //bold on, italic off
					editorPane.setText("<html><b>"+ editorPane.getText() +"</b></html>");
					editorPane.setText(editorPane.getText().replaceAll("<i>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</i>", ""));
					someUselessInt=122;
				}
			}
			else { //bold off
				if(menuCursive.getState() == true) { //bold off, italic on
					editorPane.setText("<html><i>"+ editorPane.getText() +"</i></html>");
					editorPane.setText(editorPane.getText().replaceAll("<b>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</b>", ""));
					someUselessInt=122;
				}
				else { //italic off, bold off
					editorPane.setText(editorPane.getText().replaceAll("<b>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</b>", ""));
					editorPane.setText(editorPane.getText().replaceAll("<i>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</i>", ""));
					someUselessInt=115;
				}
			}
			wasEdited = true;
		}
		else if(e.getSource() == menuCursive) {
			if(menuCursive.getState() == true) { //italic on
				if(menuBold.getState() == true) { //italic on, bold on
					editorPane.setText("<html><b><i>"+ editorPane.getText() +"</i></b></html>");
					someUselessInt=129;
				}
				else { //italic on, bold off
					editorPane.setText("<html><i>"+ editorPane.getText() +"</i></html>");
					editorPane.setText(editorPane.getText().replaceAll("<b>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</b>", ""));
					someUselessInt=122;
				}
			}
			else { //italic off
				if(menuBold.getState() == true) { //italic off, bold on
					editorPane.setText("<html><b>"+ editorPane.getText() +"</b></html>");
					editorPane.setText(editorPane.getText().replaceAll("<i>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</i>", ""));
					someUselessInt=122;
				}
				else { //italic off, bold off
					editorPane.setText(editorPane.getText().replaceAll("<i>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</i>", ""));
					editorPane.setText(editorPane.getText().replaceAll("<b>", ""));
					editorPane.setText(editorPane.getText().replaceAll("</b>", ""));
					someUselessInt=115;
				}
			}
			wasEdited=true;
		}
		else if(e.getSource() == check) {
			try {
				if(wasEdited == false) {
					temp = editorPane.getText();
					temp = temp.replace("<html>", "");
					temp = temp.replace("</html>", "");
					temp = temp.replace("<head>", "");
					temp = temp.replace("</head>", "");
					temp = temp.replace("<body>", "");
					temp = temp.replace("</body>", "");
					temp = temp.replace("<p style=\"margin-top: 0\">", "");
					temp = temp.replace("</p>", "");
					temp = temp.replace("\n", "");
					temp = temp.replace(" ", "");
					fileContentWithoutSpaces = fileContent;
					fileContentWithoutSpaces = fileContentWithoutSpaces.replace(" ", "");
					if( fileContentWithoutSpaces.equalsIgnoreCase(temp) == false )
						System.out.println("Nie sa takie same");
					else {
						JOptionPane.showMessageDialog(this, "Brawo! Wszystko jest poprawnie!");
					}		
				}
				else {
					System.out.println( editorPane.getText(0, editorPane.getText().length()+1-someUselessInt) );
				}
			}
			catch (BadLocationException e1) {
				System.out.println("coœ nie pyk³o");
				//e1.printStackTrace();
			}
		}
	}
}
