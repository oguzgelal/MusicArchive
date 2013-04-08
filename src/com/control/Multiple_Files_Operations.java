package com.control;

import java.io.File;
import java.util.ArrayList;

import com.operations.Operation_approveProcess;
import com.operations.Operation_folderOps;
import com.operations.Operation_pathOps;
import com.operations.Operation_titleFixer;
import com.operations.Operation_trimBeginning;

public class Multiple_Files_Operations {
	
	// trim beginnings (Operation_trimBeginning)
	public static void trimBeginnings(ArrayList<File> files){
		trimBeginnings(files,null);
	}
	public static void trimBeginnings(ArrayList<File> files, String bandName){
		Operation_trimBeginning.trimBeginnings(files, bandName);
	}

	// search path (Operation_pathOps)
	public static ArrayList<File> searchPath (File path, boolean includeSubFolders){
		return Operation_pathOps.searchPath(path, includeSubFolders);
	}
	
	// generate readable names (Operation_pathOps)
	public static ArrayList<String> generateReadableNames(ArrayList<File> files){
		return Operation_pathOps.generateReadableNames(files);
	}
	
	// approve changes (Operation_approveProcess)
	public static void approveChange(ArrayList<File> newFiles, ArrayList<File> oldFiles){
		Operation_approveProcess.approveChange(newFiles, oldFiles);
	}
	
	// extract folder (Operation_folderOps)
	public static void extractFolders(ArrayList<File> files, File path, boolean ask){
		Operation_folderOps.extractFolders(files, path, ask);
	}
	
	// Folderify (Operation_folderOps)
	public static void Folderify(File path, String type, boolean ask){
		Operation_folderOps.Folderify(path, type, ask, true);
	}
	
	// Title Fixer
	public static void editTitle(ArrayList<File> files, String selection){
		Operation_titleFixer.editTitle(files, selection);
	}
	
}
