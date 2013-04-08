package com.msc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.control.Single_File_Operations;

public class SingleFile_Form extends JFrame {

	private JPanel contentPane;
	static File file;
	private JLabel title_txt;
	private JLabel artist_txt;
	private JLabel album_txt;
	private JLabel filename_txt;
	private JTextField pathtxt;
	private JLabel lblPath;
	private JButton btnDeleteFile;
	private JButton btnRenameFile;
	private JTextField textField;

	
	static SingleFile_Form frame;
	public static void runSingleFile() {
		try {
			frame = new SingleFile_Form(file);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SingleFile_Form(File f) {
		file = f;
		setResizable(false);
		setBounds(500, 100, 350, 311);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblFile = new JLabel("File  ");
		lblFile.setForeground(Color.GREEN);
		lblFile.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblFile.setBounds(20, 15, 324, 16);
		contentPane.add(lblFile);

		//filename info
		JLabel filename_lbl = new JLabel("File Name : ");
		filename_lbl.setForeground(Color.GREEN);
		filename_lbl.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		filename_lbl.setBounds(20, 49, 77, 16);
		contentPane.add(filename_lbl);
		filename_txt = new JLabel();
		filename_txt.setForeground(Color.GREEN);
		filename_txt.setText(Single_File_Operations.seperateNameAndExtension(file)[0]);
		filename_txt.setBounds(106, 45, 222, 28);
		contentPane.add(filename_txt);

		//title info
		JLabel title_lbl = new JLabel("Title : ");
		title_lbl.setForeground(Color.GREEN);
		title_lbl.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		title_lbl.setBounds(20, 85, 61, 16);
		contentPane.add(title_lbl);
		title_txt = new JLabel();
		title_txt.setForeground(Color.GREEN);
		title_txt.setText(Single_File_Operations.getTitle(file));
		title_txt.setBounds(106, 81, 222, 28);
		contentPane.add(title_txt);

		//artist info
		JLabel artist_lbl = new JLabel("Artist : ");
		artist_lbl.setForeground(Color.GREEN);
		artist_lbl.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		artist_lbl.setBounds(20, 121, 61, 16);
		contentPane.add(artist_lbl);
		artist_txt = new JLabel();
		artist_txt.setForeground(Color.GREEN);
		artist_txt.setText(Single_File_Operations.getArtist(file));
		artist_txt.setBounds(106, 117, 222, 28);
		contentPane.add(artist_txt);

		//album info
		JLabel album_lbl = new JLabel("Album : ");
		album_lbl.setForeground(Color.GREEN);
		album_lbl.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		album_lbl.setBounds(20, 157, 61, 16);
		contentPane.add(album_lbl);
		album_txt = new JLabel();
		album_txt.setForeground(Color.GREEN);
		album_txt.setText(Single_File_Operations.getAlbum(file));
		album_txt.setBounds(106, 152, 222, 28);
		contentPane.add(album_txt);

		pathtxt = new JTextField();
		pathtxt.setBackground(Color.BLACK);
		pathtxt.setForeground(Color.GREEN);
		pathtxt.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		pathtxt.setText(file.toString());
		pathtxt.setBounds(60, 185, 193, 28);
		contentPane.add(pathtxt);
		pathtxt.setColumns(10);

		lblPath = new JLabel("Path : ");
		lblPath.setForeground(Color.GREEN);
		lblPath.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblPath.setBounds(20, 191, 41, 16);
		contentPane.add(lblPath);

		ImageIcon btn11 = (new ImageIcon(getClass().getResource("/btn11.png")));
		btnDeleteFile = new JButton(btn11);
		btnDeleteFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file.delete();
				ArrayList<File> files = Music_Form.files;
				files.remove(file);
				Music_Form.files = files;
				Music_Form.refreshList();
				frame.dispose();
			}
		});
		btnDeleteFile.setBounds(80, 250, 198, 30);
		contentPane.add(btnDeleteFile);

		ImageIcon btn10 = (new ImageIcon(getClass().getResource("/btn10.png")));
		btnRenameFile = new JButton(btn10);
		btnRenameFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<File> files = Music_Form.files;
				files.remove(file);
				try{
				file.renameTo(new File(Single_File_Operations.seperatePath(file)+"/"+textField.getText()+"."+Single_File_Operations.seperateNameAndExtension(file)[1]));
				files.add(new File(Single_File_Operations.seperatePath(file)+"/"+textField.getText()+"."+Single_File_Operations.seperateNameAndExtension(file)[1]));
				}
				catch(Exception ee){
				}
				Music_Form.files = files;
				Music_Form.refreshList();
				frame.dispose();
			}
		});
		btnRenameFile.setBounds(193, 214, 154, 29);
		contentPane.add(btnRenameFile);

		textField = new JTextField();
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.GREEN);
		textField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		textField.setBounds(10, 213, 182, 28);
		textField.setText(Single_File_Operations.seperateNameAndExtension(file)[0]);
		contentPane.add(textField);
		textField.setColumns(10);

		ImageIcon btn9 = (new ImageIcon(getClass().getResource("/btn9.png")));
		JButton btnCopy = new JButton(btn9);
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Single_File_Operations.copyToClipboard(pathtxt.getText());
			}
		});
		btnCopy.setBounds(263, 186, 83, 29);
		contentPane.add(btnCopy);

		if(file != null){
			lblFile.setText("File : "+file.getName());
		}
	}
}
