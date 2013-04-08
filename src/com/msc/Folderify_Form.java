package com.msc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.control.Multiple_Files_Operations;

public class Folderify_Form extends JFrame {

	ArrayList<File> files = new ArrayList<File>();
	File path;
	ImageIcon byalbum = (new ImageIcon(getClass().getResource("/img1.png")));
	ImageIcon byartist = (new ImageIcon(getClass().getResource("/img3.png")));
	ImageIcon byartistalbum = (new ImageIcon(getClass().getResource("/img2.png")));
	//ImageIcon byalbum = new ImageIcon("images/img1.png");
	//ImageIcon byartist = new ImageIcon("images/img3.png");
	//ImageIcon byartistalbum = new ImageIcon("images/img2.png");
	String[] values = {"By Artist","By Album", "By Artist and Album"};
	String type = "byartist";
	Folderify_Form frame;
	
	JPanel mainPanel;
	
	// components - wait
	JLabel lblPleaseWait;
	JProgressBar progressBar;
	// components - selection
	JLabel iconlabel;
	JLabel lblFolderify;
	JComboBox comboBox;
	JButton btnConfirm;
	
	
	public void runFolderify(){
				try {
					frame = new Folderify_Form(files, path);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
	}

	public Folderify_Form(ArrayList<File> f, File p) {
		this.files = f;
		this.path = p;
		setBounds(100, 100, 298, 342);
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		
		lblPleaseWait = new JLabel("Please Wait...");
		lblPleaseWait.setBounds(108, 125, 80, 16);
		mainPanel.add(lblPleaseWait);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(40, 153, 219, 20);
		mainPanel.add(progressBar);
		
		
		iconlabel = new JLabel();
		iconlabel.setBounds(17, 35, 260, 240);
		iconlabel.setIcon(byartist);
		
		lblFolderify = new JLabel("Folderify");
		lblFolderify.setBounds(17, 8, 61, 16);
		mainPanel.add(lblFolderify);
		
		comboBox = new JComboBox(values);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				String str = (String)comboBox.getSelectedItem();
				if(str.equals(values[0])){
					iconlabel.setIcon(byartist);
					type = "byartist";
				}
				else if(str.equals(values[1])){
					iconlabel.setIcon(byalbum);
					type = "byalbum";
				}
				else if(str.equals(values[2])){
					iconlabel.setIcon(byartistalbum);
					type = "byartistalbum";
				}
			}
		});
		comboBox.setBounds(78, 4, 199, 27);
		mainPanel.add(comboBox);
		mainPanel.add(iconlabel);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setToWait(); // if no ???
				Multiple_Files_Operations.Folderify(path, type, true);
			}
		});
		btnConfirm.setBounds(17, 281, 260, 29);
		mainPanel.add(btnConfirm);
		
		mainPanel.setVisible(true);
		setToSelection();
		
	}
	
	public void setToWait(){
		System.out.println("settowait");

		lblPleaseWait.setVisible(true);
		progressBar.setVisible(true);
		iconlabel.setVisible(false);
		lblFolderify.setVisible(false);
		comboBox.setVisible(false);
		btnConfirm.setVisible(false);
	}
	public void setToSelection(){
		System.out.println("settoselection");

		lblPleaseWait.setVisible(false);
		progressBar.setVisible(false);
		iconlabel.setVisible(true);
		lblFolderify.setVisible(true);
		comboBox.setVisible(true);
		btnConfirm.setVisible(true);
	}
	public void closeThis(){
		frame.dispose();
	}
	
}
