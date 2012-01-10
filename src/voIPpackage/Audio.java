package voIPpackage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;

public class Audio extends JFrame implements ActionListener{
	
	public JButton b1, b2;
	public JLabel textlbl1;
	public String IP = "192.168.40.100";
	public int portsend = 10100, portreceive = 40500;
	
	public Audio() {
		Dimension btnsize = new Dimension(100, 30);
		b1 = new JButton("TRASNMIT");
	    b1.addActionListener(this);
	    b1.setMaximumSize(btnsize);
	    b1.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    
	    b2 = new JButton("RECIEVE");
	    b2.addActionListener(this);
	    b2.setMaximumSize(btnsize);
	    b2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    JPanel p1 = new JPanel();
	    // Set a top-down box-layout on the container
	    p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
	    // Create a small filler
	    p1.add(Box.createRigidArea(new Dimension(110, 5)));
	    // Add first button
	    p1.add(b1);
	    // Create a small filler
	    p1.add(Box.createRigidArea(new Dimension(110, 5)));
	    // Add second button
	    p1.add(b2);
	    p1.add(Box.createRigidArea(new Dimension(110, 5)));
	    getContentPane().add(p1, BorderLayout.LINE_END);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1) {
			Thread transmit = new Transmitter(true,portsend,IP); 
//			Transmitter.remoteIP = IP;
//			Transmitter.Portremotehost = port;
			transmit.start();
			}
		if(e.getSource()==b2) {
			Thread recieve = new Receiver(true,portreceive);
			recieve.start();
		}
		}
		
	
	
	public static void main(String[] args) {
		Audio simpleyo = new Audio();
	    
	    simpleyo.setTitle("SIMPLE");
	    simpleyo.setSize(500, 200);
	    simpleyo.setVisible(true);
	}
	
	public static AudioFormat getFormat() {
		float sampleRate = 8000;
		int sampleSizeInBits = 8;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

}
