package audioPlayerPackage;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * based on example from  www.codejava.net
 */

public class ExampleAudioPlayer extends JFrame implements LineListener,ActionListener {
     
	private static final long serialVersionUID = 1L;
	String audioFilePath = "";
	Clip audioClip = null;
	File audioFile = null;
	AudioInputStream audioStream = null;
	JFileChooser fileChooser;
	JButton playPauseBtn, stopBtn;
	boolean fileSelected = false;
    boolean playCompleted = false;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    Timer timer;
    boolean isPlay=true;
    long clipTime;
	
    ExampleAudioPlayer(){
    	setSize(600,400);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setVisible(true);
    	playPauseBtn = new JButton("Play/pause");
    	playPauseBtn.addActionListener(this);
    	stopBtn = new JButton("Stop");
    	stopBtn.addActionListener(this);
    	
    	this.setLayout(new GridLayout(1,2));
    	
    	menuBar = new JMenuBar();
    	menu = new JMenu("Sound");
    	menuItem = new JMenuItem("Choose a file");
    	menuItem.addActionListener(this);
    	menu.add(menuItem);
    	menuBar.add(menu);
    	
    	fileChooser = new JFileChooser();
    	fileChooser.setCurrentDirectory(new File("./resources/"));
    	
    	this.setJMenuBar(menuBar);
    	this.add(playPauseBtn);
    	this.add(stopBtn);
    	
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == menuItem) {
			fileChooser.showOpenDialog(this);
			if(fileChooser.getSelectedFile().exists() == true)
				fileSelected = true;
		}
		else if(e.getSource() == stopBtn) {
			audioClip.close();
		}
		else if(e.getSource() == playPauseBtn) {
			isPlay= !isPlay;
			clipTime = audioClip.getMicrosecondPosition();
		}
		
	}
  
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    void play(String audioFilePath) {
    	
        try {
        	String audioFilePathLocal = audioFilePath;
            audioFile = new File(audioFilePathLocal);
            audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
                      
            /**
             *  Play the audio clip in a new thread not to block the GUI.
             *  It helps in this case, but is not really necessary. 
             */
            Thread thread = new Thread(new Runnable() {

                public void run() {
                	while(true) {
                		try {
							Thread.sleep(200);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
                		if(fileSelected == true) {
                			audioClip.start();
                			while(!playCompleted){          	
         	            	    if(isPlay == true) {
         	            	    	//System.out.println(isPlay);
         	            	    }
         	            	    else {
         	            	    	try {
										Thread.sleep(100);
										audioClip.stop();
										//System.out.println(isPlay);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
         	            	    }
                			}
                			audioClip.close();
                			fileSelected = false;
                			try {
                				audioStream.close();
                			} catch (IOException e) {
     				            e.printStackTrace();
                			}
                		}
                	}
                }             
            });
            thread.start();

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error playing the audio file.");
			e1.printStackTrace();
		}       
    }
     
    /**
     * Listens to the START and STOP events of the audio line.
     */
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
            System.out.println("Playback started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Playback completed.");
        }
 
    }
 
    public static void main(String[] args) {
    	
    	final String inFileName = "./resources/1-welcome.wav";
    	
    	 SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
		    	ExampleAudioPlayer p = new ExampleAudioPlayer();
		    	p.play(inFileName);
		    	
			}
		});
    }



 
}

