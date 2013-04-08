package com.operations;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Operation_approveProcess {

	public static void approveChange(ArrayList<File> newFiles, ArrayList<File> oldFiles){
		if(newFiles.size() != oldFiles.size()){
			System.out.println(oldFiles.get(0).toString());
			JOptionPane.showMessageDialog(null, "An error has occurred while changing files");
			System.exit(0);
		}
		else{
			for(int i = 0; i < newFiles.size(); i++){
				File oldFile = oldFiles.get(i);
				File newFile = newFiles.get(i);
				try{
					oldFile.renameTo(newFile);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "An error has occurred while changing files.");
					System.exit(0);
				}
			}
		}
	}

}
