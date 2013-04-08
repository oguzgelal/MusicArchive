package com.msc;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class About_Form extends JFrame {

	private JPanel contentPane;
	public void runAbout(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					About_Form frame = new About_Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public About_Form() {
		
		ImageIcon icon = (new ImageIcon(getClass().getResource("/about.png")));
		//ImageIcon icon = new ImageIcon("images/about.png");
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
