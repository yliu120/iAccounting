package edu.jhu.cs.cs475.finalproject.UI;

import java.io.IOException;

import javax.swing.JFrame;

public class UIMain {

	private static DetectorUI gui;
	public static void main (String[] args) throws IOException {
        gui = new DetectorUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

