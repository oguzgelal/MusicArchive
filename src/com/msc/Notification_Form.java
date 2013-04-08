package com.msc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.control.Multiple_Files_Operations;

public class Notification_Form extends JFrame {

	private JPanel contentPane;
	private JList list;
	private JScrollPane scroll;
	private JButton btnApprove;
	private ArrayList<File> files = new ArrayList<File>();
	private String note;
	ArrayList<String> files_readable = new ArrayList<String>();
	public void runNotification(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Notification_Form frame = new Notification_Form(files,note);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Notification_Form (ArrayList<File> files, String note) {
		setResizable(false);
		this.files = files;
		this.note = note;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList();
		list.setBounds(6, 6, 488, 336);
		contentPane.add(list);
		scroll = new JScrollPane(list);
		contentPane.add(scroll);
		scroll.setViewportView(list);
		scroll.setBounds(6, 6, 488, 336);
		
		JLabel listNote = new JLabel(note);
		listNote.setHorizontalAlignment(SwingConstants.CENTER);
		listNote.setOpaque(true);
		listNote.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		scroll.setColumnHeaderView(listNote);
		
		files_readable = Multiple_Files_Operations.generateReadableNames(files);
		list.setListData(files_readable.toArray());
		
		btnApprove = new JButton("Ok");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnApprove.setBounds(0, 343, 500, 29);
		contentPane.add(btnApprove);
	}
}
