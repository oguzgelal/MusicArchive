package com.msc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
import javax.swing.border.EmptyBorder;

import com.control.Multiple_Files_Operations;

public class Approval_Form extends JFrame {

	private JPanel contentPane;
	private JList list;
	private JScrollPane scroll;
	private JButton btnApprove;
	private JButton btnCancel;
	private ArrayList<File> newFiles = new ArrayList<File>();
	private ArrayList<File> oldFiles = new ArrayList<File>();
	ArrayList<String> files_readable = new ArrayList<String>();
	public void runApproval(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Approval_Form frame = new Approval_Form(newFiles, oldFiles);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Approval_Form(final ArrayList<File> newFiles, final ArrayList<File> oldFiles) {
		setResizable(false);
		this.newFiles = newFiles;
		this.oldFiles = oldFiles;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list = new JList();
		list.setBounds(6, 6, 488, 336);
		contentPane.add(list);
		scroll = new JScrollPane(list);
		contentPane.add(scroll);
		scroll.setViewportView(list);
		scroll.setBounds(6, 6, 488, 336);
		
		JLabel listNote = new JLabel("The changes you made will look like this");
		listNote.setHorizontalAlignment(SwingConstants.CENTER);
		listNote.setOpaque(true);
		listNote.setBackground(UIManager.getColor("EditorPane.selectionBackground"));
		scroll.setColumnHeaderView(listNote);
		
		files_readable = Multiple_Files_Operations.generateReadableNames(newFiles);
		list.setListData(files_readable.toArray());
		
		btnApprove = new JButton("Apply");
		btnApprove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Multiple_Files_Operations.approveChange(newFiles, oldFiles);
				Music_Form.files = newFiles;
				Music_Form.refreshList();
				dispose();
			}
		});
		btnApprove.setBounds(0, 343, 250, 29);
		contentPane.add(btnApprove);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(250, 343, 250, 29);
		contentPane.add(btnCancel);
	}
}
