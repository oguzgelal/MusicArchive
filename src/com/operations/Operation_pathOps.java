package com.operations;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.ArrayList;

public class Operation_pathOps {
	
	public static String[] acceptableExt = {".mp3", ".MP3", ".m4a", ".M4A", ".wma", ".WMA", ".wav", ".WAV"};
	
	public static ArrayList<File> searchPath (File path, boolean includeSubFolders){
		ArrayList<File> returnFiles = new ArrayList<File>();
		File[] files = path.listFiles();
		if (files != null){
		for(int i = 0; i < files.length; i++){
			String str = files[i].toString();
			if((includeSubFolders) && (files[i].isDirectory())){
				ArrayList<File> toAddList = searchPath(files[i], true);
				for(int j = 0; j < toAddList.size(); j++){
					returnFiles.add(toAddList.get(j));
				}
			}
			else{
				for(int j = 0; j < acceptableExt.length; j++){
					if(files[i].getName().endsWith(acceptableExt[j])){
						returnFiles.add(files[i]);
						break;
					}
				}
			}
		}
		}
		return returnFiles;
	}
	
	public static ArrayList<File> searchPathFolders (File path){
		ArrayList<File> returnFiles = new ArrayList<File>();
		File[] files = path.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				returnFiles.add(file);
			}
		}
		return returnFiles;
	}
	
	public static ArrayList<String> generateReadableNames(ArrayList<File> files){
		ArrayList<String> readable = new ArrayList<String>();
		for(int i = 0; i < files.size(); i++){
			readable.add(files.get(i).getName());
		}
		return readable;
	}
	// output : (abc.mp3) "[0] -> abc , [1] -> mp3"
	public static String[] seperateNameAndExtension(File file){
		String[] nameandextension = new String[2];
		String fname = file.getName();
		String[] seperate = fname.split("\\.");
		String combine = "";
		for(int i = 0; i < seperate.length-1; i++){
			if(i != seperate.length-2){
				combine = combine + seperate[i] + ".";
			}
			else{
				combine += seperate[i];
			}
		}
		nameandextension[0] = combine;
		nameandextension[1] = seperate[seperate.length-1];
		return nameandextension;
	}
	
	// output : "../../../mp3"
	public static String seperatePath(File file){
		String fname = file.toString();
		fname = Operation_trimBeginning.trimWhiteSpaces(fname);
		String[] splitted = fname.split("\\.");
		if(splitted.length == 1){
			return fname;
		}
		else{
			String afterLastDot = "."+splitted[splitted.length-1];
			for(int i = 0; i < acceptableExt.length; i++){
				if(afterLastDot.equals(acceptableExt[i])){
					return file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-file.getName().length()-1);
				}
			}
			return file.getAbsolutePath();
		}
	}
	
	public static File changeNameIfFileExists(ArrayList<File> files, File file){
		int x = 1;
		String path = Operation_pathOps.seperatePath(file);
		boolean exists = files.contains(file);
		while(exists){
			String[] oldname = file.getName().split("\\.");
			String newName = "";
			for(int j = 0; j < oldname.length; j++){
				newName = newName + oldname[j];
				if(j == oldname.length-2){
					if(newName.length() >= 4){
					String last1 = newName.substring(newName.length()-3, newName.length()-2);
					String last2 = newName.substring(newName.length()-1, newName.length());
					if((last1.equals("(")) && (last2.equals(")"))){
						newName = newName.substring(0, newName.length()-4);
						newName = newName + " (" + x + ")";
					}
					else{
						newName = newName + " (" + x + ")";
					}
					}
					else{
						newName = newName + " (" + x + ")";
					}
					newName += ".";
				}
				else if (j != oldname.length-1){
					newName += " ";
				}
			}
			file = new File(path+"/"+newName);
			x++;
			exists = (files.contains(file));
		}
		return file;
	}
	
	public static void copyToClipboard(String str){
		StringSelection strselect = new StringSelection(str);
		Clipboard clbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clbrd.setContents(strselect, strselect);
	}
}
