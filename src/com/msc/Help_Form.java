package com.msc;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Help_Form extends JFrame {

	private JPanel contentPane;
	public void runHelp(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help_Form frame = new Help_Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Help_Form() {
		
		ImageIcon icon = (new ImageIcon(getClass().getResource("/help.png")));
		//ImageIcon icon = new ImageIcon("images/help.png");
		JLabel bg = new JLabel(icon);
		bg.setBounds(0, 0, 450, 300);
		
		setBounds(500, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.add(bg);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

	}
}
