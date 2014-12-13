package edu.jhu.cs.cs475.finalproject.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DetectorUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea textArea; 
	private JTextArea intro;
	private JTextArea message;
	private JButton enter;
	private JButton detect;
	private String imageName;
	private JLabel pic;
	private ImageIcon picture;
	private JPanel picturePanel;
	private JPanel bottomPanel;
	private JPanel rightPanel;
	
	public DetectorUI(){
		this.setTitle("Detector");
		
		textArea = new JTextArea(1,50);
		textArea.setText("");
		textArea.setEditable(true);
		
		
		intro = new JTextArea(1,50);
		intro.setText("Enter your file name:");
		intro.setEditable(false);
		intro.setBackground(Color.LIGHT_GRAY);
		
		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension(80,5));
		detect = new JButton("Detect");

		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(200,500));
		rightPanel.add(intro,BorderLayout.NORTH);
		rightPanel.add(textArea, BorderLayout.CENTER);
		rightPanel.add(enter,BorderLayout.EAST);
		rightPanel.add(detect,BorderLayout.SOUTH);
		
		picturePanel = new JPanel(new BorderLayout());
		picturePanel.setPreferredSize(new Dimension(700,500));
		
		
		
		
		enter.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				imageName = getText();
				load(imageName);
				picture = new ImageIcon(load(imageName).getScaledInstance(640,480,Image.SCALE_SMOOTH));
				pic = new JLabel(picture);
				picturePanel.add(pic);
			}
		});
		
		
		
		
		bottomPanel = new JPanel();
		//message = new JTextArea(1,900);
		message = new JTextArea();
		message.setText("Welcome to our final project!");
		message.setVisible(true);
		message.setEditable(false);
		bottomPanel.add(message);
		
		
		this.getContentPane().add(BorderLayout.CENTER, picturePanel);
		this.getContentPane().add(BorderLayout.EAST, rightPanel);
		this.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
		this.setSize(900,500);
		this.setVisible(true);
	}
	
	
	public String getText(){
		return textArea.getText();
	}
	
	private BufferedImage load(String imgStr) {
		BufferedImage img;
		img = this.loadImage("image" + imgStr + ".png");
		return img;
	}
	
	private BufferedImage loadImage(String filename) {
		try {
			return ImageIO.read(new File(filename));
		} catch (IOException e) {
			message.setText("Image: " + filename
					+ " not found.");
			return null;
		}
	}
}
