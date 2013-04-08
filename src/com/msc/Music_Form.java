package com.msc;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.control.Multiple_Files_Operations;

public class Music_Form extends JFrame implements ActionListener {

	private static JPanel contentPane;
	private JPanel activeActionPane;
	private static List list;

	boolean trimBeginning = false;
	boolean includeSubFolders = true;
	String[] values = {"Select Action","Trim Beginning", "Title Fixer", "Folder Operations"}; // combo box values
	String[] values2 = {"combo1","combo2", "combo3", "combo4"}; // combo box values
	public static ArrayList<File> files = new ArrayList<File>();
	static File current_path;
	private JTextField txtBandName;
	private JLabel pathlabel;
	static Folderify_Form folderify;

	public static void main(String[] a…rgs) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Music_Form frame = new Music_Form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setFolderifyToWait(){
		folderify.setToWait();
		System.out.println("content pane wait music");
	}
	public static void closeThis(){
		System.out.println("CLOSE");
		folderify.closeThis();
	}

	public Music_Form() {		
		setResizable(false);	
		setTitle("Music Archive Organiser");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 468);		
		
		ImageIcon icon = (new ImageIcon(getClass().getResource("/bg.jpg")));
		JLabel bg = new JLabel(icon);
		bg.setBounds(0, 0, 388, 468);
		
		// create content pane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setOpaque(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// create select path button
		ImageIcon btn1 = (new ImageIcon(getClass().getResource("/btn1.png")));
		JButton btnSelectPath = new JButton(btn1);
		btnSelectPath.addActionListener(new ActionListener() {// select path action listener
			public void actionPerformed(ActionEvent arg0) {
				// initialize file chooser
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle("choosertitle");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					// selected path
					current_path = chooser.getSelectedFile();
					pathlabel.setText(current_path.toString());
					// set file ArrayLists
					files = setFilesArrayList(files,current_path,includeSubFolders);
					list.addListValue(files);
				}
			}
		});
		btnSelectPath.setBounds(10, 6, 99, 30);
		contentPane.add(btnSelectPath);	

		//createList();
		list = new List(files, contentPane);
		
		
		//Create Action Combobox
		final JComboBox comboBox = new JComboBox(values);
		comboBox.setBackground(Color.BLACK);
		comboBox.setForeground(Color.GREEN);
		comboBox.setRenderer(new DefaultListCellRenderer() {
		    @Override
		    public void paint(Graphics g) {
		        setBackground(Color.BLACK);
		        setForeground(Color.GREEN);
		        super.paint(g);
		    }
		});
		comboBox.setBounds(10, 207, 188, 27);
		contentPane.add(comboBox);

		//Action Panel 0 : (default)
		final JPanel defaultPanel = new JPanel();
		defaultPanel.setBackground(Color.BLACK);
		defaultPanel.setForeground(Color.GREEN);
		defaultPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		defaultPanel.setBounds(17, 246, 354, 183);
		contentPane.add(defaultPanel);//************
		activeActionPane = defaultPanel;
		defaultPanel.setLayout(null);
		JLabel lblIntroductions = new JLabel("Introductions");
		lblIntroductions.setForeground(Color.GREEN);
		lblIntroductions.setBounds(6, 6, 300, 16);
		defaultPanel.add(lblIntroductions);
		JLabel lblSelectThe = new JLabel("1) Select the folder that you want to edit");
		lblSelectThe.setForeground(Color.GREEN);
		lblSelectThe.setBounds(16, 34, 310, 16);
		defaultPanel.add(lblSelectThe);
		JLabel lblYourMusic = new JLabel("- Your music files will be listed in the list");
		lblYourMusic.setForeground(Color.GREEN);
		lblYourMusic.setBounds(26, 55, 311, 16);
		defaultPanel.add(lblYourMusic);
		JLabel lblClickOn = new JLabel("- Click on a single music file to edit its metadata");
		lblClickOn.setForeground(Color.GREEN);
		lblClickOn.setBounds(26, 73, 311, 16);
		defaultPanel.add(lblClickOn);
		JLabel lblYouCan = new JLabel("- You can also edit multiple files from your list");
		lblYouCan.setForeground(Color.GREEN);
		lblYouCan.setBounds(24, 89, 330, 16);
		defaultPanel.add(lblYouCan);
		JLabel lblSelectAction = new JLabel("2) Select Action to edit all the files in the list");
		lblSelectAction.setForeground(Color.GREEN);
		lblSelectAction.setBounds(16, 120, 338, 16);
		defaultPanel.add(lblSelectAction);
		ImageIcon btn4 = (new ImageIcon(getClass().getResource("/btn4.png")));
		JButton btnAbout = new JButton(btn4);
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				About_Form form = new About_Form();
				form.runAbout();
			}
		});
		btnAbout.setBounds(203, 148, 145, 29);
		defaultPanel.add(btnAbout);
		ImageIcon btn3 = (new ImageIcon(getClass().getResource("/btn3.png")));
		JButton btnHelp = new JButton(btn3);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help_Form help = new Help_Form();
				help.runHelp();
			}
		});
		btnHelp.setBounds(6, 148, 145, 29);
		defaultPanel.add(btnHelp);


		//Action Panel 1 : Trim Beginning
		final JPanel trimBeginningPanel = new JPanel();
		trimBeginningPanel.setBackground(Color.BLACK);
		trimBeginningPanel.setForeground(Color.GREEN);
		trimBeginningPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		trimBeginningPanel.setBounds(17, 246, 354, 183);
		contentPane.add(trimBeginningPanel);//************
		trimBeginningPanel.setLayout(null);
		trimBeginningPanel.setVisible(false);
		JLabel lblTrimBeginning = new JLabel("Trim Beginning");
		lblTrimBeginning.setForeground(Color.GREEN);
		lblTrimBeginning.setBounds(6, 6, 95, 16);
		trimBeginningPanel.add(lblTrimBeginning);
		// create and add trim beginning button
		ImageIcon btn5 = (new ImageIcon(getClass().getResource("/btn5.png")));
		JButton btnPrint = new JButton(btn5);
		btnPrint.setBounds(228, 148, 117, 29);
		trimBeginningPanel.add(btnPrint);
		txtBandName = new JTextField();
		txtBandName.setBackground(Color.BLACK);
		txtBandName.setForeground(Color.GREEN);
		txtBandName.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		txtBandName.setBounds(116, 61, 134, 28);
		trimBeginningPanel.add(txtBandName);
		txtBandName.setColumns(10);	
		JLabel lblBandName = new JLabel("Band Name :");
		lblBandName.setForeground(Color.GREEN);
		lblBandName.setBounds(36, 67, 88, 16);
		trimBeginningPanel.add(lblBandName);
		JRadioButton trimBand = new JRadioButton("Trim Band Name (ex. Queen - Bohem...)", false);
		ImageIcon radio1 = (new ImageIcon(getClass().getResource("/radio1.png")));
		trimBand.setIcon(radio1);
		ImageIcon radio2 = (new ImageIcon(getClass().getResource("/radio2.png")));
		trimBand.setSelectedIcon(radio2);
		trimBand.setForeground(Color.GREEN);
		trimBand.setBounds(16, 38, 338, 23);
		trimBand.setName("group");
		trimBeginningPanel.add(trimBand);
		JRadioButton trimNum = new JRadioButton("Trim Album Number (ex. 03 - Bohem...)", true);
		trimNum.setIcon(radio1);
		trimNum.setSelectedIcon(radio2);
		trimNum.setForeground(Color.GREEN);
		trimNum.setBounds(16, 101, 338, 23);
		trimNum.setName("number");
		trimBeginningPanel.add(trimNum);
		//trim beginning radio button properties
		ButtonGroup group = new ButtonGroup();
		ActionListener rb_listener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JRadioButton rbtn = (JRadioButton)e.getSource();
				if (rbtn.getName().equals("group")){
					trimBeginning = true; // group trim
				}
				else if (rbtn.getName().equals("number")){
					trimBeginning = false; // number trim
				}
			}
		};
		group.add(trimBand);
		group.add(trimNum);
		trimBand.addActionListener(rb_listener);
		trimNum.addActionListener(rb_listener);
		// trim beginning button listener
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(trimBeginning){ // group trim
					if(!txtBandName.getText().equals("")){
						if(current_path != null){
							Multiple_Files_Operations.trimBeginnings(files, txtBandName.getText());
						}else{
							JOptionPane.showMessageDialog(null, "Please Select A Path.");
						}
					}else{
						JOptionPane.showMessageDialog(null, "Please Enter Band Name.");
					}
				}else{ // number trim
					if(current_path != null){
						Multiple_Files_Operations.trimBeginnings(files);
					}else{
						JOptionPane.showMessageDialog(null, "Please Select A Path.");
					}
				}
			}
		});


		//Action Panel 2 : Title Fixer
		final JPanel titleFixerPanel = new JPanel();
		titleFixerPanel.setBackground(Color.BLACK);
		titleFixerPanel.setForeground(Color.GREEN);
		titleFixerPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		titleFixerPanel.setBounds(17, 246, 354, 183);
		contentPane.add(titleFixerPanel); //****************
		titleFixerPanel.setLayout(null);
		titleFixerPanel.setVisible(false);

		final JRadioButton r1 = new JRadioButton("Title.mp3");
		r1.setForeground(Color.GREEN);
		r1.setIcon(radio1);
		r1.setSelectedIcon(radio2);
		r1.setBounds(28, 42, 102, 23);
		r1.setActionCommand("title");
		titleFixerPanel.add(r1);
		final JRadioButton r2 = new JRadioButton("Artist - Title.mp3");
		r2.setForeground(Color.GREEN);
		r2.setIcon(radio1);
		r2.setSelectedIcon(radio2);
		r2.setBounds(165, 42, 147, 23);
		r2.setActionCommand("artist-title");
		titleFixerPanel.add(r2);
		final JRadioButton r3 = new JRadioButton("Artist Title.mp3");
		r3.setForeground(Color.GREEN);
		r3.setIcon(radio1);
		r3.setSelectedIcon(radio2);
		r3.setBounds(28, 71, 147, 23);
		r3.setActionCommand("artisttitle");
		titleFixerPanel.add(r3);
		final JRadioButton r4 = new JRadioButton("Title - Artist.mp3");
		r4.setForeground(Color.GREEN);
		r4.setIcon(radio1);
		r4.setSelectedIcon(radio2);
		r4.setBounds(165, 71, 147, 23);
		r4.setActionCommand("title-artist");
		titleFixerPanel.add(r4);
		final JRadioButton r5 = new JRadioButton("Title Artist.mp3");
		r5.setForeground(Color.GREEN);
		r5.setIcon(radio1);
		r5.setSelectedIcon(radio2);
		r5.setBounds(90, 97, 147, 23);
		r5.setActionCommand("titleartist");
		titleFixerPanel.add(r5);
		final ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(r1);
		radioGroup.add(r2);
		radioGroup.add(r3);
		radioGroup.add(r4);
		radioGroup.add(r5);
		JLabel lblChooseTheFormat = new JLabel("Choose the format you want to edit the title.");
		lblChooseTheFormat.setBounds(25, 13, 330, 16);
		titleFixerPanel.add(lblChooseTheFormat);
		ImageIcon btn6 = (new ImageIcon(getClass().getResource("/btn6.png")));
		JButton modifyAll = new JButton(btn6);
		modifyAll.setBounds(57, 132, 223, 30);
		titleFixerPanel.add(modifyAll);
		modifyAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = "";
				if (r1.isSelected()){
					selection = r1.getActionCommand();
				}
				else if (r2.isSelected()){
					selection = r2.getActionCommand();
				}
				else if (r3.isSelected()){
					selection = r3.getActionCommand();
				}
				else if (r4.isSelected()){
					selection = r4.getActionCommand();
				}
				else if (r5.isSelected()){
					selection = r5.getActionCommand();
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select A Format.");
				}
				if (current_path == null){
					JOptionPane.showMessageDialog(null, "Please Select A Path.");
				}
				else{
					Multiple_Files_Operations.editTitle(files, selection);
				}
			}
		});


		//Action Panel 3 : Folder Options
		final JPanel folderOpsPanel = new JPanel();
		folderOpsPanel.setBackground(Color.BLACK);
		folderOpsPanel.setForeground(Color.GREEN);
		folderOpsPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		folderOpsPanel.setBounds(17, 246, 354, 183);
		contentPane.add(folderOpsPanel);//************
		folderOpsPanel.setLayout(null);
		folderOpsPanel.setVisible(false);
		JLabel lblFolderOptions = new JLabel("Folder Options :");
		lblFolderOptions.setForeground(Color.GREEN);
		lblFolderOptions.setBounds(6, 10, 164, 16);
		folderOpsPanel.add(lblFolderOptions);
		ImageIcon btn7 = (new ImageIcon(getClass().getResource("/btn7.png")));
		JButton btnRemoveAllFolders = new JButton(btn7);
		btnRemoveAllFolders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(current_path != null){
					if(includeSubFolders){
						Multiple_Files_Operations.extractFolders(files, current_path, true);
					}
					else{
						JOptionPane.showMessageDialog(null, "Subfolders are not included. Please select \"Include Subfolders\" option.");
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select A Path.");
				}
			}
		});
		btnRemoveAllFolders.setBounds(184, 68, 153, 30);
		folderOpsPanel.add(btnRemoveAllFolders);
		JLabel lblTakesAll = new JLabel("Extracts subfolders into the selected path");
		lblTakesAll.setForeground(Color.GREEN);
		lblTakesAll.setBounds(16, 47, 332, 16);
		folderOpsPanel.add(lblTakesAll);
		JLabel lblFolderify = new JLabel("Create folders by the metadata information");
		lblFolderify.setForeground(Color.GREEN);
		lblFolderify.setBounds(16, 109, 332, 16);
		folderOpsPanel.add(lblFolderify);
		ImageIcon btn8 = (new ImageIcon(getClass().getResource("/btn8.png")));
		JButton btnFolderifyOptions = new JButton(btn8);
		btnFolderifyOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(current_path != null){
					folderify = new Folderify_Form(files, current_path);
					folderify.runFolderify();
				}
				else{
					JOptionPane.showMessageDialog(null, "Please Select A Path.");
				}
			}
		});
		btnFolderifyOptions.setBounds(184, 132, 153, 30);
		folderOpsPanel.add(btnFolderifyOptions);



		JLabel lblAppliesToAll = new JLabel("Applies to all");
		lblAppliesToAll.setForeground(Color.GREEN);
		lblAppliesToAll.setBounds(210, 211, 89, 16);
		contentPane.add(lblAppliesToAll);

		// add refresh button and listener
		ImageIcon btn2 = (new ImageIcon(getClass().getResource("/btn2.png")));
		JButton btnNewButton = new JButton(btn2); // REFRESH !!! ****
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (current_path != null){
				files = Multiple_Files_Operations.searchPath(current_path, includeSubFolders);
				list.refreshList(files);
				}
			}
		});
		btnNewButton.setBounds(300, 206, 72, 30);
		contentPane.add(btnNewButton);

		// add include subfolders checkbox and listener
		final JCheckBox chckbxIncludeSubfolders = new JCheckBox("Include Subfolders");
		chckbxIncludeSubfolders.setForeground(Color.GREEN);
		ImageIcon cb_unchecked = (new ImageIcon(getClass().getResource("/cb_unchecked.png")));
		chckbxIncludeSubfolders.setIcon(cb_unchecked);
		ImageIcon cb_checked = (new ImageIcon(getClass().getResource("/cb_checked.png")));
		chckbxIncludeSubfolders.setSelectedIcon(cb_checked);
		chckbxIncludeSubfolders.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				includeSubFolders = chckbxIncludeSubfolders.isSelected();
				try{
					files = setFilesArrayList(files,current_path,includeSubFolders);
					list.refreshList(files);
				}
				catch(Exception e){	
				}
			}
		});
		chckbxIncludeSubfolders.setSelected(true);
		chckbxIncludeSubfolders.setBounds(237, 35, 151, 23);
		contentPane.add(chckbxIncludeSubfolders);

		pathlabel = new JLabel("Please choose a path...");
		pathlabel.setForeground(Color.GREEN);
		pathlabel.setBounds(6, 39, 225, 16);
		contentPane.add(pathlabel);	
		

		// Select Action (ComboBox) Listener
		comboBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie){
				activeActionPane.setVisible(false);
				String str = (String)comboBox.getSelectedItem();
				if(str.equals(values[0])){
					activeActionPane = defaultPanel;
				}
				else if(str.equals(values[1])){
					activeActionPane = trimBeginningPanel;
				}
				else if(str.equals(values[2])){
					activeActionPane = titleFixerPanel;
				}
				else if(str.equals(values[3])){
					activeActionPane = folderOpsPanel;
				}
				activeActionPane.setVisible(true);
			}
		});
		
		contentPane.add(bg);
		
	}

	public static void refreshList(){
		list.refreshList(files);
	}

	class List extends JList{
		private JList list;
		private JScrollPane scroll;
		ArrayList<String> files_readable = new ArrayList<String>();

		public List (final ArrayList<File> files, JPanel panel) {
			list = new JList();
			list.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if ((!e.getValueIsAdjusting()) && (list.getSelectedIndex() != -1)){
						File show_properties = Music_Form.files.get(list.getSelectedIndex());
						SingleFile_Form singleFileWindow = new SingleFile_Form(show_properties);
						singleFileWindow.runSingleFile();
						// metadata window
					}
				}
			});
			list.setBounds(0, 58, 388, 140);
			list.setOpaque(true);
			list.setBackground(Color.BLACK);
			list.setForeground(Color.GREEN);
			list.setBorder(null);
					
			// create and add scroll pane
			scroll = new JScrollPane(list);
			scroll.setBackground(Color.BLACK);
			scroll.setForeground(Color.GREEN);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			panel.add(scroll);
			scroll.setViewportView(list);
			scroll.setBounds(0, 58, 388, 140);

			if((!files.isEmpty()) && (files != null)){
				addListValue(files);
			}
		}

		public void addListValue(ArrayList<File> files){
			files_readable = Multiple_Files_Operations.generateReadableNames(files);
			list.setListData(files_readable.toArray());
		}

		public void refreshList(ArrayList<File> files){
			list.removeAll();
			addListValue(files);
		}
	}

	public ArrayList<File> setFilesArrayList(ArrayList<File> files, File path, boolean includeSubFolders) {
		return Multiple_Files_Operations.searchPath(path, includeSubFolders);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}